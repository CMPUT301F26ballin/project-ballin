package com.example.project_ballin.owner.ownerUI

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.project_ballin.Notification
import com.example.project_ballin.reusableui.TopAppBar
import androidx.compose.foundation.lazy.items

class OwnerNotificationListScreen() {

    /**
     * Screen to send notifications to entrants in a lottery.
     * Notifications are sent to one lottery for all entrants to read.
     *
     * @param navController the navcontroller of the app
     * @param userID the userID of the owner sending the message
     * @param lotteryID the lotteryID of the lottery we're sending notifcations to
     * @param sendNotification the function to send a notification
     */

    @SuppressLint("NotConstructor")
    @Composable
    fun OwnerNotificationListScreen(
        navController: NavController,
        userID: String,
        notifications: List<Notification>
    ) {
        // show newest ones first
        val sortedNotifs = notifications.sortedByDescending { it.dateSent }

        TopAppBar(navController, "Sent Notifications") {
            if (sortedNotifs.isEmpty()){
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("You haven't sent any notifications yet")
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ){
                    items(sortedNotifs, key = {it.ID}) { notif ->
                        NotificationDetailBox(notif)
                    }
                }
            }
        }
    }

    @SuppressLint("NotConstructor")
    @Composable
    fun NotificationDetailBox(
        notification: Notification
    ){
        Card(
            modifier = Modifier
                .fillMaxWidth()
        ){
            Column(modifier = Modifier.padding(16.dp)) {
                Text(notification.dateSent.toString())
                Text(notification.content)
            }
        }
    }
}