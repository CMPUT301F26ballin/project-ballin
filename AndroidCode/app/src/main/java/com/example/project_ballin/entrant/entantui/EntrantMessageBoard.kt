package com.example.project_ballin.entrant.entantui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.project_ballin.Message
import com.example.project_ballin.MessageList
import com.example.project_ballin.OwnerList
import com.example.project_ballin.entrant.Entrant
import com.example.project_ballin.reusableui.TopAppBar

class EntrantMessageBoard {

    @Composable
    fun MessageBoard(navController: NavController, messageList: MessageList, entrant: Entrant) {

        val messages = messageList.getMessagesForEntrant(entrant.userID)

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            TopAppBar(
                navController,
                "Confirm Booking"
            ) {
                Column{
                    LazyColumn{
                        items(messages){ message ->
                            OwnerMessageCard(message)
                        }

                    }
                }

            }

        }
    }

    @Composable
    fun OwnerMessageCard(message: Message){
        Card(
            modifier = Modifier.fillMaxWidth()

        ){
            Text(
                text = message.content,
                style = MaterialTheme.typography.bodyLarge
            )

        }
    }
}