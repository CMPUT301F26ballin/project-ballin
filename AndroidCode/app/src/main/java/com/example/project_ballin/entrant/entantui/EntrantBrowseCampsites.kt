package com.example.project_ballin.entrant.entantui

import androidx.compose.foundation.layout.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.*
import androidx.navigation.compose.*
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