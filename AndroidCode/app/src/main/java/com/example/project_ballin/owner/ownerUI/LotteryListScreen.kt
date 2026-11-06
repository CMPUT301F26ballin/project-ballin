package com.example.project_ballin.owner.ownerUI

import android.annotation.SuppressLint
import com.example.project_ballin.lottery.Lottery
import com.example.project_ballin.reusableui.TopAppBar

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.project_ballin.Campsite
import com.example.project_ballin.lottery.LotteryList

class LotteryListScreen() {
    /**
     * The screen to view all of the owners lotteries
     * and the add lottery button.
     *
     * @param navController the navController of the app
     * @param ownerID the ID of the owner user accessing the list
     * @param getOwnerLotteries the function to get the lotteries of the owner
     * @param getCampsite the function to get a campsite by ID
     * */
    @SuppressLint("NotConstructor")
    @Composable
    fun LotteryListScreen(
        navController: NavController,
        ownerID: String,
        getOwnerLotteries: (String) -> List<Lottery>,
        getCampsite: (String) -> Campsite?,
    ) {
        val lotteries = getOwnerLotteries(ownerID)

        TopAppBar(navController, "My Lotteries") {
            LotteryListView(navController, ownerID, lotteries, getCampsite)
        }
    }

    /**
     * Holds the list of all of the lotteries
     * and the new lottery button
     * @param navController the navController of the app
     * @param ownerID the ID of the owner user accessing the list
     * @param lotteries the list of lotteries to display for this owner
     * @param getCampsite the function to get a campsite by ID
     */
    @Composable
    fun LotteryListView(
        navController: NavController,
        ownerID: String,
        lotteries: List<Lottery>,
        getCampsite: (String) -> Campsite?
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            item {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    NewLotteryButton(navController, ownerID)
                }
            }
            if (lotteries.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier.fillParentMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No active lotteries")
                    }
                }
            }

            itemsIndexed(lotteries) { index, lottery ->
                val ID = lottery.lotteryID
                Spacer(modifier = Modifier.height(16.dp))
                LotteryItem(
                    lottery,
                    onClick = { navController.navigate("LotteryDetailScreen/$ownerID?LotteryID=$ID") },
                    getCampsite = getCampsite
                )
            }
        }
    }

    /**
     * Create new lottery button
     *
     * @param navController the navController of the app
     * @param ownerID the ID of the owner user accessing the list
     */
    @Composable
    fun NewLotteryButton(
        navController: NavController,
        ownerID: String
    ) {
        Button(onClick = { navController.navigate("LotteryDetailScreen/$ownerID") })
        {
            Text("Create new lottery")
        }
    }

    /**
     * A single row of the lottery list
     *
     * @param lottery the lottery of the entry,
     * @param onClick what to do when clicked
     * @param getCampsite the function to get a campsite by ID
     * */

    @Composable
    fun LotteryItem(
        lottery: Lottery,
        onClick: () -> Unit,
        getCampsite: (String) -> Campsite?
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .border(2.dp, Color.Black)
                .clickable { onClick() }
                .padding(16.dp)
        ) {
            Column {
                Text(
                    "Lottery period: ${formatDateMillisToDMY(lottery.registrationStart!!)} " +
                            "to ${formatDateMillisToDMY(lottery.registrationEnd!!)}"
                )
                Text(
                    "Booking period: ${formatDateMillisToDMY(lottery.bookingStart!!)} " +
                            "to ${formatDateMillisToDMY(lottery.bookingEnd!!)}"
                )
                Spacer(modifier = Modifier.height(8.dp))

                // list of the sites
                if (lottery.associatedSites.isEmpty()) Text("No sites associated")
                lottery.associatedSites.forEach { id ->
                    val campsite = getCampsite(id)
                    if (campsite != null) Text("${campsite.name} - ${campsite.description}")
                    else Text("Deleted campsite")
                }
                Spacer(modifier = Modifier.height(16.dp))
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