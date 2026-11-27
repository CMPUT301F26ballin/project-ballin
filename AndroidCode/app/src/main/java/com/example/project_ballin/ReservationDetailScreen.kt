package com.example.project_ballin

import android.annotation.SuppressLint
import android.widget.Button
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.project_ballin.entrant.Reservation
import com.example.project_ballin.reusableui.GeneralReportButton
import com.example.project_ballin.reusableui.TopAppBar
import java.text.DateFormat
import java.util.Locale

/**
 * A view class that shows the details of a reservation to an owner and gives them the option to reject
 * a reservation or send a message
 * */

class ReservationDetailScreen {

    // Source - https://stackoverflow.com/q/49551461
    // Posted by HelloCW
    // Retrieved 2026-11-21, License - CC BY-SA 3.0

    fun Long.toDateString(dateFormat: Int =  DateFormat.MEDIUM): String {
        val df = DateFormat.getDateInstance(dateFormat, Locale.getDefault())
        return df.format(this)
    }

    /**
     * The composable function that allows the user to view the active reservation's details
     * Along with giving them the option to cancel that reservation, or send the reserver a message
     *
     * @param navController the navController of the app
     * @param res: the reservation object currently being viewed
     * @param ownerID: The string of the ownerID
     * @param onCancel: The method that calls back to BallinApp that cancels the current reservation
     * */

    @SuppressLint("NotConstructor")
    @Composable
    fun ReservationDetailScreen(navController: NavController, res: Reservation, ownerID: String, onCancel: () -> Unit) {
        val entrantList = EntrantList()
        val campsiteList = CampsiteList()
        val site = campsiteList.getCampsite(res.campsiteID)
        val entrant = entrantList.getEntrant(res.userID!!)
        val confirmDeleteDialog = ConfirmDeleteDialog()
        var confirming = remember { false }

        val startDate = res.dates?.get(0)?.toDateString()
        val endDate = res.dates?.get(1)?.toDateString()

        TopAppBar(
            navController = navController,
            screenTitle = "Reservation Details"){
            Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {

                if(confirming){
                    confirmDeleteDialog.ConfirmDeleteDialog("${entrant!!.firstName}'s Reservation",
                        onConfirm = {
                            onCancel()
                            confirming = false
                            navController.popBackStack()
                        },
                        onCancel = {
                            confirming = false
                        }
                    )
                }

                Row(modifier = Modifier.padding(16.dp)) {
                    Text("Site Name: ${site!!.name}")
                    Spacer(modifier = Modifier.padding(12.dp))
                    Text("Start Date: $startDate")
                }
                Row(){
                    Text("Name: ${entrant!!.firstName} ${entrant.lastName}")
                    Spacer(modifier = Modifier.padding(12.dp))
                    Text("End Date: $endDate")
                }
                Row(modifier = Modifier.padding(16.dp)){
                    Button(onClick = {navController.navigate("messagingScreen/${res.userID}/${ownerID}")},
                        modifier = Modifier.fillMaxWidth()) {
                        Text("Message User")
                    }
                }
                Row(modifier = Modifier.padding(16.dp)){
                    Button(onClick = {confirming = true}){
                        Text("Cancel Reservation")
                    }
                }
                // Lets the owner report the entrant who made this reservation
                Row(modifier = Modifier.padding(16.dp)){
                    GeneralReportButton().ReportButton(
                        subjectReport = "Entrant",
                        clickedID = res.userID ?: "",
                        navController = navController,
                        label = "Report Entrant",
                        testTag = "reportEntrantButton"
                    )
                }
            }

        }
    }
}