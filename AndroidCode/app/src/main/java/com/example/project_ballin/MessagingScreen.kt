package com.example.project_ballin

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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
    fun MessagingScreen(navController: NavController, ownerID: String, userID: String,userName: String,
                        onAddMessage:(userID: String, ownerID: String, messageContent: String) -> Unit){
        var messageContent by remember { mutableStateOf("") }

        TopAppBar(navController, "Messaging ${userName}"){
            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                // Lets the owner report the entrant they are messaging
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
                        onAddMessage(userID, ownerID, messageContent)
                        navController.popBackStack()

                    }){
                        Text("Send Message")
                    }
                }

            }
        }
    }

}