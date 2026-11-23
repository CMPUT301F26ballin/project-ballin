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
import androidx.compose.material3.HorizontalDivider
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
import com.example.project_ballin.lottery.Lottery
import com.example.project_ballin.reusableui.TopAppBar
import java.text.DateFormat
import java.util.Locale
import kotlin.uuid.Uuid

class EntrantConfirmBooking {

    // Source - https://stackoverflow.com/q/49551461
    // Posted by HelloCW
    // Retrieved 2026-11-21, License - CC BY-SA 3.0

    fun Long.toDateString(dateFormat: Int =  DateFormat.MEDIUM): String {
        val df = DateFormat.getDateInstance(dateFormat, Locale.getDefault())
        return df.format(this)
    }
    @Composable
    fun ConfirmBooking(navController: NavController, campsite: Campsite, entrant: Entrant, chosenDates: List<Long>, reservationList: ReservationList) {

        var rulesChecked by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            TopAppBar(
                navController,
                "Confirm Booking"
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp, 16.dp)
                        ){
                            Text(
                                text = "Booking Details:",
                                style = MaterialTheme.typography.titleLarge
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ){
                            Text(
                                text = "Campsite Name: ${campsite.name}",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ){
                            Text(
                                text = "Campsite Location: ${campsite.locationName}",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ){
                            Text(
                                text = "Campsite Occupancy: ${campsite.occupancy}",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ){
                            Text(
                                text = "Campsite Price: $${"%.2f".format(campsite.price)}/night",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ){
                            Text(
                                text = "Booking from: ${chosenDates[0].toDateString()} to: ${chosenDates[1].toDateString()}",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }

                        HorizontalDivider(
                            modifier = Modifier.padding(0.dp, 16.dp, 0.dp, 0.dp)
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp, 16.dp),
                        ) {
                            Text(
                                text = "Before you book, confirm you have read the rules for this campsite:",
                                style = MaterialTheme.typography.titleMedium
                            )

                        }

                        for (rule in campsite.rules) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp, 0.dp),
                            ) {
                                Text(
                                    text = "- $rule",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp, 16.dp),
                                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically

                        ) {
                            Text(
                                text = "I confirm I have read and agree to the rules.",
                                style = MaterialTheme.typography.titleSmall,
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
                                    navController.navigate("EntrantBrowseReservations")
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
}
