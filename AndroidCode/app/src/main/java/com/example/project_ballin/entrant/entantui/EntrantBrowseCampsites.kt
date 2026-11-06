package com.example.project_ballin.entrant.entantui

import androidx.compose.foundation.layout.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.*
import com.example.project_ballin.CampsiteList
import com.example.project_ballin.reusableui.TopAppBar


@Composable
fun EntrantBrowseCampsites (navController: NavController, campsiteList: CampsiteList, entrantID: String) {
    TopAppBar(
        navController,
        "Browse Campsites"){
        Spacer(modifier = Modifier.height(16.dp))
        SearchBar(navController, campsiteList = campsiteList, entrantID = entrantID)
    }
}

@Preview(showBackground = true)
@Composable
fun EntrantBrowseCampsitesPreview() {

}