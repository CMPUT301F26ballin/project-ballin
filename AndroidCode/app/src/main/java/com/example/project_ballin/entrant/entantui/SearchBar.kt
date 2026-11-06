package com.example.project_ballin.entrant.entantui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.ui.unit.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.*
import com.example.project_ballin.Campsite
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.project_ballin.CampsiteList

/**
 * function to filter campsites by number of guests
 *
 *
 * @param modifier the modifier to apply to the filter
 * @param viewModel the view model to use for filtering
 *
 */
@Composable
fun GuestFilter(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = viewModel()
){
    val selectedNumOfGuests by viewModel.numOfGuests.collectAsState()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
        ,
    ) {
        OutlinedTextField(
            value = selectedNumOfGuests?.toString() ?: "",
            onValueChange = viewModel::onNumOfGuestsChange,
            placeholder = { Text("Number of Guests") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.testTag("Occupancy Filter")
        )
    }
}

/**
 *
 * function to filter campsites by location
 *
 * @param modifier the modifier to apply to the filter
 * @param viewModel the view model to use for filtering
 * @param campsites the list of campsites to filter from
 *
 */
@Composable
fun LocationFilter(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = viewModel(),
    campsites: List<Campsite>
){
    val locationOptions = campsites.map { it.locationName }.distinct()
    val selectedLocation by viewModel.locationFilter.collectAsState()

    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .testTag("Location Filter")
    ) {
        items(locationOptions) { location ->
            FilterChip(
                selected = selectedLocation == location,
                onClick = {
                    if (selectedLocation == location) {
                        viewModel.onSelectedLocationFilterChange(null)
                    } else {
                        viewModel.onSelectedLocationFilterChange(location)
                    }
              },
                label = { Text(location) } ,
                modifier = Modifier.testTag("Location Chip: $location")
            )

        }
    }
}

@Composable
fun DateFilter(){
    TODO() //when date range and stuff have been implemented
}

/**
 *
 * function to display the campsites
 *
 * @param navController the navigation controller to use for navigation,
 * will take the user to the campsite's details screen once implemented
 * @param modifier the modifier to apply to the filter
 * @param campsite the campsites to display
 *
 */
@Composable
fun DisplayCampsite(
    navController: NavController,
    modifier: Modifier = Modifier,
    campsite: Campsite,
    entrantID: String
){
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate("CampsiteDetailScreen/${campsite.ID},${entrantID}")

            }
            .testTag("Campsite: ${campsite.name}"),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = campsite.name,
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = "Location: ${campsite.locationName}",
            )
            Text(
                text = "Max Number of Guests: ${campsite.occupancy}",
            )
            Text(
                text = "Rating: ${campsite.rating}/5",
            )
        }
    }
}

/**
 *
 * searchbar ui element to search for campsites
 *
 * @param navController the navigation controller to use for navigation
 * @param viewModel the view model to use for filtering
 *
 */
@Composable
fun SearchBar(
    navController: NavController,
    viewModel: SearchViewModel = viewModel(),
    entrantID: String,
    campsiteList: CampsiteList
){
    val queryText by viewModel.query.collectAsState()
    val campsites by viewModel.campsites.collectAsState()

    val allSites = campsiteList.campsites.toList()
    LaunchedEffect(allSites) {
        viewModel.updateCampsites(allSites)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = queryText,
            onValueChange = viewModel::onSearchTextChange,
            modifier = Modifier.fillMaxWidth()
                .testTag("Search Bar"),
            placeholder = { Text("Search Campsites") }
        )
        Spacer(modifier = Modifier.height(8.dp))

        GuestFilter()

        Spacer(modifier = Modifier.height(8.dp))

        LocationFilter(campsites = campsites)

        Spacer(modifier = Modifier.height(8.dp))


        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(campsites) {campsite ->
                DisplayCampsite(
                    navController = navController,
                    campsite = campsite,
                    entrantID = entrantID
                )
            }
        }

    }
}


