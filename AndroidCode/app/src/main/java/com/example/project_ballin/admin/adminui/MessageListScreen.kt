package com.example.project_ballin

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
import com.example.project_ballin.reusableui.TopAppBar
import java.util.Date
import kotlin.uuid.Uuid



class MessageListScreen {
    val adminSearch = AdminSearch()

    /**
     * Searches a message by its content, sender, receiver, and date sent.
     *
     * @param query the search text
     * @return true if the message matches the search
     */
    fun Message.matchesSearch(query: String): Boolean =
        adminSearch.matchesQuery(query, content, ownerID, entrantID, getFormattedDate())

    /**
     * A composable that shows the overview of all the messages
     * sent by an owner to an entrant.
     *
     * @param navController the navController of the app
     * @param navController the navController of the app
     * @param messages the list of messages to iterate through
     */
    @Composable
    fun MessageList(
        navController: NavController,
        messages: List<Message>
    ) {
        TopAppBar(
            navController = navController,
            screenTitle = "Messages",
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Spacer(modifier = Modifier.height(34.dp))

                if (messages.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No messages to browse through")
                    }
                } else {
                    // What the admin typed in the search box (kept when the screen rotates)
                    var query by rememberSaveable { mutableStateOf("") }
                    val results = messages.filter { it.matchesSearch(query) }

                        adminSearch.AdminSearchField(
                        query,
                        onQueryChange = { query = it },
                        placeholder = "Search messages"
                    )

                    if (results.isEmpty()) {
                        Text(
                            "No messages match \"${query.trim()}\".",
                            modifier = Modifier.padding(16.dp).testTag("noSearchResults")
                        )
                    } else {
                        ListMessages(navController, results)
                    }
                }

            }
        }
    }

    /**
     * Shows all the messages in a list.
     *
     * @param navController the navController of the app
     * @param messages the list of messages to iterate through
     */
    @Composable
    fun ListMessages(navController: NavController, messages: List<Message>) {
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

    /**
     * Shows the general overview of a specific message,
     * it'll be a clickable button, showing ID and date sent only.
     *
     * @param index the index of the message in the list
     * @param message the message to show
     * @param navController the navController of the app
     */
    @Composable
    fun MessageButtonRow(index: Int, message: Message, navController: NavController) {

        Button(
            onClick = {
                navController.navigate("AdminMessageDetails/${message.ID}")
            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .testTag("checkMessage${message.ID}") // for UI testing
                .width(250.dp)
                .height(85.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Message ${index + 1}")
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

        val sampleMessages = listOf(
            Message("1", "1", "Hello", Date(), "1"),
            Message("2", "2", "Hi", Date(), "2")
        )
        MessageList(navController, sampleMessages)
    }
}
