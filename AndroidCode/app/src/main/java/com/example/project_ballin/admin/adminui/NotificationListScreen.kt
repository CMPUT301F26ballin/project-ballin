package com.example.project_ballin.admin.adminui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.project_ballin.AdminSearch
import com.example.project_ballin.ConfirmDeleteDialog
import com.example.project_ballin.Message
import com.example.project_ballin.Notification
import com.example.project_ballin.reusableui.TopAppBar
import java.util.Date


class NotificationListScreen {

    val adminSearch = AdminSearch()
    val confirmDeleteDialog = ConfirmDeleteDialog()

    /**
     * Searches a notification by its content, recipient, and date sent.
     *
     * @param query the search text
     * @return true if the notification matches the search
     */
    fun Notification.matchesSearch(query: String): Boolean =
        adminSearch.matchesQuery(query, content, recipientID, getFormattedDate())
    /**
     * A composable that shows the overview of all the notifications
     *
     * @param navController the navController of the app
     * @param notifications the list of messages to iterate through
     */
    @Composable
    fun NotificationList(
        navController: NavController,
        notifications: List<Notification>,
        onDelete: (Notification) -> Unit
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
                    // What the admin typed in the search box (kept when the screen rotates)
                    var query by rememberSaveable { mutableStateOf("") }
                    val results = notifications.filter { it.matchesSearch(query) }

                        adminSearch.AdminSearchField(
                        query,
                        onQueryChange = { query = it },
                        placeholder = "Search notifications"
                    )

                    if (results.isEmpty()) {
                        Text(
                            "No notifications match \"${query.trim()}\".",
                            modifier = Modifier.padding(16.dp).testTag("noSearchResults")
                        )
                    } else {
                        ListNotifications(navController, results, onDelete = {onDelete(it)})
                    }
                }
            }
        }
    }

    /**
     * Shows all the notifications in a list.
     *
     * @param navController the navController of the app
     * @param notifications the list of notifications to iterate through
     * @param onDelete runs when the delete button is pressed
     */
    @Composable
    fun ListNotifications(navController: NavController, notifications: List<Notification>, onDelete: (Notification) -> Unit) {
        var notificationToDelete by remember {mutableStateOf<Notification?>(null)}

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            itemsIndexed(notifications) { index, notification ->
                NotificationButtonRow(index, notification, navController, onDelete = {notificationToDelete = notification})
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        notificationToDelete?.let { notification ->
            confirmDeleteDialog.ConfirmDeleteDialog(
                itemDescription = "the notification",
                onConfirm = {
                    onDelete(notification)
                    notificationToDelete = null
                },
                onCancel = { notificationToDelete = null }
            )
        }
    }

    /**
     * Shows the general overview of a specific notification,
     * it'll be a clickable button, showing ID and date sent only.
     *
     * @param index the index of the notification in the list
     * @param notification the notification to show
     * @param navController the navController of the app
     * @param onDelete runs when the delete button is pressed
     */
    @Composable
    fun NotificationButtonRow(
        index: Int,
        notification: Notification,
        navController: NavController,
        onDelete: (Notification) -> Unit
    ) {

        Button(
            onClick = {
                navController.navigate("AdminNotificationDetails/${notification.ID}")
            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .testTag("checkNotification${index + 1}") // for UI testing
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    // Arbitrary indexes, so no inherent order to them, just for sake of ui
                    // I didn't want to display the Uuid of Notification
                    Text(
                        text = "Notification ${index + 1}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text("Date Sent: ${notification.getFormattedDate()}")
                    Text("Recipient: ${notification.recipientID}")
                }

                Button(
                    onClick = { onDelete(notification) },
                    modifier = Modifier
                        .testTag("removeMessage_${notification.ID}"), // for UI testing
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    )
                ) {
                    Text("Remove")
                }
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
        NotificationList(navController, sampleNotifications, onDelete = {})
    }
}