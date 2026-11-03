package com.example.project_ballin.entrant.entantui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.*
import androidx.navigation.compose.*
import com.example.project_ballin.Campsite
import com.example.project_ballin.TopAppBar
import com.example.project_ballin.entrant.Entrant
import com.example.project_ballin.lottery.Lottery
import com.example.project_ballin.owner.Owner
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * The entant browse reservations page
 * Entrants can look at their current reservations and past reservations
 * Entrants can book a campsite if they have won a lottery
 *
 * @param navController the navigation controller for the app
 */
@Composable
fun EntrantBrowseReservations (navController: NavController, entrant: Entrant) {

    var chosenDates by remember { mutableStateOf<List<Long>?>(null) }

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
                ActivePastReservationButtons()

                ShowLotteries(entrant)
            }
        }
    }
}

/**
 * Shows the active and past reservation buttons
 */
@Composable
fun ActivePastReservationButtons() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = {

            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Active\nReservations")
        }

        Button(
            onClick = {

            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Past\nReservations")
        }
    }
}

/**
 * Shows the decline and book buttons
 *
 * @param chosenDates the chosen dates for the reservation
 *
 */

@Composable
fun DeclineBookButtons(chosenDates: List<Long>?) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red),
            onClick = {

            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Cancel")
        }


        Button(
            onClick = {

            },
            enabled = chosenDates != null,
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Book")
        }
    }
}

@Composable
fun ShowLotteries(entrant: Entrant) {
    val activeLotteries = entrant.getActiveLotteries()

    LazyColumn {
        items(activeLotteries) { lottery ->
            LotteryCard(lottery, entrant)
        }
    }
}

@OptIn(ExperimentalUuidApi::class)
@Composable
fun LotteryCard(lottery: Lottery, entrant: Entrant) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    ) {
        if (entrant.getUuid() in lottery.getWinners()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "You have won a lottery for ${lottery.getAssociatedSites()[0].name}. Book it?",
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

                        },
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text("Select Dates")
                    }
                }
            }
        }

        else if (entrant.getUuid() in lottery.getLosers()) {
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
                        text = "Unfortunately you lost the lottery for ${lottery.getAssociatedSites()[0].name}.",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {

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
                    .padding(16.dp)
            ) {
                Text(
                    text = "You are registered in a lottery for ${lottery.getAssociatedSites()[0].name}",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}


@OptIn(ExperimentalUuidApi::class)
@Preview(showBackground = true)
@Composable
fun EntrantBrowseReservationsPreview() {
    val navController = rememberNavController()
    val entrant = Entrant(
        name = "Mies",
        phoneNum = "123",
        email = "mies@ualberta.ca",
        deviceId = "123456",
        profilePic = "xy",
        appPolicy = "abc",
        userID = Uuid.random()
    )

    val owner = Owner(
        name = "Nick",
        phoneNum = "345",
        email = "nick@ualberta.ca",
        deviceId = "345",
        profilePic = "zw",
        appPolicy = "instagram",
        userID = Uuid.random(),
        campsitesOwned = arrayListOf(Uuid.random())
    )

    val campsite = Campsite(
        name = "Campsite",
        ID = Uuid.random(),
        tags = mutableListOf("test"),
        price = 1.toFloat(),
        rating = 5.toFloat(),
        rules = "no pooping",
        description = "poop",
        occupancy = 1,
        ownerID = owner.getUuid(),
    )
        val lottery1 = Lottery(
        waitList = mutableListOf(entrant),
        occupancy = 1,
        numWinners = 1,
        lotteryOwner = owner,
        associatedSites = mutableListOf(campsite),
        lotteryID = Uuid.random(),
        winners = mutableListOf(entrant.getUuid()),
        losers = mutableListOf()
    )
    val lottery2 = Lottery(
        waitList = mutableListOf(entrant),
        occupancy = 1,
        numWinners = 1,
        lotteryOwner = owner,
        associatedSites = mutableListOf(campsite),
        lotteryID = Uuid.random(),
        winners = mutableListOf(),
        losers = mutableListOf(entrant.getUuid())
    )

    val lottery3 = Lottery(
        waitList = mutableListOf(entrant),
        occupancy = 1,
        numWinners = 1,
        lotteryOwner = owner,
        associatedSites = mutableListOf(campsite),
        lotteryID = Uuid.random(),
        winners = mutableListOf(),
        losers = mutableListOf()
    )


    entrant.addActiveLottery(lottery1)
    entrant.addActiveLottery(lottery2)
    entrant.addActiveLottery(lottery3)


    EntrantBrowseReservations(navController, entrant)
}
