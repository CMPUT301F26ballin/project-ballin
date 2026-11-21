package com.example.project_ballin

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.project_ballin.owner.ownerUI.OwnerReservationScreen
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
    fun MessagingScreen(navController: NavController, ownerID: String, userID: String){
        val entrantList = EntrantList()
        val entrant = entrantList.getEntrant(userID)

        TopAppBar(navController, "Messaging ${entrant!!.firstName}"){
            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                
            }
        }
    }

}