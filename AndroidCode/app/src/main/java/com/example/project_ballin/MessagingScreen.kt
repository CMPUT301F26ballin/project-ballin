package com.example.project_ballin

import android.annotation.SuppressLint
import androidx.camera.video.internal.muxer.Muxer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onVisibilityChangedNode
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.project_ballin.owner.ownerUI.OwnerReservationScreen
import com.example.project_ballin.reusableui.GeneralReportButton
import com.example.project_ballin.reusableui.TopAppBar

/**
 * The screen class that shows a screen allowing an Owner to message a user.
 * */

class MessagingScreen {
    /**
     * The composable method showing the objects allowing the logged in owner
     * to message a specific entrant
     *
     * @param navController the navController of the entire app
     * @param ownerID the Uuid of the messaging owner
     * @param userID the Uuid of the messaged entrant
     * */

    @SuppressLint("NotConstructor")
    @Composable
    fun MessagingScreen(navController: NavController, senderID: String, receiverID: String, receiverName: String,
                        onAddMessage:(senderID: String, receiverID: String, messageContent: String) -> Unit,
                        onGetMessages:(senderID: String, receiverID: String) -> List<Message>){
        var messageContent by remember { mutableStateOf("") }
        var messages = onGetMessages(senderID, receiverID)

        TopAppBar(navController, "Messaging ${receiverName}"){
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
                // Lets the owner report the entrant they are messaging
                GeneralReportButton().ReportButton(
                    subjectReport = "Entrant",
                    clickedID = senderID,
                    navController = navController,
                    label = "Report Entrant",
                    testTag = "reportEntrantButton"
                )

                Row(modifier = Modifier.padding(16.dp)){
                    LazyColumn(modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)) {
                        itemsIndexed(messages){index, message ->
                            if(message.senderID == senderID){
                                Row(modifier = Modifier
                                    .width(100.dp)
                                    .padding(16.dp),
                                    horizontalArrangement = Arrangement.End){
                                    Card(colors = CardColors(
                                        containerColor = Color.Green,
                                        contentColor = Color.White,
                                        disabledContainerColor = Color.Black,
                                        disabledContentColor = Color.Black
                                    )){
                                        Text(message.content)
                                        Text(java.text.SimpleDateFormat("dd/MM/yyyy").format(java.util.Date(message.dateSent)))
                                    }
                                }
                            }
                            else {
                                Row(
                                    modifier = Modifier
                                        .width(100.dp)
                                        .padding(16.dp),
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    Card(colors = CardColors(
                                        contentColor = Color.White,
                                        containerColor = Color.Gray,
                                        disabledContainerColor = Color.Black,
                                        disabledContentColor = Color.Black
                                    )) {
                                        Text(message.content)
                                        Text(java.text.SimpleDateFormat("dd/MM/yyyy").format(java.util.Date(message.dateSent)))
                                    }
                                }
                            }

                        }
                    }
                }

                Row(modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .testTag("sendMessageButton")){

                    OutlinedTextField(
                        value = messageContent,
                        onValueChange = { messageContent = it },
                        label = { Text("Your Message:") },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("messageContent"),
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Button(onClick = {
                        onAddMessage(senderID, receiverID, messageContent)
                        messages = onGetMessages(senderID, receiverID)
                    }){
                        Text("Send")
                    }
                }

            }
        }
    }

}