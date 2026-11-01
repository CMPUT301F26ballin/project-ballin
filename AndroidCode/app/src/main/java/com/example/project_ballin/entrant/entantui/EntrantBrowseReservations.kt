package com.example.project_ballin.entrant.entantui

import androidx.compose.runtime.*
import androidx.navigation.*
import com.example.project_ballin.reusableui.TopAppBar

@Composable
fun EntrantBrowseReservations (navController: NavController) {
    TopAppBar(
        navController,
        "Browse Reservations") {
    }
}
