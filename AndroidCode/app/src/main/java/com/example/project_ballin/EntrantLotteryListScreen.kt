package com.example.project_ballin

import android.adservices.topics.Topic
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.project_ballin.entrant.Entrant
import com.example.project_ballin.lottery.Lottery
import com.example.project_ballin.reusableui.TopAppBar
import org.jetbrains.annotations.TestOnly


/**
 * A screen where entrants view all the active lotteries of a given campsite
 *
 * */
class EntrantLotteryListScreen {


    /**
     * The composable screen where entrants can view and enroll in lotteries based on a campsite.
     *
     * @param navController: the app's navcontroller
     * @param entrantID: the id of the user
     * @param campsiteID: the id of the campsite whose lotteries are being accessed
     * @param getLotteries: the function that gets the lotteries of the campsite
     * @param onEnroll: the function that enrolls a user in a lottery
     *
     * */


    @SuppressLint("NotConstructor")
    @Composable
    fun EntrantLotteryListScreen(navController: NavController,entrantID: String, campsiteID: String, getLotteries: (String) -> List<Lottery>, onEnroll:(String, String) -> Unit){

        val lotteries = remember { getLotteries(campsiteID) }


        TopAppBar(navController, "Lotteries")
        {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                itemsIndexed(lotteries) { index, lottery ->

                    ActiveLotteryCard(
                        lottery,
                        entrantID,
                        CampsiteList(),
                        onEnrollClick = { onEnroll(lottery.lotteryID, entrantID) })

                }
            }
        }
    }
    /**
     * A card composable for an active lottery.
     *
     * @param lottery: the lottery being displayed
     * @param entrantID: the ID of the entrant used to check if they are already there
     * @param campsiteList: the global list of campsites (used to list the names of camps in a lottery)
     * @param onEnrollClick: The function defining what to do on enroll
     *
     * */

    @Composable
    fun ActiveLotteryCard(lottery: Lottery, entrantID: String, campsiteList: CampsiteList, onEnrollClick: () -> Unit){

        var enrolled by remember { mutableStateOf(entrantID in lottery.waitList) }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .testTag("Active Lottery Card")
            )
            {
                Text(text = "Sites:",
                    style = MaterialTheme.typography.titleMedium
                )
                lottery.associatedSites.forEach{site ->
                    Text(
                        text = "${
                            campsiteList.getCampsite(
                                site
                            )?.name
                        }",
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    "Lottery period: ${formatDateMillisToDMY(lottery.registrationStart!!)} " +
                            "to ${formatDateMillisToDMY(lottery.registrationEnd!!)}"
                )
                Text(
                    "Booking period: ${formatDateMillisToDMY(lottery.bookingStart!!)} " +
                            "to ${formatDateMillisToDMY(lottery.bookingEnd!!)}"
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    if(enrolled){
                        Text("Already enrolled in lottery")
                    }
                    else if(lottery.occupancy != null && lottery.waitList.size >= lottery.occupancy!!){
                        Text("Lottery full")
                    }
                    else{

                        Button(
                            colors = ButtonDefaults.buttonColors(),
                            onClick = {
                                onEnrollClick()
                                enrolled = true
                            },
                            modifier = Modifier
                                .testTag("Leave Waiting List Button")
                        ) {
                            Text("Enroll")
                        }
                    }

                }
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