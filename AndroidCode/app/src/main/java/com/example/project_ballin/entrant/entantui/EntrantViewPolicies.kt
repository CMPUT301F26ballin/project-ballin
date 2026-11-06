package com.example.project_ballin.entrant.entantui

import androidx.compose.runtime.*
import androidx.navigation.*
import com.example.project_ballin.reusableui.TopAppBar

/**
 * The policies and guidelines page
 * Entrants can view the policies and guidelines for the app
 * Not done yet
 *
 * @param navController the navigation controller for the app
 */
@Composable
fun EntrantViewPolicies(navController: NavController) {
    TopAppBar(navController,
        "Policy and Guidelines") {
    }
}
