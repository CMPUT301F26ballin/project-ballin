package com.example.project_ballin.entrant.entantui

import android.util.Size
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.project_ballin.Campsite
import com.example.project_ballin.entrant.Entrant
import com.example.project_ballin.entrant.Reservation
import com.example.project_ballin.reusableui.TopAppBar
import nl.dionsegijn.konfetti.compose.KonfettiView
import nl.dionsegijn.konfetti.core.Angle
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.text.DateFormat
import java.util.Locale
import java.util.concurrent.TimeUnit

class EntrantBookingConfirmation {
    // Converts long to date string
    // Source - https://stackoverflow.com/q/49551461
    // Posted by HelloCW
    // Retrieved 2026-11-21, License - CC BY-SA 3.0
    fun Long.toDateString(dateFormat: Int =  DateFormat.MEDIUM): String {
        val df = DateFormat.getDateInstance(dateFormat, Locale.getDefault())
        return df.format(this)
    }



    /**
     * The entrant confirm booking page
     * Entrants can confirm a booking after confirming they have read the rules for the campsite
     *
     * @param navController the navigation controller for the app
     * @param campsite the campsite to book
     * @param entrant the entrant
     * @param chosenDates the chosen dates for the reservation
     * @param reservationList the list of reservations
     * @param lottery the lottery associated with the site
     */
    @Composable
    fun BookingConfirmation(navController: NavController, reservation: Reservation?, campsite: Campsite?, entrant: Entrant?) {

        val parties = remember {
            listOf(
                Party(
                    angle = Angle.BOTTOM,
                    spread = 360,
                    speed = 0f,
                    maxSpeed = 30f,
                    damping = 0.9f,
                    position = Position.Relative(0.5, 0.0),
                    emitter = Emitter(duration = 150, TimeUnit.MILLISECONDS).max(500),
                    fadeOutEnabled = false,
                    timeToLive = 10000,

                )
            )
        }


        Box(
            modifier = Modifier.fillMaxSize()
        ) {


            TopAppBar(
                navController,
                "Your Booking"
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
                                text = "Your booking is confirmed!",
                                style = MaterialTheme.typography.titleLarge
                            )
                        }

                        Spacer(modifier = Modifier.padding(8.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ){
                            Text(
                                text = "Booking details:",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ){
                            Text(
                                text = "Campsite: ${campsite?.name}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }

                        Spacer(modifier = Modifier.padding(8.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ){
                            Text(
                                text = "Check in: ${reservation?.dates?.get(0)?.toDateString()}",
                                style = MaterialTheme.typography.bodyLarge

                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ){
                            Text(
                                text = "Check out: ${reservation?.dates?.get(1)?.toDateString()}",
                                style = MaterialTheme.typography.bodyLarge
                            )

                        }


                        HorizontalDivider(
                            modifier = Modifier.padding(0.dp, 16.dp)
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ){
                            Text(
                                text = "Campsite Location: ${campsite?.locationName}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ){
                            Text(
                                text = "Campsite Occupancy: ${campsite?.occupancy}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ){
                            Text(
                                text = "Campsite Price: $${"%.2f".format(campsite?.price)}/night",
                                style = MaterialTheme.typography.bodyLarge
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
                                onClick = {
                                    navController.popBackStack("EntrantBrowseReservations", false)
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Continue")
                            }
                        }
                    }
                }
            }
        }

        KonfettiView(
            modifier = Modifier.fillMaxSize(),
            parties = parties
        )

    }
}