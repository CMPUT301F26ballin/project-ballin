package com.example.project_ballin.owner.ownerUI

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.project_ballin.reusableui.TopAppBar

class NotificationSendScreen() {

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
    fun NotificationSendScreen(
        navController: NavController,
        userID: String,
        lotteryID: String,
        sendNotification: (String, String) -> Boolean
    ) {
        val longestMessage = 500
        var message by remember { mutableStateOf("") }

        TopAppBar(navController, "Lottery Notification") {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text("lottery notifications")

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = message,
                    onValueChange = {
                        if (it.length <= longestMessage) message = it
                    },
                    label = { Text("Notification message") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .testTag("NotificationTextEntry")
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("SendNotificationButton"),
                    onClick = {
                        if (!message.isBlank()) {
                            sendNotification(lotteryID, message)
                            navController.popBackStack()
                        }
                    }
                ) {
                    Text("Send notification")
                }
            }
        }
    }
}