package com.example.project_ballin.entrant.entantui

import android.annotation.SuppressLint
import androidx.compose.runtime.*
import androidx.navigation.*
import com.example.project_ballin.reusableui.TopAppBar
/**
 * The screen allowing the entrant to view the rules and policies.
 *
 * */
class EntrantViewPolicies(){
    /**
     * The policies and guidelines page
     * Entrants can view the policies and guidelines for the app
     * Not done yet
     *
     * @param navController the navigation controller for the app
     */
    @SuppressLint("NotConstructor")
    @Composable
    fun EntrantViewPolicies(navController: NavController) {
        TopAppBar(
            navController,
            "Policy and Guidelines"
        ) {
        }
    }
}
