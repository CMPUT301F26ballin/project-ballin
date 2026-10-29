package com.example.project_ballin.entrant.entantui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.*
import androidx.navigation.compose.*
import com.example.project_ballin.TopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Searchbar(){


}

@Composable
fun EntrantBrowseCampsites (navController: NavController) {
    TopAppBar(
        navController,
        "Browse Campsites"){

    }
}