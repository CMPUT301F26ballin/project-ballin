package com.example.project_ballin

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.compose.runtime.*
import androidx.navigation.*
import androidx.navigation.compose.*
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import kotlin.uuid.Uuid

/**
 *
 * the first screen a user will see, need to implement something that will
 * recognize device ID
 *
 * @param navController a [NavController] to continue to enter details screen
 *
 */

@Composable
fun Signup(navController: NavController){
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome To Fire-ballin",
            fontSize = 34.sp
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "The Lottery-based Campsite Reservation App",
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Please sign up to continue:",
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                /*TODO*/
            },
            modifier = Modifier.testTag("entrantSignin") // for testing
        ) {
            Text("Sign-up as Entrant")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                navController.navigate("OwnerMenu/${Uuid.random()}")
            },
            modifier = Modifier.testTag("ownerSignin") // for testing
        ) {
            Text("Sign-up as Campsite Owner")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                /*TODO*/
            },
            modifier = Modifier.testTag("adminSignin") // for testing
        ) {
            Text("Sign-up as Admin")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignupPreview() {
    val navController = rememberNavController()
    Signup(navController)
}