package com.example.project_ballin.admin.adminui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.example.project_ballin.AdminList
import com.example.project_ballin.EntrantList
import com.example.project_ballin.OwnerList
import com.example.project_ballin.entrant.entantui.ConfirmActionDialog
import com.example.project_ballin.reusableui.TopAppBar
import com.example.project_ballin.reusableui.UserDetailModel
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.project_ballin.Admin


class AdminSwitchModesScreen {

    /**
     * This allows the admin user to switch between modes/role.
     * through their admin profile.
     *
     * The admin user can also act as a campsite owner or an entrant if they want.
     * This is made possible through screen navigations, and permissions.
     *
     * @param navController the navController of the app
     * @param entrantList the list of entrants, to access permission to switch roles
     * @param ownerList the list of owners, to access permission to switch roles
     * @param adminList the list of admins, to access permission to switch roles
     */
    @Composable
    fun AdminSwitchModes(
        adminID: String,
        navController: NavController,
        entrantList: EntrantList,
        ownerList: OwnerList,
        adminList: AdminList,
        userDetailModel: UserDetailModel = viewModel()
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
                    "AdminMenu/${adminID}",
                    "adminModeButton",
                    "Admin Mode",
                    navController,
                    onRoleChange = {
                        userDetailModel.onRoleChangeToAdmin(adminList)
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                SwitchModeButton(
                    "EntrantMenu/${adminID}",
                    "entrantModeButton",
                    "Entrant Mode",
                    navController,
                    onRoleChange = {
                        userDetailModel.onRoleChangeToEntrant(entrantList)
                    }
                )


                Spacer(modifier = Modifier.height(16.dp))
                SwitchModeButton(
                    "OwnerMenu/${adminID}",
                    "ownerModeButton",
                    "Owner Mode",
                    navController,
                    onRoleChange = {
                        userDetailModel.onRoleChangeToOwner(ownerList)
                    }
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
        navController: NavController,
        onRoleChange: () -> Unit
    ) {

        var confirmActionPopUp by remember { mutableStateOf(false) }

        if (confirmActionPopUp) {
            ConfirmActionDialog().ConfirmAction(
                "Are you sure you want to switch to $name?",
                confirm = { confirmed: Boolean ->
                    confirmActionPopUp = false
                    if (confirmed) {
                        onRoleChange()
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
                .fillMaxWidth()
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
        AdminSwitchModes(
          "",navController, entrantList = EntrantList(),
            ownerList = OwnerList(), adminList = AdminList())
    }
}