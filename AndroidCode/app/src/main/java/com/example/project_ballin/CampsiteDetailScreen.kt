package com.example.project_ballin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun CampsiteDetailScreen(navController: NavController, siteID: Int, getSite: (Int) -> Campsite){
    TopAppBar(navController, "Campsite Details"){
        CampsiteDetailsView(navController, siteID, getSite = {getSite(it)})
    }

}

@Composable
fun CampsiteDetailsView(navController: NavController, siteID: Int, getSite: (Int) -> Campsite){
    val site = getSite(siteID)
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        //images go at the top.

        Text("Campsite Name: ${site.name}")
        Spacer(Modifier.height(16.dp))
        Text("Campsite Description: ${site.description}")
        Spacer(Modifier.height(16.dp))
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)){
            Text("Price Per Night: ${site.price}")
            Spacer(Modifier.width(16.dp))
            Text("Occupancy: ${site.occupancy}")
        }
        Spacer(Modifier.height(16.dp))
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)){
            Text("Campsite Description: ${site.description}")
        }
        Spacer(Modifier.height(16.dp))
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)){
            if (site.rating != null) {
                Text("Current Rating: ${site.rating}")
            } else {
                Text("No Ratings Available")
            }
        }
        Spacer(Modifier.height(16.dp))
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            Text(site.rules)
        }
    }
}