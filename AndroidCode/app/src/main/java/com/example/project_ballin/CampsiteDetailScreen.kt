package com.example.project_ballin

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        site.
    }

}