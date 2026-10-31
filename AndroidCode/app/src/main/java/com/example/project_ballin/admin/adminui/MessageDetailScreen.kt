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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import java.util.Date
import kotlin.uuid.Uuid

// NOT COMPLETED YET
// VERIFY onDelete parameter works properly
// VERIFY that the delete message brings user to confirmDeleteDialog

/**
 * A composable that shows the details of a specific message
 * that was clicked on from the messages list screen.
 *
 * @param navController the navController of the app
 * @param message the message to show the details of
 * @param onDelete runs when the delete button is pressed
 */
@Composable
fun MessageDetailScreen(
    navController: NavController,
    message: Message,
    onDelete: (Message) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

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

            Spacer(modifier = Modifier.height(34.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(fraction = 0.5f)
                    .padding(16.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(10.dp)
                    )
            ) {
                Column {
                    MessageDetailRow("Sent By Owner ID", message.ownerID)
                    MessageDetailRow("Recieved By Entrant ID", message.entrantID)
                    MessageDetailRow("Date Sent", message.getFormattedDate())
                    MessageDetailRow("Message Content", message.content)
                }
            }

            Button(
                onClick = {
                    showDialog = true
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.testTag("deleteMessage") // for UI testing
            ) {
                Text("Delete Message")
            }

            if (showDialog) {
                ConfirmDeleteDialog(
                    itemDescription = "the message ${message.ID}",
                    onConfirm = {
                        showDialog = false
                        onDelete(message)
                    },
                    onCancel = { showDialog = false }
                )
            }
        }
    }
}

/**
 * Shows a specific part of the message and puts it in a row
 */
@Composable
fun <T> MessageDetailRow(descriptionText: String, content: T) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Text(
            text = "${descriptionText} : ${content}",
            color = Color(255,255,255)
        )
    }
}

@OptIn(kotlin.uuid.ExperimentalUuidApi::class)
@Preview(showBackground = true)
@Composable
fun MessageDetailScreenPreview() {
    val navcontroller = rememberNavController()
    val uuidRandom: Uuid = Uuid.random()
    val sampleMessage = Message(
        uuidRandom,
        uuidRandom,
        "Hello, this is the content of the message",
        Date(),
        uuidRandom)

    MessageDetailScreen(navcontroller, sampleMessage, onDelete = {})
}