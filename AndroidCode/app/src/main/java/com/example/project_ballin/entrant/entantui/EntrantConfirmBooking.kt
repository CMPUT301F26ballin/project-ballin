package com.example.project_ballin.entrant.entantui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.project_ballin.Campsite
import com.example.project_ballin.ReservationList
import com.example.project_ballin.entrant.Entrant
import com.example.project_ballin.entrant.Reservation
import com.example.project_ballin.lottery.Lottery
import com.example.project_ballin.lottery.LotteryList
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
    fun ConfirmBooking(navController: NavController, campsite: Campsite, entrant: Entrant, chosenDates: List<Long>, reservationList: ReservationList, lottery: Lottery) {

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
                        ){
                            Text(
                                text = "Booking Details:",
                                style = MaterialTheme.typography.titleLarge
                            )
                        }

                        Spacer(modifier = Modifier.padding(8.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ){
                            Text(
                                text = "Campsite Name: ${campsite.name}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ){
                            Text(
                                text = "Campsite Location: ${campsite.locationName}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ){
                            Text(
                                text = "Campsite Occupancy: ${campsite.occupancy}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ){
                            Text(
                                text = "Campsite Price: $${"%.2f".format(campsite.price)}/night",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ){
                            Text(
                                text = "Booking from: ${chosenDates[0].toDateString()} to: ${chosenDates[1].toDateString()}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }

                        HorizontalDivider(
                            modifier = Modifier.padding(0.dp, 16.dp)
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "Before you book, confirm you have read the rules for this campsite:",
                                style = MaterialTheme.typography.titleMedium
                            )

                        }

                        Spacer(modifier = Modifier.padding(8.dp))

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

                        Spacer(modifier = Modifier.padding(8.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .toggleable(
                                    value = rulesChecked,
                                    onValueChange = { rulesChecked = it }
                                )
                                .testTag("rulesCheckbox"),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "I confirm I have read and agree to the rules.",
                                style = MaterialTheme.typography.titleSmall,
                                modifier = Modifier.weight(1f)
                            )
                            Checkbox(
                                checked = rulesChecked,
                                onCheckedChange = null
                            )
                        }

                        HorizontalDivider(
                            modifier = Modifier.padding(0.dp, 16.dp)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Button(
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.error
                                ),
                                onClick = {
                                    navController.popBackStack()
                                },
                                modifier = Modifier
                                    .weight(1f)
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
                                            userID = entrant.userID,
                                            cancelled = false,
                                            resID = Uuid.random().toString(),
                                        )
                                    )
                                    navController.navigate("EntrantBrowseReservations")

                                    entrant.activeLotteries.remove(lottery)
                                    entrant.wonLotteries.remove(lottery)
                                },
                                enabled = rulesChecked,
                                modifier = Modifier
                                    .weight(1f)
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
