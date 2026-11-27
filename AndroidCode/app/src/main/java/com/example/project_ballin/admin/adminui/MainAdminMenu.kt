package com.example.project_ballin

import android.provider.Settings
import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.project_ballin.entrant.Entrant
import com.example.project_ballin.reusableui.TopAppBar
import com.example.project_ballin.reusableui.UserDetailModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 *The main menu seen by admins logging into the app
 * */
class MainAdminMenu(){
    /**
     *  The main home screen of admin, it displays different
     *  buttons and those buttons brings them to different screens
     *
     *  @param navController the navController of the app
     */

    @Composable
    fun AdminMenuScreen(navController: NavController, adminID: String) {

        val adminList = remember { AdminList() }
        val admin = adminList.getAdmin(adminID)
        val firstName = admin?.firstName ?: ""

        Log.d("AdminMenuScreen", "AdminID: $adminID" + "FirstName: $firstName" + "LastName: {$admin.lastName}")
        TopAppBar(
            navController = navController,
            screenTitle = "Admin Home",
            backButton = false,
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(128.dp))
                Text(
                    text = "Welcome $firstName",
                    fontSize = 34.sp
                )
                Spacer(modifier = Modifier.height(128.dp))

                AdminOptionButton(
                    "All Campsites",
                    "AdminCampsites",
                    "adminCampsitesButton",
                    navController
                )
                Spacer(modifier = Modifier.height(16.dp))
                AdminOptionButton(
                    "All Profiles",
                    "AdminProfiles",
                    "adminProfilesButton",
                    navController
                )
                Spacer(modifier = Modifier.height(16.dp))
                AdminOptionButton(
                    "All Notifications",
                    "AdminNotifications",
                    "adminNotificationsButton",
                    navController
                )
                Spacer(modifier = Modifier.height(16.dp))
                AdminOptionButton(
                    "All Messages",
                    "AdminMessages",
                    "adminMessagesButton",
                    navController
                )
                Spacer(modifier = Modifier.height(16.dp))
                AdminOptionButton(
                    "All Images",
                    "AdminImages",
                    "adminImagesButton",
                    navController
                )
                Spacer(modifier = Modifier.height(16.dp))
                AdminOptionButton(
                    "QR Codes",
                    "AdminQRCodes",
                    "adminQRCodesButton",
                    navController
                )
                Spacer(modifier = Modifier.height(16.dp))
                AdminOptionButton(
                    "Reports and Violations",
                    "AdminViolationChoice",
                    "violationsAndReportsButton",
                    navController
                )
                Spacer(modifier = Modifier.height(16.dp))
                AdminOptionButton(
                    "Switch Modes",
                    "AdminSwitchModes",
                    "switchModesButton",
                    navController
                )
                Spacer(modifier = Modifier.height(16.dp))
                AdminOptionButton(
                    "Policies and Guidelines",
                    "PoliciesAndGuidelines",
                    "policyGuidelinesButton",
                    navController
                )

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
    fun AdminOptionButton(
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

    @Preview(showBackground = true)
    @Composable
    fun AdminMenuScreenPreview() {
        val navController = rememberNavController()
        val adminID = "456"
        AdminMenuScreen(navController, adminID)
        AdminMenuScreen(navController, "")
    }
}
