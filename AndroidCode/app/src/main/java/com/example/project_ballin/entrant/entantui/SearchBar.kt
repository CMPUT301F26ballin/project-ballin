package com.example.project_ballin.entrant.entantui

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.ui.unit.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.*
import androidx.navigation.compose.rememberNavController
import com.example.project_ballin.Campsite
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.project_ballin.CampsiteList
import com.example.project_ballin.GeneralDatePicker
import com.example.project_ballin.ui.theme.ProjectBallinTheme
import kotlin.String
import kotlin.collections.sorted

/**
 * The important functions allowing the user to search for campsites based on a variety of factors
 * */
class SearchBar(){


    @Composable
    fun ClearFilters(
        modifier: Modifier = Modifier,
        viewModel: SearchViewModel = viewModel()
    ) {

        Button(
            onClick = { viewModel.clearFilters() },
            modifier = modifier.testTag("Clear Filters")
        ){
            Text("Clear Filters")
        }

    }
    /**
     * function to filter campsites by number of guests
     *
     * @param modifier the modifier to apply to the filter
     * @param viewModel the view model to use for filtering
     *
     */
    @Composable
    fun GuestFilter(
        modifier: Modifier = Modifier,
        viewModel: SearchViewModel = viewModel()
    ) {
        val selectedNumOfGuests by viewModel.numOfGuests.collectAsState()

        Text("Max Number of Guests:")
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = modifier
                .fillMaxWidth()
        ) {
            OutlinedTextField(
                value = selectedNumOfGuests?.toString() ?: "",
                onValueChange = viewModel::onNumOfGuestsChange,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .width(100.dp)
                    .testTag("Occupancy Filter"),
            )
        }
    }

    /**
     *
     * function to filter campsites by location
     *
     * @param modifier for UI
     * @param viewModel the view model to use for filtering
     * @param campsiteList the list of campsites to filter from
     *
     */
    @Composable
    fun LocationFilter(
        modifier: Modifier = Modifier,
        viewModel: SearchViewModel = viewModel(),
        campsiteList: CampsiteList
    ) {
        val locationOptions = campsiteList.campsites.map { it.locationName }.distinct()
        val selectedLocation by viewModel.locationFilter.collectAsState()

        Text("Filter by Location:")
        LazyRow(
            modifier = modifier
                .fillMaxWidth()
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
                    label = { Text(location) },
                    modifier = Modifier.testTag("Location Chip: $location")
                )

            }
        }
    }

    /**
     *
     * Function to filter campsites by date
     *
     *@param modifier for UI
     *@param viewModel the view model to use for filtering
     *@param campsiteList the list of campsites to filter from
     *
     */
    @Composable
    fun DateFilter(
        modifier: Modifier = Modifier,
        viewModel: SearchViewModel,
        campsiteList: CampsiteList
    ) {

        var showDatePicker by remember { mutableStateOf(false) }

        val dateOptions = remember(campsiteList.campsites) {
            campsiteList.campsites
                .flatMap { it.availability }
                .distinct()
                .sorted()
        }
        Button(
            onClick = {
                showDatePicker = true
            }
        ){
            Text("Filter by Availability")
        }
        if (showDatePicker) {
            GeneralDatePicker().DatePick(
                campAvailableDates = dateOptions,
                onDismiss = { showDatePicker = false },
                onSelect = { viewModel.onSelectedDateFilterChange(it) },
                adding = false
            )
        }

    }

    /**
     *
     * function to display the campsites
     *
     * @param navController the navigation controller to use for navigation,
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
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .clickable {
                    navController.navigate("CampsiteDetailScreen/${campsite.ID}/${entrantID}")

                }
                .testTag("Campsite: ${campsite.name}"),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
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

                HorizontalDivider(
                    modifier = Modifier.padding(0.dp, 8.dp),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
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
    @SuppressLint("NotConstructor")
    @Composable
    fun SearchBar(
        navController: NavController,
        viewModel: SearchViewModel = viewModel(),
        entrantID: String,
        campsiteList: CampsiteList
    ) {
        val queryText by viewModel.query.collectAsState()
        val campsites by viewModel.campsites.collectAsState()

        val allSites = campsiteList.campsites.toList()
        LaunchedEffect(allSites) {
            viewModel.updateCampsites(allSites)
        }

        SearchBarContent(
            queryText = queryText,
            onSearchTextChange = viewModel::onSearchTextChange,
            campsites = campsites,
            navController = navController,
            entrantID = entrantID,
            campsiteList = campsiteList,
            viewModel = viewModel
        )
    }

    @Composable
    fun SearchBarContent(
        queryText: String,
        onSearchTextChange: (String) -> Unit,
        campsites: List<Campsite>,
        navController: NavController,
        entrantID: String,
        campsiteList: CampsiteList,
        viewModel: SearchViewModel = viewModel()
    ) {
        var showFilters by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedTextField(
                    value = queryText,
                    onValueChange = onSearchTextChange,
                    modifier = Modifier
                        //.fillMaxWidth()
                        .testTag("Search Bar"),
                    placeholder = { Text("Search Campsites") }
                )

                Button(
                    onClick = { showFilters = !showFilters },
                    modifier = Modifier
                        .testTag("Filters Button")
                        .align(Alignment.CenterVertically),

                ) {
                    Text("Filters")
                }
            }
            if (showFilters) {

                HorizontalDivider(modifier = Modifier.padding(0.dp, 16.dp))

                GuestFilter(viewModel = viewModel)

                Spacer(modifier = Modifier.height(8.dp))

                LocationFilter(viewModel = viewModel, campsiteList = campsiteList)

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    DateFilter(viewModel = viewModel, campsiteList = campsiteList)
                    ClearFilters(viewModel = viewModel)
                }
            }

            HorizontalDivider(modifier = Modifier.padding(top = 16.dp))


            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                items(campsites) { campsite ->

                    Spacer(modifier = Modifier.height(16.dp))

                    DisplayCampsite(
                        navController = navController,
                        campsite = campsite,
                        entrantID = entrantID
                    )
                }
            }
        }
    }
}


