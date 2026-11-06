package com.example.project_ballin.admin.adminui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.project_ballin.ConfirmDeleteDialog
import com.example.project_ballin.Notification
import com.example.project_ballin.entrant.entantui.ConfirmActionDialog
import com.example.project_ballin.reusableui.TopAppBar
import java.util.Date
import kotlin.uuid.Uuid


class NotificationDetailScreen {
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
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "DETAILS",
                    fontSize = 34.sp,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(fraction = 0.8f)
                        .padding(16.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(10.dp)
                        )
                ) {
                    Column {
                        NotificationDetailRow("Date Sent", notification.getFormattedDate(), "dateSent${notification.getFormattedDate()}")
                        NotificationDetailRow("Notification Content", notification.content, "notificationContent${notification.content}")
                        NotificationDetailRow("Notification ID", notification.ID, "notificationID${notification.ID}")
                        NotificationDetailRow("Recipient ID", notification.recipientID, "recipientID${notification.recipientID}")
                    }
                }

                Button(
                    onClick = {
                        notificationToDelete = notification
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.testTag("deleteNotification") // for UI testing
                ) {
                    Text("Delete Notification")
                }
            }

            notificationToDelete?.let { notification ->
                ConfirmDeleteDialog(
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
    fun <T> NotificationDetailRow(descriptionText: String, content: T, testTag: String) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .testTag(testTag)
        ) {
            Text(
                text = "$descriptionText : $content",
                color = Color(255,255,255)
            )
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun NotificationDetailScreenPreview() {
        val navcontroller = rememberNavController()

        val sampleNotification = Notification(
            "Hello, this is the content of the notification",
            Date(),
            "1", "1"
        )

        NotificationDetail(navcontroller, sampleNotification, onDelete = {})
    }
}