package com.example.project_ballin.owner.ownerUI

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.project_ballin.reusableui.TopAppBar
import kotlin.uuid.Uuid
/**
 * The Main menu for when an Owner account logs in. It features buttons to go to campsites,
 * lotteries, viewing past notifications, and viewing your active reservations.
 *
 * */
class MainOwnerMenu(){

/**
 * The main composable menu that owners see when they first log in
 *
 * @param navController the navController Object.
 * @param ownerID the String representation of the logged-in owner's Uuid. This value gets passed along to new screens as needed
 *
 *
 *
 * */
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun OwnerMenuScreen(navController: NavController, ownerID: String) {
        TopAppBar(
            navController = navController,
            screenTitle = "Owner Homepage",
            backButton = false,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(128.dp))
                Text(
                    text = "Welcome Owner",
                    fontSize = 34.sp
                )
                Spacer(modifier = Modifier.height(128.dp))

                OwnerOptionsButton(
                    "My Campsites",
                    "CampsiteListScreen/$ownerID",
                    "toCampsiteList",
                    navController
                )
                Spacer(modifier = Modifier.height(16.dp))
                OwnerOptionsButton(
                    "My Lotteries",
                    "LotteryListScreen/$ownerID",
                    "toOwnerLotteries",
                    navController
                )
                Spacer(modifier = Modifier.height(16.dp))
                OwnerOptionsButton("Past Notifications", "", "", navController) // TODO
                Spacer(modifier = Modifier.height(16.dp))
                OwnerOptionsButton("Active Reservations", "", "", navController) // TODO
                Spacer(modifier = Modifier.height(16.dp))
                OwnerOptionsButton(
                    "POLICY AND GUIDELINES",
                    "PoliciesAndGuidelines",
                    "",
                    navController
                )
            }
        }
    }

    /**
     * REUSED FROM ADMIN (standard buttons could be moved to a separate file?)
     *
     * A button that relates to an admin's features
     * Summarized in a topic. Example: Admin can browse
     * and delete profiles, so the button's topic is about Profiles
     *
     * @param name is the topic name related to admin features
     * @param destination is the destination screen of that button
     * @param testTag is the testTag name for UI testing
     * @param navController is the navController of the App
     */
    @Composable
    fun OwnerOptionsButton(
        name: String,
        destination: String,
        testTag: String,
        navController: NavController
    ) {

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
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(name)
            }
        }
    }
}