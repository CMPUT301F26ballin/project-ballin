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
                        Spacer(modifier = Modifier.height(12.dp))
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
                Text(formatDateMillisToDMY(notification.dateSent))
                Text(notification.content)
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