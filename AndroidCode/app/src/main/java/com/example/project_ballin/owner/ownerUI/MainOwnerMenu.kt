package com.example.project_ballin.owner.ownerUI

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlin.uuid.Uuid

@Composable
fun OwnerMenuScreen(navController: NavController, ownerID: Uuid){

    Column(modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center){

        Text("Welcome: ${ownerID.toString()}")

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { Log.d("Navigation", "I should go to the campsite List")
                navController.navigate("CampsiteListScreen/$ownerID")
                },
            modifier = Modifier
                .padding(16.dp)
                .testTag("toCampsiteList")
        )
        {
            Text("My Campsites")
        }


    }



}