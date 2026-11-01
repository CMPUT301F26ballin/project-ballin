package com.example.project_ballin.reusableui

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.*
import com.example.project_ballin.Campsite
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun DisplayCampsite(
    modifier: Modifier = Modifier,
    campsite: Campsite){
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                //navController.navigate("CampsiteDetailScreen/${campsites.ID}")
            },
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
@Composable
fun SearchBar(
    navController: NavController,
    viewModel: SearchViewModel = viewModel()
){
    val queryText by viewModel.query.collectAsState()
    val campsites by viewModel.campsites.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = queryText,
            onValueChange = viewModel::onSearchTextChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search Campsites") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(campsites) {campsites ->
                DisplayCampsite(campsite = campsites)
            }
        }

    }
}


