package com.example.project_ballin.admin.adminui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.project_ballin.ConfirmDeleteDialog
import com.example.project_ballin.EntrantList
import com.example.project_ballin.Notification
import com.example.project_ballin.OwnerList
import com.example.project_ballin.reusableui.TopAppBar
import java.util.Date


class NotificationDetailScreen {

    val confirmDeleteDialog = ConfirmDeleteDialog()
    /**
     * A composable that shows the details of a specific notification
     * that was clicked on from the notifications list screen.
     *
     * @param navController the navController of the app
     * @param notification the notification to show the details of
     * @param onDelete runs when the delete button is pressed
     */
    @Composable
    fun NotificationDetail(
        navController: NavController,
        notification: Notification,
        onDelete: (Notification) -> Unit
    ) {
        var notificationToDelete by remember { mutableStateOf<Notification?>(null) }

        TopAppBar(
            navController = navController,
            screenTitle = "Notification Details"
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
            ) {

                val entrant = EntrantList().getEntrant(notification.recipientID)
                val owner = OwnerList().getOwner(notification.recipientID)

                // Trying to get name because its more useful information
                // at a glance for admin, rather than UUID unique ID- harder to decipher
                val userName =
                    when {
                        entrant != null ->
                            "${entrant.firstName} ${entrant.lastName}"
                        owner != null ->
                            "${owner.firstName} ${owner.lastName}"
                        else ->
                            "Anonymous User"
                    }
                
                NotificationDetailRows("Recipient Name", userName, "userName${userName}")
                NotificationDetailRows("Recipient ID", notification.recipientID, "recipientID${notification.recipientID}")
                NotificationDetailRows("Date Sent", notification.getFormattedDate(), "dateSent${notification.dateSent}")
                NotificationDetailRows("Notification Content", notification.content, "notificationContent${notification.content}")
                NotificationDetailRows("Notification ID", notification.ID, "notificationID${notification.ID}")
                Spacer(modifier = Modifier.height(68.dp))

                Button(
                    onClick = {
                        notificationToDelete = notification
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("removeNotification") // for UI testing
                ) {
                    Text("Remove Notification")
                }
            }

            notificationToDelete?.let { notification ->
                confirmDeleteDialog.ConfirmDeleteDialog(
                    itemDescription = "the notification",
                    onConfirm = {
                        onDelete(notification)
                        notificationToDelete = null
                        navController.popBackStack()
                    },
                    onCancel = { notificationToDelete = null }
                )
            }
        }
    }

    /**
     * Shows a specific part of the notification and puts it in a row
     */
    @Composable
    fun NotificationDetailRows(descriptionText: String, content: String, testTag: String) {
        Spacer(modifier = Modifier.height(34.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .testTag(testTag)
        ) {
            Text(
                text = "${descriptionText}:",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(content)
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun NotificationDetailScreenPreview() {
        val navcontroller = rememberNavController()

        val sampleNotification = Notification(
            "Hello, this is the content of the notification",
            System.currentTimeMillis(),
            "1", "1"
        )

        NotificationDetail(navcontroller, sampleNotification, onDelete = {})
    }
}