package com.example.project_ballin.owner.ownerUI

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlin.uuid.Uuid

@Composable
fun OwnerMenuScreen(navController: NavController, ownerID: Uuid){

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)){
        Button(
            onClick = { navController.navigate("CampsiteListScreen/$ownerID") },
            modifier = Modifier
                .padding(16.dp)
                .testTag("toCampsiteList")
        )
        {
            Text("My Campsites")
        }

        Spacer(modifier = Modifier.height(16.dp))
    }



}