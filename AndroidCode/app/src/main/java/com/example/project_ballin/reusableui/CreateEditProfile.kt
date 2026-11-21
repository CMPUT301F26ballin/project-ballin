package com.example.project_ballin.reusableui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.*
import androidx.navigation.compose.*

/**
 *
 * screen for the user (anyone) to enter their details in order to sign in
 *
 */

class UserProfile(){

    @Composable
    fun CreateProfile(navController: NavController) {
        TopAppBar(
            navController,
            "Sign-In Details",
            profileButton = false
        ) {
            val mode = "create"
            InputDetails(navController, mode = mode)
        }

    }

    @Composable
    fun EditProfile(navController: NavController) {
        TopAppBar(
            navController,
            "Edit Profile",
            profileButton = false
        ) {
            val mode = "edit"
            InputDetails(navController, mode = mode)
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun CreateProfilePreview() {
        val navController = rememberNavController()
        CreateProfile(navController)
    }
    @Preview(showBackground = true)
    @Composable
    fun EditProfilePreview() {
        val navController = rememberNavController()
        EditProfile(navController)
    }
}