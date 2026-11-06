package com.example.project_ballin.entrant.entantui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.project_ballin.CampsiteList
import com.example.project_ballin.entrant.Entrant
import com.example.project_ballin.entrant.Reservation
import com.example.project_ballin.reusableui.TopAppBar


class EntrantSiteBooker {
    /**
     * The entant book site page
     * Entrants can book a campsite from a date picker that shows the available days
     *
     * @param navController the navigation controller for the app
     * @param campsiteID the campsite to book
     * @param entrant the entrant
     */
    @Composable
    fun SiteBooker (navController: NavController, campsiteID: String?, campsiteList: CampsiteList, entrant: Entrant) {


        var chosenDates by remember { mutableStateOf<List<Long>?>(null) }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            TopAppBar(
                navController,
                "Book Site"
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {


                    DateRangePicker().BookingDateRangePicker(
                        availableDates = campsiteList.getCampsite(campsiteID)!!.availability,
                        bookingYear = 2027,
                        modifier = Modifier.weight(1f),
                        chosenDates = { chosenDates = it }
                    )

                    CancelBookButtons(navController, chosenDates, entrant, campsiteID)
                }
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
    fun CancelBookButtons(navController: NavController, chosenDates: List<Long>?, entrant: Entrant, campsiteID: String?) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red),
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Cancel")
            }


            Button(
                onClick = {
                    entrant.activeReservations.add(Reservation(campsiteID,chosenDates))
                },
                enabled = chosenDates != null,
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Book")
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun EntrantBookSitePreview() {

    }
}
