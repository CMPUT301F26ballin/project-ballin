package com.example.project_ballin.reusableui

import android.R
import android.annotation.SuppressLint
import android.graphics.fonts.FontStyle
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.project_ballin.Notification
import com.example.project_ballin.reusableui.TopAppBar
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.text.font.FontWeight
import com.example.project_ballin.Message

class MessageChatScreen() {

    @SuppressLint("NotConstructor")
    @Composable
    fun MessageChatScreen(
        navController: NavController,
        userID: String,
        theirID: String,
        sendMessage: (String, String) -> Unit,  // recipient userID, message
        yourMessages: List<Message>,
        theirMessages: List<Message>,
        theirName: String
    ) {
        // sort all the messages by time
        val allMessages = yourMessages + theirMessages
        val sortedMessages = allMessages.sortedByDescending(){ it.dateSent }.reversed()

        var messageContent by remember { mutableStateOf("") }

        TopAppBar(navController, "Messaging ${theirName}") {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
            ){
                items(sortedMessages, key = {it.ID}) { msg ->
                    MessageCard(msg, msg.senderID == userID)
                }
            }
            GeneralReportButton().ReportButton(
                subjectReport = "Entrant",
                clickedID = userID,
                navController = navController,
                label = "Report Entrant",
                testTag = "reportEntrantButton"
            )

            Row(modifier = Modifier.padding(16.dp)){
                OutlinedTextField(
                    value = messageContent,
                    onValueChange = { messageContent = it },
                    label = { Text("Your Message:") },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("messageContent"),
                )
            }

            Row(modifier = Modifier.padding(16.dp).fillMaxWidth().testTag("sendMessageButton")){
                Button(onClick = {
                    sendMessage(theirID, messageContent)
                    navController.popBackStack()

                }){
                    Text("Send Message")
                }
            }
        }
    }

    @SuppressLint("NotConstructor")
    @Composable
    fun MessageCard(
        message: Message,
        youSent: Boolean
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = if (youSent) { Arrangement.End } else { Arrangement.Start }
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = if (youSent){
                        MaterialTheme.colorScheme.surfaceVariant
                    } else {
                        MaterialTheme.colorScheme.surfaceVariant
                    }
                ),
            ){
                Column(modifier = Modifier.padding(12.dp)
                ) {
                    Text(message.dateSent.toString(), fontWeight = FontWeight.Bold)
                    Text(message.content)
                }
            }
        }
    }
}