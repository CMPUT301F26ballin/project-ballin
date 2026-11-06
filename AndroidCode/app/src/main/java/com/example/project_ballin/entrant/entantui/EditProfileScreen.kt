package com.example.project_ballin.entrant.entantui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.*
import androidx.navigation.compose.*
import com.example.project_ballin.reusableui.TopAppBar

/**
 * Just there for testing
 */
@Composable
fun TestColumn(){
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Text("test")
    }
}

/**
 * Edit profile screen
 * Will be where users can edit their profile
 * So fat does not do anything though
 *
 * @param navController the navigation controller for the app
 */
@Composable
fun EditProfileScreen(navController: NavController){
    TopAppBar(
        navController,
        screenTitle = "Edit Profile",
        profileButton = false
    ) {
        TestColumn()
    }



}


@Preview(showBackground = true)
@Composable
fun EditProfileScreenPreview() {
    val navController = rememberNavController()
    EditProfileScreen(navController)

}