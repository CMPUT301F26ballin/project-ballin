package com.example.project_ballin.owner.ownerUI

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.project_ballin.Campsite
import com.example.project_ballin.EntrantList
import com.example.project_ballin.entrant.Reservation
import com.example.project_ballin.reusableui.TopAppBar

/**
 * The screen utilized by owners to see various reservations (upcoming, past or cancelled)
 * The screen shows all of a certain type of reservation for one specific campsite.
 * This screen is accessed via campsiteDetailScreen
 *
 * */

class OwnerReservationScreen {

    val entrantList = EntrantList()

    /**
     * Composable function that shows all of the reservations of a specific site.
     *
     * @param navController: The navController of the app (passed for the sake of TopAppBar)
     * @param reservations a list of all of the reservations to be viewed by the user (Which reservations are determined
     * in the navHost)
     * */
    @SuppressLint("NotConstructor")
    @Composable
    fun OwnerReservationScreen(navController: NavController, reservations: List<Reservation>, ownerID: String, clickable: Boolean){

        TopAppBar(navController, "Reservations"){
            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)) {
                itemsIndexed(reservations) { index, reservation ->
                    ReservationRow(reservation, onClick = {if(clickable){
                     navController.navigate("reservationDetailScreen/$reservation")
                    }})
                    if (index < reservations.lastIndex) {
                        HorizontalDivider()
                    }
                }
            }
        }

    }
    /**
     * A composable row item that shows the details of a reservation. It shows the reserver's name and the start and end dates of the Reservation.
     * @param reservation the reservation being shown
     * */

    @Composable
    fun ReservationRow(reservation: Reservation, onClick: () -> Unit){
        val startDate = formatDateMillisToDMY(reservation.dates!!.first())
        val endDate = formatDateMillisToDMY(reservation.dates.last())
        val reserverName = entrantList.getEntrant(reservation.UserID!!)

        Column(modifier = Modifier.fillMaxWidth().clickable(onClick = {onClick()})) {
            Row(modifier = Modifier.padding(8.dp)) {
                Text("Start Date: $startDate")
                Spacer(modifier = Modifier.width(12.dp))
                Text("End Date: $endDate")
            }
            Row(modifier = Modifier.padding(8.dp)) {
                Text("Reserver: ${reserverName!!.firstName} ${reserverName.lastName}")
            }
        }


    }


    /**
     * Convert the date picker Long millis times to standard date format
     *
     * @param millis the millis to convert
     * @return the formatted date (dd/mm/yyyy)
     */
    fun formatDateMillisToDMY(millis: Long): String {
        val format = java.text.SimpleDateFormat("dd/MM/yyyy")
        format.timeZone = java.util.TimeZone.getTimeZone("UTC")
        return format.format(java.util.Date(millis))
    }

}