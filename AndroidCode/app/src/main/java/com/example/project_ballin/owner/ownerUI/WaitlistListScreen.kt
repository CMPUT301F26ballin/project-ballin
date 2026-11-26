package com.example.project_ballin.owner.ownerUI

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import com.example.project_ballin.entrant.Entrant

class WaitlistListScreen() {

    /**
     * Screen to send notifications to entrants in a lottery.
     * Notifications are sent to one lottery for all entrants to read.
     *
     * @param navController the navcontroller of the app
     * @param entrants the list of entrants enrolled in this lottery
     */

    @SuppressLint("NotConstructor")
    @Composable
    fun WaitlistListScreen(
        navController: NavController,
        entrants: List<Entrant>
    ) {
        TopAppBar(navController, "Sent Notifications") {
            if (entrants.isEmpty()){
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No one has signed up for the lottery yet")
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ){
                    items(entrants, key = {it.userID}) { entrant ->
                        EntrantDetailBox(entrant)
                    }
                }
            }
        }
    }

    @SuppressLint("NotConstructor")
    @Composable
    fun EntrantDetailBox(
        entrant: Entrant
    ){
        Card(
            modifier = Modifier
                .fillMaxWidth()
        ){
            Column(modifier = Modifier.padding(16.dp)) {
                Text("${entrant.firstName} ${entrant.lastName}")
                Text("${entrant.email}")
                Text("Past reservations: ${entrant.pastReservations.count()}")
            }
        }
    }
}