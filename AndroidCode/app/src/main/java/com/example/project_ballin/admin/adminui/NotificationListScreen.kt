package com.example.project_ballin.admin.adminui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.lazy.itemsIndexed
import com.example.project_ballin.Notification
import com.example.project_ballin.reusableui.TopAppBar
import java.util.Date



/**
 * A composable that shows the overview of all the notifications
 *
 * @param navController the navController of the app
 * @param notifications the list of messages to iterate through
 */
@Composable
fun NotificationListScreen(
    navController: NavController,
    notifications: List<Notification>
) {
    TopAppBar(
        navController = navController,
        screenTitle = "Notifications",
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Spacer(modifier = Modifier.height(34.dp))

            if (notifications.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No notifications to browse through")
                }
            } else {
                ListNotifications(navController, notifications)
            }
        }
    }
}

@Composable
fun ListNotifications(navController: NavController, notifications: List<Notification>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        itemsIndexed(notifications) { index, notification->
            NotificationButtonRow(index, notification, navController)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

/**
 * Shows the general overview of a specific notification,
 * it'll be a clickable button, showing ID and date sent only.
 *
 * @param index the index of the notification in the list
 * @param notification the notification to show
 * @param navController the navController of the app
 */
@Composable
fun NotificationButtonRow(index: Int, notification: Notification, navController: NavController) {

    Button(
        onClick = {
            navController.navigate("AdminNotificationDetails/${notification.ID}")
        },
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .testTag("checkNotification${index+1}") // for UI testing
            .width(250.dp)
            .height(85.dp)
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Arbitrary indexes, so no inherent order to them, just for sake of ui
            // I didn't want to display the Uuid of Notification
            Text( "Notification ${index + 1}")
            Text(
                text = "Date Sent: ${notification.getFormattedDate()}",
                fontSize = 10.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotificationListScreenPreview() {
    val navController = rememberNavController()
    val sampleNotifications = listOf(
        Notification("Hello", Date(), "1", "1"),
        Notification("Hi", Date(), "2", "2"),
    )
   NotificationListScreen(navController, sampleNotifications)
}