package com.example.project_ballin.entrant.entantui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.project_ballin.CampsiteList
import com.example.project_ballin.ReservationList
import com.example.project_ballin.entrant.Entrant
import com.example.project_ballin.entrant.Reservation
import com.example.project_ballin.reusableui.TopAppBar
import org.w3c.dom.Text
import java.text.DateFormat
import java.util.Locale

class PastReservationScreen {

    // Converts long to date string
    // Source - https://stackoverflow.com/q/49551461
    // Posted by HelloCW
    // Retrieved 2026-11-21, License - CC BY-SA 3.0

    fun Long.toDateString(dateFormat: Int =  DateFormat.MEDIUM): String {
        val df = DateFormat.getDateInstance(dateFormat, Locale.getDefault())
        return df.format(this)
    }

    /**
     * The entrant past reservations page
     * Entrants can look at their past reservations
     *
     * @param navController the navigation controller for the app
     * @param entrant the entrant
     */
    @Composable
    fun PastReservations(navController: NavController, entrant: Entrant, campsiteList: CampsiteList, reservationList: ReservationList){
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            TopAppBar(
                navController,
                "Past Reservations"
            ) {
                LazyColumn(
                    modifier = Modifier.padding(16.dp)
                ){
                    if(reservationList.getPastReservationsForEntrant(entrant.userID).isNotEmpty()){
                        item{


                            Text(
                                text = "Your past reservations:",
                                style = MaterialTheme.typography.titleLarge,

                                )

                            HorizontalDivider(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 16.dp))
                        }
                        items(reservationList.getPastReservationsForEntrant(entrant.userID)){ reservation ->
                            PastReservationCard(entrant, reservation, campsiteList, navController)

                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }

                    if(reservationList.getCancelledReservationsForEntrant(entrant.userID).isNotEmpty()){

                        item{
                            Text(
                                text = "Your cancelled reservations:",
                                style = MaterialTheme.typography.titleLarge
                            )

                            HorizontalDivider(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 16.dp))
                        }

                        items(reservationList.getCancelledReservationsForEntrant(entrant.userID)){ reservation ->
                            PastReservationCard(entrant, reservation, campsiteList, navController)

                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }



                }
            }
        }
    }

    /**
     * The entrant current reservation card
     * Entrants can look at their current reservations
     *
     * @param entrant the entrant
     * @param reservation the reservation
     * @param campsiteList the list of campsites
     * @param navController the navigation controller for the app
     *
     */
    @Composable
    fun PastReservationCard(entrant: Entrant, reservation: Reservation, campsiteList: CampsiteList, navController: NavController) {
        val campsite = campsiteList.getCampsite(reservation.campsiteID)
        val startDate = reservation.dates?.get(0)?.toDateString()
        val endDate = reservation.dates?.get(1)?.toDateString()


        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("Past Reservation Card")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ){
                if(!reservation.cancelled){
                    Row {
                        Text(
                            text = "Past Reservation: ${campsite?.name}",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
                else{
                    Row {
                        Text(
                            text = "Cancelled Reservation: ${campsite?.name}",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }


                HorizontalDivider(modifier = Modifier.padding(0.dp, 16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Row {
                            Text(
                                text = "Details:",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                        Row(
                            modifier = Modifier.padding(16.dp, 0.dp)
                        ) {
                            Text(
                                text = "- Location: ${campsite?.locationName}\n" +
                                        "- Occupancy: ${campsite?.occupancy}\n" +
                                        "- Price: $${"%.2f".format(campsite?.price)}/night",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }

                    }
                }

                HorizontalDivider(modifier = Modifier.padding(0.dp, 16.dp))

                Button(
                    onClick = {
                        navController.navigate("CampsiteDetailScreen/${campsite?.ID}/${entrant.userID}")

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("More Details Button")

                ) {
                    Text("More details")
                }

                if(!reservation.cancelled){
                    Button(
                        onClick = {
                            navController.navigate("entrantReviewScreen/${campsite?.ID}")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("Review Button")

                    ) {
                        Text("Leave a review")
                    }
                }

            }
        }
    }
}