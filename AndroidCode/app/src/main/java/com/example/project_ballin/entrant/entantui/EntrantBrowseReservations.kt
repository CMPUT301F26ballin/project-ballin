package com.example.project_ballin.entrant.entantui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.*
import androidx.navigation.compose.*
import com.example.project_ballin.TopAppBar

/**
 * The entant browse reservations page
 * Entrants can look at their current reservations and past reservations
 * Entrants can book a campsite if they have won a lottery
 *
 * @param navController the navigation controller for the app
 */
@Composable
fun EntrantBrowseReservations (navController: NavController) {

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

                BookingDateRangePicker(
                    availableDates = listOf(
                        utcMillis(2027, 3, 19),
                        utcMillis(2027, 3, 20),
                        utcMillis(2027, 3, 21),
                        utcMillis(2027, 3, 31)
                    ),
                    bookingYear = 2027,
                    modifier = Modifier.weight(1f),
                    chosenDates = { chosenDates = it }
                )

                DeclineBookButtons(chosenDates)
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
            Text("Decline")
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
fun EntrantBrowseReservationsPreview() {
    val navController = rememberNavController()
    EntrantBrowseReservations(navController)
}
