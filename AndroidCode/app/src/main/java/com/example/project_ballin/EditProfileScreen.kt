package com.example.project_ballin

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.*
import androidx.navigation.compose.*

@Composable
fun TestColumn(){
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Text("test")
    }
}

@Composable
fun EditProfileScreen(navController: NavController){
    TopAppBar(navController,
        screenTitle = "Edit Profile",
        profileButton = false){
        TestColumn()
    }



}


@Preview(showBackground = true)
@Composable
fun EditProfileScreenPreview() {
    val navController = rememberNavController()
    EditProfileScreen(navController)

}