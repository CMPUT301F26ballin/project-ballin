package com.example.project_ballin.entrant.entantui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.*
import androidx.navigation.compose.*
import com.example.project_ballin.CampsiteList
import com.example.project_ballin.reusableui.TopAppBar
import com.example.project_ballin.entrant.Entrant
import com.example.project_ballin.lottery.Lottery
import com.example.project_ballin.reusableui.TopAppBar
import kotlin.uuid.ExperimentalUuidApi

/**
 * The entant browse reservations page
 * Entrants can look at their current reservations and past reservations
 * Entrants can book a campsite if they have won a lottery
 *
 * @param navController the navigation controller for the app
 */

class EntrantBrowseReservations{
    @Composable
    fun BrowseReservations (navController: NavController, entrant: Entrant) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            TopAppBar(
                navController,
                "Browse Reservations"
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ActivePastReservationButtons(navController)

                    ShowLotteries(entrant, navController)
                }
            }
        }
    }

    /**
     * Shows the active and past reservation buttons
     */
    @Composable
    fun ActivePastReservationButtons(navController: NavController) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = {
                    navController.navigate("ActiveReservationScreen")
                },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.weight(1f)

            ) {
                Text("Active\nReservations",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium)
            }



            Button(
                onClick = {
                    navController.navigate("PastReservationScreen")
                },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.weight(1f)

            ) {
                Text("Past\nReservations",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium)

            }
        }
    }

    /**
     * Shows the active lotteries as a lazyColumn of cards
     *
     * @param entrant the entrant
     * @param navController the navigation controller for the app
     *
     */
    @Composable
    fun ShowLotteries(entrant: Entrant, navController: NavController) {
        val activeLotteries = entrant.getActiveLotteries()

        LazyColumn {
            items(activeLotteries) { lottery ->
                LotteryCard(lottery, entrant, navController)
            }
        }
    }

    /**
     * Shows a card for a lottery
     * Entrants can book a campsite if they have won the lottery
     * Entrants can dismiss a lost lottery
     * Cards are displayed based on the lottery status
     *
     * @param lottery the lottery to show
     * @param entrant the entrant
     * @param navController the navigation controller for the app
     */
    @OptIn(ExperimentalUuidApi::class)
    @Composable
    fun LotteryCard(lottery: Lottery, entrant: Entrant, navController: NavController) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        ) {
            if (entrant.getUuid() in lottery.winners) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "You have won a lottery for ${CampsiteList().getCampsite(lottery.associatedSites[0])?.name}. Book it?",
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

            else if (entrant.getUuid() in lottery.losers) {
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
                            text = "Unfortunately you lost the lottery for ${CampsiteList().getCampsite(lottery.associatedSites[0])?.name}.",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = {
                                entrant.removeActiveLottery(lottery)
                            }
                        ) {
                            Text("Ok")
                        }

                    }
                }
            }
        }
    }


    @Preview(showBackground = true)
    @Composable
    fun EntrantBrowseReservationsPreview() {
    }
}

