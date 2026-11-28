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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.project_ballin.CampsiteList
import com.example.project_ballin.entrant.Entrant
import com.example.project_ballin.lottery.Lottery
import com.example.project_ballin.reusableui.TopAppBar


class EntrantSiteBooker {
    /**
     * The entrant book site page
     * Entrants can book a campsite from a date picker that shows the available days
     *
     * @param navController the navigation controller for the app
     * @param campsiteID the campsite to book
     * @param entrant the entrant
     * @param lottery the lottery
     */
    @Composable
    fun SiteBooker (navController: NavController, campsiteID: String, entrant: Entrant, lottery: Lottery) {


        var chosenDates by remember { mutableStateOf<List<Long>?>(null) }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            TopAppBar(
                navController,
                "Book Site"
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                ){
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(16.dp, 16.dp, 16.dp, 0.dp)
                    ) {
                        DateRangePicker().BookingDateRangePicker(
                            availableDates = CampsiteList().getCampsite(campsiteID)!!.availability,
                            modifier = Modifier.weight(1f),
                            chosenDates = { chosenDates = it }
                        )

                    }

                    CancelBookButtons(navController, chosenDates, campsiteID, lottery)
                }
            }
        }
    }

    /**
     * Shows the decline and book buttons
     *
     * @param navController the navigation controller for the app
     * @param chosenDates the chosen dates for the reservation
     * @param campsiteID the campsite to book
     * @param lottery the lottery associated with the site
     *
     */
    @Composable
    fun CancelBookButtons(navController: NavController, chosenDates: List<Long>?, campsiteID: String?, lottery: Lottery?) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                ),
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Cancel")
            }


            Button(
                onClick = {
                    navController.navigate("confirmBooking/${campsiteID}/${chosenDates?.joinToString(",")}/${lottery?.lotteryID}")
                },
                enabled = chosenDates != null, //note for self use with validate details in userdetailmodel() -N
                modifier = Modifier.weight(1f)
            ) {
                Text("Book")
            }
        }
    }
}
