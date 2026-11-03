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
import com.example.project_ballin.Campsite
import com.example.project_ballin.entrant.Entrant
import com.example.project_ballin.reusableui.TopAppBar

/**
 * The entant book site page
 * Entrants can book a campsite from a date picker that shows the available days
 *
 * @param navController the navigation controller for the app
 * @param campsite the campsite to book
 * @param entrant the entrant
 */
class EntrantSiteBooker {
    @Composable
    fun SiteBooker (navController: NavController, campsite: Campsite, entrant: Entrant) {


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
                        availableDates = listOf(
                            DateRangePicker().utcMillis(2027, 3, 19),
                            DateRangePicker().utcMillis(2027, 3, 20),
                            DateRangePicker().utcMillis(2027, 3, 21),
                            DateRangePicker().utcMillis(2027, 3, 31)
                        ),
                        bookingYear = 2027,
                        modifier = Modifier.weight(1f),
                        chosenDates = { chosenDates = it }
                    )

                    CancelBookButtons(navController, chosenDates)
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
    fun CancelBookButtons(navController: NavController, chosenDates: List<Long>?) {
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
