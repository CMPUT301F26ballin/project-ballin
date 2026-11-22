package com.example.project_ballin.entrant.entantui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.project_ballin.Campsite
import com.example.project_ballin.ReservationList
import com.example.project_ballin.entrant.Entrant
import com.example.project_ballin.entrant.Reservation
import com.example.project_ballin.reusableui.TopAppBar
import kotlin.uuid.Uuid

class EntrantConfirmBooking {
    @Composable
    fun ConfirmBooking(navController: NavController, campsite: Campsite, entrant: Entrant, chosenDates: List<Long>, reservationList: ReservationList) {

        var rulesChecked by remember { mutableStateOf(true) }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            TopAppBar(
                navController,
                "Confirm Booking"
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                    ) {
                        Text(
                            text = "Before you book, confirm you have read the rules for this campsite:",
                            style = MaterialTheme.typography.titleMedium
                        )
                        for (rule in campsite.rules) {
                            Text(
                                text = "- $rule",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                    ) {
                        Text(
                            text = "I confirm I have read and agree to the rules.",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Checkbox(
                            checked = rulesChecked,
                            onCheckedChange = { rulesChecked = it },
                            modifier = Modifier.testTag("rulesCheckbox")
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                    ){
                        Text(
                            text = "Campsite Name: ${campsite.name}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Campsite Location: ${campsite.locationName}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Campsite Occupancy: ${campsite.occupancy}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Campsite Price: ${campsite.price} per night",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Booking from: ${chosenDates[0]} to: ${chosenDates[1]}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                    ) {
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error
                            ),
                            onClick = {
                                navController.popBackStack()
                            },
                            modifier = Modifier.padding(16.dp)
                                .testTag("abortBookingButton")
                        ) {
                            Text("Cancel")
                        }


                        Button(
                            onClick = {
                                reservationList.addReservation(
                                    Reservation(
                                    campsite.ID,
                                    chosenDates,
                                    UserID = entrant.userID,
                                    cancelled = false,
                                    resID = Uuid.random().toString(),
                                    )
                                )
                                navController.navigate("EntrantBrowseReservations/${entrant.userID}")
                            },
                            enabled = rulesChecked,
                            modifier = Modifier.padding(16.dp)
                                .testTag("confirmBookingButton")
                        ) {
                            Text("Confirm Booking")
                        }
                    }
                }
            }
        }
    }
}
