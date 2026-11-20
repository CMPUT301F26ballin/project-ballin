package com.example.project_ballin

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
import com.example.project_ballin.reusableui.TopAppBar
import java.util.Date


class MessageDetailScreen {

    val confirmDeleteDialog = ConfirmDeleteDialog()
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
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                MessageDetailRows("Sent By User ID", message.senderID, "sentBy${message.senderID}")
                MessageDetailRows("Received By User ID", message.recipientID, "recievedBy${message.recipientID}")
                MessageDetailRows("Date Sent", message.getFormattedDate(), "dateSent${message.getFormattedDate()}")
                MessageDetailRows("Message Content", message.content, "messageContent${message.content}")
                MessageDetailRows("Message ID", message.ID, "messageID${message.ID}")
                Spacer(modifier = Modifier.height(68.dp))

                Button(
                    onClick = {
                        messageToDelete = message
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("removeMessage") // for UI testing
                ) {
                    Text("Remove Message")
                }
            }

            messageToDelete?.let { message ->
                confirmDeleteDialog.ConfirmDeleteDialog(
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
     * Shows a specific part of the message and puts it in rows
     *
     * @param descriptionText the description of the message
     * @param content the content of the message
     * @param testTag the tag to use for testing
     */
    @Composable
    fun <T> MessageDetailRows(descriptionText: String, content: T, testTag: String) {
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
            Text("${content}")
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun MessageDetailScreenPreview() {
        val navController = rememberNavController()
        val sampleMessage = Message(
            "1",
            "1",
            "Hello, this is the content of the message",
            Date(),
            "1"
        )

        MessageDetail(navController, sampleMessage, onDelete = {})
    }
}