package com.example.project_ballin.entrant.entantui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.*
import androidx.navigation.compose.*
import com.example.project_ballin.Campsite
import com.example.project_ballin.reusableui.SearchBar
import com.example.project_ballin.reusableui.TopAppBar


@Composable
fun EntrantBrowseCampsites (navController: NavController) {
    TopAppBar(
        navController,
        "Browse Campsites"){
        Spacer(modifier = Modifier.height(16.dp))
        SearchBar(navController)
    }
}


@Preview(showBackground = true)
@Composable
fun EntrantBrowseCampsitesPreview() {
    val navController = rememberNavController()
    EntrantBrowseCampsites(navController)
}