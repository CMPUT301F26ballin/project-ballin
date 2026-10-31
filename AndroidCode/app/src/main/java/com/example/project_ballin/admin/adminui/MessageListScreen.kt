package com.example.project_ballin

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
import java.util.Date
import kotlin.uuid.Uuid

// Verify messages parameter type is correct

/**
 * A composable that shows the overview of all the messages
 * sent by an owner to an entrant.
 *
 * @param navController the navController of the app
 * @param messages the list of messages to iterate through
 */
@Composable
fun MessageListScreen(
    navController: NavController,
    messages: List<Message>
) {
    TopAppBar(
        navController = navController,
        screenTitle = "Messages Home",
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
                itemsIndexed(messages) { index, message ->
                    MessageButtonRow(index, message, navController)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

/**
 * Shows the general overview of a specific message,
 * it'll be a clickable button, showing ID and date sent only.
 */
@Composable
fun MessageButtonRow(index: Int, message: Message, navController: NavController) {

    Button(
        onClick = {
            navController.navigate("MessageDetail")
        },
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .testTag("checkMessageButton") // for UI testing
            .width(250.dp)
            .height(85.dp)
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Arbitrary indexes, so no inherent order to them, just for sake of ui
            // I didn't want to display the Uuid of Message
            Text( "Message ${index + 1}")
            Text(
                text = "Date Sent: ${message.getFormattedDate()}",
                fontSize = 10.sp
            )
        }
    }
}

@OptIn(kotlin.uuid.ExperimentalUuidApi::class)
@Preview(showBackground = true)
@Composable
fun MessageListScreenPreview() {
    val navController = rememberNavController()

    val uuidRandomOwnerID: Uuid = Uuid.random()
    val uuidRandomEntrantID: Uuid = Uuid.random()
    val uuidRandomID: Uuid = Uuid.random()
    // using the same uuid for all of them btw just for preview
    // still need to get messages list
    // rn just using a static sampleMessages list
    val sampleMessages = listOf(
        Message(uuidRandomOwnerID,uuidRandomEntrantID,"Hello", Date(), uuidRandomID),
        Message(uuidRandomOwnerID,uuidRandomEntrantID,"Hi", Date(), uuidRandomID),
    )
    MessageListScreen(navController, sampleMessages)
}
