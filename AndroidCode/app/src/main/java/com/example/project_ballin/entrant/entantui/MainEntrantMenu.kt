package com.example.project_ballin.entrant.entantui

import androidx.compose.foundation.layout.*
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

@Composable
fun EntrantMenuScreen(navController: NavController){
    TopAppBar(
        navController = navController,
        screenTitle = "Home",
        backButton = false,
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(128.dp))
            Text(
                text = "Welcome",
                fontSize = 34.sp
            )
            Spacer(modifier = Modifier.height(128.dp))
            EntrantHomeButton("BROWSE CAMPSITES", "", "", navController)
            Spacer(modifier = Modifier.height(16.dp))
            EntrantHomeButton("MY RESERVATIONS", "", "", navController)
            Spacer(modifier = Modifier.height(16.dp))
            EntrantHomeButton("SCAN QR CODE", "", "", navController)
            Spacer(modifier = Modifier.height(16.dp))
            EntrantHomeButton("POLICY AND GUIDELINES", "", "", navController)
        }


    }
}

@Composable
fun EntrantHomeButton(name: String, destination: String, testTag: String, navController: NavController) {

    Button(
        onClick = {
            navController.navigate(destination)
        },
        modifier = Modifier
            .testTag(testTag) // for UI testing
            .fillMaxWidth()
            .height(64.dp)
            .padding(horizontal = 16.dp)
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