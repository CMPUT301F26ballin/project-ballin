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

class PastReservationScreen {
    /**
     * The entrant past reservations page
     * Entrants can look at their past reservations
     *
     * @param navController the navigation controller for the app
     * @param entrant the entrant
     */
    @Composable
    fun PastReservations(navController: NavController, entrant: Entrant){
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            TopAppBar(
                navController,
                "Past Reservations"
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

    @Preview(showBackground = true)
    @Composable
    fun ActiveReservationScreenPreview() {
    }
}