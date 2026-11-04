package com.example.project_ballin.entrant.entantui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.project_ballin.entrant.Entrant
import com.example.project_ballin.reusableui.TopAppBar
import kotlin.uuid.Uuid

/**
 * The entrant active reservations page
 * Entrants can look at their active reservations
 *
 * @param navController the navigation controller for the app
 * @param entrant the entrant
 */
class ActiveReservationScreen {

    @Composable
    fun ActiveReservations(navController: NavController, entrant: Entrant){
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            TopAppBar(
                navController,
                "Active Reservations"
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {




                }
            }
        }
    }
}