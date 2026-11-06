package com.example.project_ballin

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
import com.example.project_ballin.entrant.entantui.ConfirmActionDialog
import com.example.project_ballin.reusableui.TopAppBar
import java.util.Date
import kotlin.uuid.Uuid


class MessageDetailScreen {
    /**
     * A composable that shows the details of a specific message
     * that was clicked on from the messages list screen.
     *
     * @param navController the navController of the app
     * @param message the message to show the details of
     * @param onDelete runs when the delete button is pressed
     */
    @Composable
    fun MessageDetail(
        navController: NavController,
        message: Message,
        onDelete: (Message) -> Unit
    ) {
        var messageToDelete by remember { mutableStateOf<Message?>(null) }

        TopAppBar(
            navController = navController,
            screenTitle = "Message Details"
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
                        MessageDetailRow(
                            "Sent By Owner ID",
                            message.ownerID,
                            "sentBy${message.ownerID}"
                        )
                        MessageDetailRow(
                            "Recieved By Entrant ID",
                            message.entrantID,
                            "recievedBy${message.entrantID}"
                        )
                        MessageDetailRow(
                            "Date Sent",
                            message.getFormattedDate(),
                            "dateSent${message.getFormattedDate()}"
                        )
                        MessageDetailRow(
                            "Message Content",
                            message.content,
                            "messageContent${message.content}"
                        )
                    }
                }

                Button(
                    onClick = {
                        messageToDelete = message
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.testTag("deleteMessage") // for UI testing
                ) {
                    Text("Delete Message")
                }

            }

            messageToDelete?.let { message ->
                ConfirmDeleteDialog(
                    itemDescription = "the message",
                    onConfirm = {
                        onDelete(message)
                        messageToDelete = null
                        navController.popBackStack()
                    },
                    onCancel = { messageToDelete = null }
                )
            }
        }
    }

    /**
     * Shows a specific part of the message and puts it in a row
     */
    @Composable
    fun <T> MessageDetailRow(descriptionText: String, content: T, testTag: String) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .testTag(testTag)
        ) {
            Text(
                text = "${descriptionText} : ${content}",
                color = Color(255, 255, 255)
            )
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun MessageDetailScreenPreview() {
        val navcontroller = rememberNavController()
        val sampleMessage = Message(
            "1",
            "1",
            "Hello, this is the content of the message",
            Date(),
            "1"
        )

        MessageDetail(navcontroller, sampleMessage, onDelete = {})
    }
}