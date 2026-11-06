package com.example.project_ballin.entrant.entantui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.project_ballin.CampsiteList
import com.example.project_ballin.entrant.Entrant
import com.example.project_ballin.entrant.Reservation
import com.example.project_ballin.lottery.Lottery
import com.example.project_ballin.reusableui.TopAppBar


class ActiveReservationScreen {

    /**
     * The entrant active reservations page
     * Entrants can look at their active reservations
     *
     * @param navController the navigation controller for the app
     * @param entrant the entrant
     * @param campsiteList the list of campsites
     */
    @Composable
    fun ActiveReservations(navController: NavController, entrant: Entrant, campsiteList: CampsiteList){
        var lotteryToLeave by remember { mutableStateOf<Lottery?>(null) }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            TopAppBar(
                navController,
                "Active Reservations"
            ) {
                LazyColumn {
                    items(entrant.activeReservations) { reservation ->
                        CurrentReservationCard(entrant, reservation, campsiteList, navController)
                    }

                    items(entrant.activeLotteries) { lottery ->
                        ActiveLotteryCard(lottery, entrant, campsiteList, navController, { lotteryToLeave = lottery })
                    }
                }
            }
        }


        //Looked up how to make a dialog in compose
        //https://developer.android.com/develop/ui/compose/components/dialog#dialog-composable
        lotteryToLeave?.let { lottery ->
            ConfirmActionDialog().ConfirmAction(
                "Are you sure you want to leave this waiting list?",
                confirm = { confirmed ->
                    if (confirmed) {
                            lottery.waitList.remove(entrant.userID)
                        entrant.activeLotteries.remove(lottery)
                    }
                    lotteryToLeave = null
                }
            )
        }
    }


    /**
     * The entrant active lottery card
     * Entrants can look at their active lotteries and leave them
     * @param lottery the lottery
     * @param entrant the entrant
     * @param campsiteList the list of campsites
     * @param navController the navigation controller for the app
     * @param onLeaveClick the function to run when the leave button is clicked
     */
    @Composable
    fun ActiveLotteryCard(lottery: Lottery, entrant: Entrant, campsiteList: CampsiteList, navController: NavController, onLeaveClick: () -> Unit){
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            if (entrant.userID in lottery.waitList) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .testTag("Active Lottery Card")
                )
                {
                    Text(
                        text = "You are registered in a lottery for ${
                            campsiteList.getCampsite(
                                lottery.associatedSites[0]
                            )?.name
                        }",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error
                            ),
                            onClick = onLeaveClick
                            ,
                            modifier = Modifier
                                .testTag("Leave Waiting List Button")
                        ) {
                            Text("Leave Waiting List")
                        }


                        Button(
                            onClick = {

                            },
                            modifier = Modifier
                                .testTag("Owner Messages Button")
                        ) {
                            Text("Owner Messages")
                        }
                    }
                }
            }
        }
    }


    /**
     * The entrant current reservation card
     * Entrants can look at their current reservations
     *
     * @param entrant the entrant
     * @param reservation the reservation
     * @param campsiteList the list of campsites
     * @param navController the navigation controller for the app
     *
     */
    @Composable
    fun CurrentReservationCard(entrant: Entrant, reservation: Reservation, campsiteList: CampsiteList, navController: NavController){
        val campsite = campsiteList.getCampsite(reservation.campsiteID)

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .testTag("Current Reservation Card")
        ) {
            Text(
                text = "${campsite?.name}\n" +
                        "From: ${reservation.dates?.get(0)} To: ${reservation.dates?.get(1)}\n" +
                        "Details:\n" +
                        " - ${campsite?.locationName}\n" +
                        " - ${campsite?.occupancy}",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    ),
                    onClick = {

                    },

                ) {
                    Text("Cancel Registration")
                }


                Button(
                    onClick = {

                    },

                ) {
                    Text("Owner Messages")
                }
            }
        }
    }
}