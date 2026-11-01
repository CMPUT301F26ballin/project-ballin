package com.example.project_ballin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.project_ballin.reusableui.TopAppBar

// Note: Might have to add a switch mode button at bottom to
// switch modes (owner, entrant, admin), bc the top bar shows
// the title of the screen, so find diff location

/**
 *  The main home screen of admin, it displays different
 *  buttons and those buttons brings them to different screens
 *
 *  @param navController the navController of the app
 */

@Composable
fun AdminMenuScreen(navController: NavController) {
    TopAppBar(
        navController = navController,
        screenTitle = "Admin Home",
        backButton = false,
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(
                text = "Welcome, Admin !",
                fontSize = 34.sp
            )
            Spacer(modifier = Modifier.height(34.dp))

            // Still have to specify the destinations and names of test tags below

            FeatureTopicButton("CAMPSITES", "AdminCampsites", "adminCampsitesButton", navController)
            Spacer(modifier = Modifier.height(16.dp))
            FeatureTopicButton("PROFILES", "AdminProfiles", "adminProfilesButton", navController)
            Spacer(modifier = Modifier.height(16.dp))
            FeatureTopicButton("NOTIFICATIONS", "AdminNotifications", "adminNotificationsButton", navController)
            Spacer(modifier = Modifier.height(16.dp))
            FeatureTopicButton("MESSAGES", "AdminMessages", "adminMessagesButton", navController)
            Spacer(modifier = Modifier.height(16.dp))
            FeatureTopicButton("OWNERS VIOLATING POLICY", "", "", navController)
        }
    }
}

/**
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
fun FeatureTopicButton(name: String, destination: String, testTag: String, navController: NavController) {

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
            Text(
                text = "(Browse and Delete)",
                fontSize = 10.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AdminMenuScreenPreview() {
    val navController = rememberNavController()
    AdminMenuScreen(navController)
}
