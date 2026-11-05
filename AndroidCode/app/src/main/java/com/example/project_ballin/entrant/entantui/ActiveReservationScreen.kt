package com.example.project_ballin.entrant.entantui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.project_ballin.CampsiteList
import com.example.project_ballin.entrant.Entrant
import com.example.project_ballin.entrant.Reservation
import com.example.project_ballin.lottery.Lottery
import com.example.project_ballin.reusableui.TopAppBar

/**
 * The entrant active reservations page
 * Entrants can look at their active reservations
 *
 * @param navController the navigation controller for the app
 * @param entrant the entrant
 */
class ActiveReservationScreen {

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
            Dialog(onDismissRequest = { lotteryToLeave = null }) {
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
    }

    @Composable
    fun ActiveLotteryCard(lottery: Lottery, entrant: Entrant, campsiteList: CampsiteList, navController: NavController, onLeaveClick: () -> Unit){
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            if (entrant.userID in lottery.winners) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "You have won a lottery for ${campsiteList.getCampsite(lottery.associatedSites[0])?.name}. Book it?",
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
                            Text("Decline")
                        }


                        Button(
                            onClick = {
                                val site = lottery.associatedSites[0]
                                navController.navigate("EntrantSiteBooker/${site}")
                            },
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text("Select Dates")
                        }
                    }
                }
            }

            else if (entrant.userID in lottery.losers) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = "Unfortunately you lost the lottery for ${campsiteList.getCampsite(lottery.associatedSites[0])?.name}.",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = {
                                entrant.activeLotteries.remove(lottery)
                            }
                        ) {
                            Text("Ok")
                        }

                    }
                }
            }

            else{
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp))
                {
                    Text(
                        text = "You are registered in a lottery for ${
                            campsiteList.getCampsite(
                                lottery.associatedSites[0]
                            )?.name
                        }",
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
                            onClick = onLeaveClick
                            ,
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
        }
    }


    @Composable
    fun CurrentReservationCard(entrant: Entrant, reservation: Reservation, campsiteList: CampsiteList, navController: NavController){
        val campsite = campsiteList.getCampsite(reservation.campsiteID)

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "${campsite?.name}\n" +
                        "From: ${reservation.dates?.get(0)} To: ${reservation.dates?.get(1)}\n" +
                        "Details:\n" +
                        " - ${campsite?.locationName}\n" +
                        " - ${campsite?.occupancy}",
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