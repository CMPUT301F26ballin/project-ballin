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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.navigation.*
import com.example.project_ballin.CampsiteList
import com.example.project_ballin.reusableui.TopAppBar
import com.example.project_ballin.entrant.Entrant
import com.example.project_ballin.lottery.Lottery
import com.google.apps.card.v1.Columns

class EntrantBrowseReservations{

    /**
     * The entant browse reservations page
     * Entrants can look at their current reservations and past reservations
     * Entrants can book a campsite if they have won a lottery
     *
     * @param navController the navigation controller for the app
     */
    @Composable
    fun BrowseReservations (navController: NavController, campsiteList: CampsiteList ,entrant: Entrant) {

        var lotteryToLeave by remember { mutableStateOf<Lottery?>(null) }

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

                    val lotteriesToShow = entrant.activeLotteries.filter {
                        entrant.userID in it.winners || entrant.userID in it.losers
                    }

                    LazyColumn{
                        items(lotteriesToShow) { lottery ->

                            Spacer(modifier = Modifier.height(16.dp))

                            LotteryCard(lottery, entrant, campsiteList, navController, { lotteryToLeave = lottery })
                        }
                    }
                }
            }
        }

        //Looked up how to make a dialog in compose
        //https://developer.android.com/develop/ui/compose/components/dialog#dialog-composable

        //Pops up over the screen when user declines a lottery they won
        lotteryToLeave?.let { lottery ->
            ConfirmActionDialog().ConfirmAction(
                "Are you sure you want to decline booking this site??",
                confirm = { confirmed ->
                    if (confirmed) {
                        lottery.waitList.remove(entrant.userID)
                        lottery.winners.remove(entrant.userID)
                        entrant.activeLotteries.remove(lottery)
                        entrant.wonLotteries.remove(lottery)
                    }
                    lotteryToLeave = null
                }
            )
        }
    }

    /**
     * Shows the active and past reservation buttons
     * Entrants can look at their current reservations and past reservations by clicking the respective button
     *
     * @param navController the navigation controller for the app
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
                modifier = Modifier
                    .weight(1f)
                    .testTag("Active Reservation Button"),

            ) {
                Text("Active\nreservations",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium)
            }


            Button(
                onClick = {
                    navController.navigate("PastReservationScreen")
                },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .weight(1f)
                    .testTag("Past Reservation Button"),

            ) {
                Text("Past\nreservations",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium)

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
     * @param campsiteList the list of campsites
     * @param navController the navigation controller for the app
     * @param onDeclineClick the function to run when the decline button is clicked
     */
    @Composable
    fun LotteryCard(lottery: Lottery, entrant: Entrant, campsiteList: CampsiteList, navController: NavController, onDeclineClick: () -> Unit){
        Card(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            if (entrant.userID in lottery.winners) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .testTag("Won Lottery Card")
                ) {
                    Text(
                        text = "You won the lottery for:",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    for(campsite in lottery.associatedSites){
                        Text(
                            text = "- ${campsiteList.getCampsite(campsite)?.name}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Book it?",
                        style = MaterialTheme.typography.titleMedium
                    )

                    HorizontalDivider(modifier = Modifier.padding(0.dp, 16.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth(),

                    ) {
                        Button(
                            onClick = {
                                navController.navigate("siteSelector/${lottery.lotteryID}")
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("Choose Site Button")
                        ) {
                            Text("Choose site")
                        }

                        Button(
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error
                            ),
                            onClick = onDeclineClick
                            ,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("Decline Button")
                        ) {
                            Text("Decline")
                        }
                    }
                }
            }

            else if (entrant.userID in lottery.losers) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .testTag("Lost Lottery Card")
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = "Unfortunately, you lost the lottery for:",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    for(campsite in lottery.associatedSites){
                        Text(
                            text = "- ${campsiteList.getCampsite(campsite)?.name}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                    HorizontalDivider(modifier = Modifier.padding(0.dp, 16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = {
                                entrant.activeLotteries.remove(lottery)
                            },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("Dismiss Button")
                        ) {
                            Text("Dismiss")
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

