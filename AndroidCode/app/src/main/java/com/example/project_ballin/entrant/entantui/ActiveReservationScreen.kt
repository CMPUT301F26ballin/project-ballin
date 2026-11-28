package com.example.project_ballin.entrant.entantui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.room.util.TableInfo
import com.example.project_ballin.CampsiteList
import com.example.project_ballin.ReservationList
import com.example.project_ballin.entrant.Entrant
import com.example.project_ballin.entrant.Reservation
import com.example.project_ballin.lottery.Lottery
import com.example.project_ballin.lottery.LotteryList
import com.example.project_ballin.reusableui.TopAppBar
import java.text.DateFormat
import java.util.Locale


class ActiveReservationScreen {


    // Converts long to date string
    // Source - https://stackoverflow.com/q/49551461
    // Posted by HelloCW
    // Retrieved 2026-11-21, License - CC BY-SA 3.0

    fun Long.toDateString(dateFormat: Int =  DateFormat.MEDIUM): String {
        val df = DateFormat.getDateInstance(dateFormat, Locale.getDefault())
        return df.format(this)
    }


    /**
     * The entrant active reservations page
     * Entrants can look at their active reservations
     *
     * @param navController the navigation controller for the app
     * @param entrant the entrant
     * @param campsiteList the list of campsites
     */
    @Composable
    fun ActiveReservations(navController: NavController, entrant: Entrant, campsiteList: CampsiteList, reservationList: ReservationList, lotteryList: LotteryList, onLeaveLottery: (Lottery, Entrant) -> Unit, onLeaveReservation: (Reservation) -> Unit){
        var lotteryToLeave by remember { mutableStateOf<Lottery?>(null) }
        var reservationToLeave by remember { mutableStateOf<Reservation?>(null) }
        var refresh by remember { mutableIntStateOf(0) }

        val currentReservations = remember(refresh) {
            reservationList.getCurrentReservationsForEntrant(entrant.userID)
        }
        val activeLotteries = remember(refresh) {
            lotteryList.getEntrantLotteries(entrant.userID)
        }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            TopAppBar(
                navController,
                "Active Reservations"
            ) {
                LazyColumn(
                    modifier = Modifier.padding(16.dp, 0.dp)
                ){
                    if(currentReservations.isNotEmpty()){
                        item{

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = "Your current reservations:",
                                style = MaterialTheme.typography.titleLarge,

                                )

                            HorizontalDivider(modifier = Modifier.padding(0.dp,0.dp, 0.dp, 16.dp))
                        }

                        items(currentReservations) { reservation ->
                            CurrentReservationCard(entrant, reservation, campsiteList, navController, { reservationToLeave = reservation })

                            Spacer(modifier = Modifier.height(16.dp))
                        }


                    }


                    if(activeLotteries.isNotEmpty()){

                        item{

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = "Your active lotteries:",
                                style = MaterialTheme.typography.titleLarge,

                                )

                            HorizontalDivider(modifier = Modifier.padding(0.dp,0.dp, 0.dp, 16.dp))
                        }

                        items(activeLotteries) { lottery ->
                            ActiveLotteryCard(lottery, entrant, campsiteList, navController, { lotteryToLeave = lottery })

                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        }


        //Looked up how to make a dialog in compose
        //https://developer.android.com/develop/ui/compose/components/dialog#dialog-composable
        lotteryToLeave?.let { lottery ->
            ConfirmActionDialog().ConfirmAction(
                "Are you sure you want to leave this waiting list?",
                confirm = { confirmed ->
                    if (confirmed) {
                        onLeaveLottery(lottery, entrant)
                        refresh++
                    }
                    lotteryToLeave = null
                }
            )
        }

        reservationToLeave?.let { reservation ->
            ConfirmActionDialog().ConfirmAction(
                "Are you sure you want to cancel this reservation?",
                confirm = { confirmed ->
                    if (confirmed) {
                        onLeaveReservation(reservation)
                        refresh++
                    }
                    reservationToLeave = null
                }
            )
        }
    }


    /**
     * The entrant active lottery card
     * Entrants can look at their active lotteries and leave them
     * @param lottery the lottery
     * @param entrant the entrant
     * @param campsiteList the list of campsites
     * @param navController the navigation controller for the app
     * @param onLeaveClick the function to run when the leave button is clicked
     */
    @Composable
    fun ActiveLotteryCard(lottery: Lottery, entrant: Entrant, campsiteList: CampsiteList, navController: NavController, onLeaveClick: () -> Unit){
        val campsites = lottery.associatedSites
        val startDate = lottery.registrationStart?.toDateString()
        val endDate = lottery.registrationEnd?.toDateString()


        Card(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            if (entrant.userID in lottery.waitList) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .testTag("Active Lottery Card")
                )
                {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                    ){

                        Text(
                            text = "You are registered in a lottery for:",
                            style = MaterialTheme.typography.titleLarge
                        )


                        for (site in campsites) {

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "${campsiteList.getCampsite(site)?.name}",
                                style = MaterialTheme.typography.titleLarge
                            )

                            Column{

                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ){
                                    Column {


                                        Text(
                                            text = "Details:",
                                            style = MaterialTheme.typography.titleMedium
                                        )

                                        Row(
                                            modifier = Modifier.padding(16.dp, 0.dp)
                                        ){
                                            Column {
                                                Text(
                                                    text = "- Location: ${
                                                        campsiteList.getCampsite(
                                                            site
                                                        )?.locationName
                                                    }" ,
                                                    style = MaterialTheme.typography.bodyLarge
                                                )
                                                Text(
                                                    text = "- Occupancy: ${
                                                        campsiteList.getCampsite(
                                                            site
                                                        )?.occupancy
                                                    }",
                                                    style = MaterialTheme.typography.bodyLarge

                                                )

                                                Text(
                                                    text = "- Price: $${
                                                        "%.2f".format(
                                                            campsiteList.getCampsite(
                                                                site
                                                            )?.price
                                                        )
                                                    }/night",
                                                    style = MaterialTheme.typography.bodyLarge

                                                )

                                                if(campsiteList.getCampsite(site)?.rating == null){
                                                    Text(
                                                        text = "- No ratings yet",
                                                        style = MaterialTheme.typography.bodyLarge
                                                    )
                                                }
                                                else{
                                                    Text(
                                                        "- Rating: ${
                                                            "%.2f".format(
                                                                campsiteList.getCampsite(
                                                                    site
                                                                )?.rating
                                                            )
                                                        }/5",
                                                        style = MaterialTheme.typography.bodyLarge

                                                    )
                                                }

                                            }

                                        }
                                    }

                                    Column(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalAlignment = Alignment.End,
                                        verticalArrangement = Arrangement.SpaceEvenly
                                    ){
                                        Button(
                                            onClick = {
                                                navController.navigate("CampsiteDetailScreen/${campsiteList.getCampsite(site)?.ID}/${entrant.userID}")

                                            },
                                            modifier = Modifier
                                                .testTag("More Details Button"),



                                        ) {
                                            Text("More details")
                                        }
                                    }
                                }
                            }
                        }

                        HorizontalDivider(modifier = Modifier.padding(0.dp, 16.dp))

                        Row{
                            Text(
                                text = "Start: $startDate\nEnd: $endDate",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }

                        HorizontalDivider(modifier = Modifier.padding(0.dp, 16.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ){


                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.End
                            )
                            {

                                Button(
                                    onClick = {

                                    },
                                    modifier = Modifier
                                        .testTag("Messages Button")
                                        .fillMaxWidth()

                                ) {
                                    Text("Messages")
                                }

                                Button(
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.error
                                    ),
                                    onClick = onLeaveClick
                                    ,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("Leave Waiting List Button")
                                ) {
                                    Text("Leave waiting list")
                                }
                            }
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
    fun CurrentReservationCard(entrant: Entrant, reservation: Reservation, campsiteList: CampsiteList, navController: NavController, onCancelClick: () -> Unit){
        val campsite = campsiteList.getCampsite(reservation.campsiteID)
        val startDate = reservation.dates?.get(0)?.toDateString()
        val endDate = reservation.dates?.get(1)?.toDateString()

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("Current Reservation Card")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ){

                Row {
                    Text(
                        text = "Upcoming reservation: ${campsite?.name}",
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                Row{
                    Text(
                        text = "Check-in: $startDate\nCheck-out: $endDate",
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                HorizontalDivider(modifier = Modifier.padding(0.dp, 16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Column{
                        Row{
                            Text(
                                text = "Details:",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                        Row(
                            modifier = Modifier.padding(16.dp, 0.dp)
                        ){
                            Text(
                                text =  "- Location: ${campsite?.locationName}\n" +
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

                Button(
                    onClick = {

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("Messages Button")

                ) {
                    Text("Messages")
                }

                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    ),
                    onClick = onCancelClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("Cancel Reservation Button")


                ) {
                    Text("Cancel reservation")
                }
            }
        }
    }
}