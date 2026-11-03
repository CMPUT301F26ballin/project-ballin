package com.example.project_ballin.entrant.entantui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.project_ballin.reusableui.TopAppBar


class ActiveReservationScreen {

    @Composable
    fun ActiveReservations(navController: NavController){
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

    @Preview(showBackground = true)
    @Composable
    fun ActiveReservationScreenPreview() {
        val navController = rememberNavController()
        ActiveReservations(navController)
    }
}