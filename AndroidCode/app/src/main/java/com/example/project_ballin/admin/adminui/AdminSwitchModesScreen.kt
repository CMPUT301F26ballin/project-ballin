package com.example.project_ballin.admin.adminui

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.project_ballin.UserCurrentSession
import com.example.project_ballin.entrant.entantui.ConfirmActionDialog
import com.example.project_ballin.reusableui.TopAppBar

// Still basic right now, because if admin user wants to switch
// they have to make a new campsite owner/entrant account first
// then they can freely switch based on those accounts.
class AdminSwitchModesScreen {

    /**
     * This allows the admin user to switch between modes/role.
     *
     * The admin user can also act as a campsite owner or an entrant if they want.
     * This is made possible through screen navigations, and permissions.
     *
     * @param navController the navController of the app
     * @param userCurrentSession the userCurrentSession object stores relevant information of current user
     */
    @Composable
    fun AdminSwitchModes(
        navController: NavController,
        userCurrentSession: UserCurrentSession
    ) {
        TopAppBar(
            navController = navController,
            screenTitle = "Switch Modes"
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Choose Mode",
                    fontSize = 34.sp
                )
                Spacer(modifier = Modifier.height(34.dp))

                SwitchModeButton(
                    "Admin Menu Screen",
                    "adminModeButton",
                    "Admin Mode",
                    navController
                )
                Spacer(modifier = Modifier.height(16.dp))
                SwitchModeButton(
                    "MainEntrantMenu",
                    "entrantModeButton",
                    "Entrant Mode",
                    navController
                )

                // I did a userCurrentSession object to access the userID
                Spacer(modifier = Modifier.height(16.dp))
                SwitchModeButton(
                    "OwnerMenu/${userCurrentSession.currentProfile.userID}",
                    "ownerModeButton",
                    "Owner Mode",
                    navController
                )
            }
        }
    }

    /**
     * A button that relates to different roles an admin user can switch to
     * it also adds in a confirmation dialog to make sure the user wants to switch
     *
     * @param destination is the destination screen of that button
     * @param testTag is the testTag name for UI testing
     * @param name is the name of the button or the role they want to switch to
     * @param navController is the navController of the App
     */
    @Composable
    fun SwitchModeButton(
        destination: String,
        testTag: String,
        name: String,
        navController: NavController
    ) {

        var confirmActionPopUp by remember { mutableStateOf(false) }

        if (confirmActionPopUp) {
            ConfirmActionDialog().ConfirmAction(
                "Are you sure you want to switch to $name?",
                confirm = { confirmed: Boolean ->
                    confirmActionPopUp = false
                    if (confirmed) {
                        navController.navigate(destination)
                    }
                }
            )
        }

        Button(
            onClick = { confirmActionPopUp = true },
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

    @Preview(showBackground = true)
    @Composable
    fun AdminSwitchModesPreview() {
        val navController = rememberNavController()
        AdminSwitchModes(navController, userCurrentSession = UserCurrentSession())
    }
}