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
 * screen for the user (anyone) to enter their details in order to sign in
 *
 */

@Composable
fun IntroText(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ){
        Text(
            text = "Before We Begin, Please Enter Your Details:",
            fontSize = 24.sp,
        )
        Spacer(modifier = Modifier.height(16.dp))

    }
}

@Composable
fun CreateProfile(navController: NavController){
    TopAppBar(
        navController,
        "Sign-In Details",
        profileButton = false) {
        IntroText()
        val mode = "create"
        InputDetails(navController, mode = mode)
    }

    //create function to get the user's device ID
    //create function to generate a UUID for the user
    //store everything in firebase
    //continue button is only valid after user enters all info
    //continue takes user to homescreen for owner/entrant/admin

}

@Preview(showBackground = true)
@Composable
fun CreateProfilePreview() {
    val navController = rememberNavController()
    CreateProfile(navController)
}