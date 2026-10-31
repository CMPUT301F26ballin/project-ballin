package com.example.project_ballin.admin.adminui

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
import com.example.project_ballin.TopAppBar
import java.util.Date
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

// Verify notifications parameter type is correct

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
        screenTitle = "Notifications Home",
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Spacer(modifier = Modifier.height(34.dp))

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
    }
}

/**
 * Shows the general overview of a specific notification,
 * it'll be a clickable button, showing ID and date sent only.
 */
@Composable
fun NotificationButtonRow(index: Int, notification: Notification, navController: NavController) {

    Button(
        onClick = {
            navController.navigate("NotificationDetail")
        },
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .testTag("checkNotificationButton") // for UI testing
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

@OptIn(ExperimentalUuidApi::class)
@Preview(showBackground = true)
@Composable
fun NotificationListScreenPreview() {
    val navController = rememberNavController()
    val uuidRandomID: Uuid = Uuid.random()
    val sampleNotifications = listOf(
        Notification("Hello", Date(), uuidRandomID),
        Notification("Hi", Date(), uuidRandomID),
    )
   NotificationListScreen(navController, sampleNotifications)
}