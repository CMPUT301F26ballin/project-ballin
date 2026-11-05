package com.example.project_ballin.entrant.entantui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.project_ballin.Campsite
import com.example.project_ballin.CampsiteList
import com.example.project_ballin.entrant.Entrant
import com.example.project_ballin.entrant.Reservation
import com.example.project_ballin.lottery.Lottery
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

    @Composable
    fun ActiveLotteryCard(lottery: Lottery, entrant: Entrant,navController: NavController){
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "You are registered in a lottery for ${lottery.getAssociatedSites()[0].name}",
                style = MaterialTheme.typography.titleMedium
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    ),
                    onClick = {
                        lottery.removeFromWaitlist(entrant)
                    },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Leave Waiting List")
                }


                Button(
                    onClick = {

                    },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Owner Messages")
                }
            }
        }
    }

    @Composable
    fun CurrentReservationCard(entrant: Entrant, reservation: Reservation, navController: NavController){
        val campsite = CampsiteList().getCampsite(reservation.campsiteID)

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "${campsite.name}\n" +
                        "From: ${reservation.dates[0]} To: ${reservation.dates[1]}\n" +
                        "Details:\n" +
                        " - ${campsite.locationName}\n" +
                        " - ${campsite.occupancy}",
                style = MaterialTheme.typography.titleMedium
            )


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    ),
                    onClick = {

                    },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Cancel Registration")
                }


                Button(
                    onClick = {

                    },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Owner Messages")
                }
            }
        }
    }
}