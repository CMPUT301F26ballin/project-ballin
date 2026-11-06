package com.example.project_ballin.entrant.entantui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.*
import androidx.navigation.compose.*
import com.example.project_ballin.reusableui.TopAppBar

/**
 * The entrant menu screen
 * Has the main four items for entrant:
 * - Browse campsites
 * - my reservations
 * - scan QR code
 * - policies and guidelines
 *
 * @param navController the navigation controller for the app
 */
@Composable
fun EntrantMenuScreen(navController: NavController){
    TopAppBar(
        navController,
        screenTitle = "Home",
        backButton = false,
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(128.dp))
            Text(
                text = "Welcome Entrant",
                fontSize = 34.sp
            )
            Spacer(modifier = Modifier.height(128.dp))
            EntrantHomeButton("BROWSE CAMPSITES", "Browse Campsites", "BROWSE CAMPSITES", navController)
            Spacer(modifier = Modifier.height(16.dp))
            EntrantHomeButton("MY RESERVATIONS", "EntrantBrowseReservations", "MY RESERVATIONS", navController)
            Spacer(modifier = Modifier.height(16.dp))
            EntrantHomeButton("SCAN QR CODE", "Scan QR Code", "SCAN QR CODE", navController)
            Spacer(modifier = Modifier.height(16.dp))
            EntrantHomeButton("POLICY AND GUIDELINES", "PoliciesAndGuidelines", "POLICY AND GUIDELINES", navController)
        }


    }
}

/**
 * A button template for the entrant menu screen
 *
 * @param name the name of the button
 * @param destination the destination of the button
 * @param testTag the test tag of the button
 */
@Composable
fun EntrantHomeButton(name: String, destination: String, testTag: String, navController: NavController) {

    Button(
        onClick = {
            navController.navigate(destination)
        },
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .testTag(testTag) // for UI testing
            .width(250.dp)
            .height(85.dp)
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(name)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EntrantMenuScreenPreview(){
    val navController = rememberNavController()
    EntrantMenuScreen(navController)
}