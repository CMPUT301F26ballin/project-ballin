package com.example.project_ballin

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.items
import java.util.Date

// Verify messages parameter type is correct
// Also debating on if I should add a mini description
// at top, to tell admin user what they can do on this screen

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
            Text("Click on specific message to view more details")

            Spacer(modifier = Modifier.height(34.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(messages) { message ->
                    MessageButtonRow(message, navController)
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
fun MessageButtonRow(message: Message, navController: NavController) {

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
            Text( "Message ${message.ID}")
            Text(
                text = "Date Sent: ${message.getFormattedDate()}",
                fontSize = 10.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MessageListScreenPreview() {
    val navController = rememberNavController()
    // still need to get messages list
    // rn just using a static sampleMessages list
    val sampleMessages = listOf(
        Message(1,1,"Hello", Date(), 1),
        Message(2,2,"Hi", Date(), 2),
        Message(3,3,"Bye", Date(), 3)
    )
    MessageListScreen(navController, sampleMessages)
}
