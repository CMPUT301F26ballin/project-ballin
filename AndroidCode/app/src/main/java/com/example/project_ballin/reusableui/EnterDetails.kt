package com.example.project_ballin.reusableui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.*
import androidx.navigation.compose.*

/**
 *
 * screen for the user (anyone) to enter their details in order to sign in and
 *
 */

@Composable
fun EnterDetails(){

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
    ){
        Text(
            text = "Before We Begin Please Enter Your Details:",
            style = MaterialTheme.typography.titleLarge
        )

    }

    //user enters first name and last name
    //user enters email, phone number
        // android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        //Patterns.PHONE.matcher(phoneNumber).matches()
    //create function to get the user's device ID
    //create function to generate a UUID for the user
    //store everything in firebase
    //continue button is only valid after user enters all info
    //continue takes user to homescreen for owner/entrant/admin

}

@Preview(showBackground = true)
@Composable
fun EnterDetailsPreview() {
    EnterDetails()
}