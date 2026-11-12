package com.example.project_ballin.admin.adminui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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

// STILL DOING IT/ CHANGES TO DO
// CHANGE THE TEXT A BIT LATER ON TO MAKE IT MORE UNDERSTANDABLE

class AdminViolationChoiceScreen {

    /**
     * Shows three buttons to further navigate to different screens
     * that will end up showing a list of violations of certain things.
     *
     * @param navController the navController of the app
     */
    @Composable
    fun AdminViolationChoice (
        navController: NavController
    ) {
        TopAppBar(
            navController = navController,
            screenTitle = "Violation Topics"
        ){
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Choose Violation Topic",
                        fontSize = 24.sp
                    )
                }

                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "to",
                        fontSize = 24.sp
                    )
                }

                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "View List of Related Violations",
                        fontSize = 24.sp
                    )
                }
                Spacer(modifier = Modifier.height(34.dp))

                ViolationTopicButton(
                    navController,
                    "Reviews",
                    "TODO",
                    "reviewViolationsButton"
                )
                Spacer(modifier = Modifier.height(16.dp))

                ViolationTopicButton(
                    navController,
                    "Ratings",
                    "TODO",
                    "ratingViolationsButton"
                )
                Spacer(modifier = Modifier.height(16.dp))

                ViolationTopicButton(
                    navController,
                    "Campsite Owners",
                    "TODO",
                    "campsiteOwnerViolationsButton"
                )
            }
        }
    }

    /**
     * A button that relates to different topics of violations
     *
     * @param navController the navController of the app
     * @param topic the topic of the violation
     * @param destination the destination screen of that button
     * @param testTag the testTag name for UI testing
     */
    @Composable
    fun ViolationTopicButton (
        navController: NavController,
        topic: String,
        destination: String,
        testTag: String,
    ) {
        Button (
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
                Text(
                    text = topic,
                    fontSize = 16.sp)
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun AdminViolationChoiceScreenPreview() {
        val navController = rememberNavController()
        AdminViolationChoice(navController)
    }
}