package com.example.project_ballin

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun BallinApp() {


    val navController = rememberNavController()

    val campsiteList = CampsiteList()

    NavHost(navController = navController, startDestination = "signup") {
        composable("signup") { Signup(navController) }
        composable("profile"){ EditProfileScreen(navController) }

        composable("OwnerMenu/{OwnerID}") {
            val ownerID = it.arguments!!.getInt("OwnerID")
            OwnerMenuScreen(navController, ownerID)
        }

        composable("CampsiteListScreen/{OwnerID}") {
            val ownerID = it.arguments!!.getInt("OwnerID")
            //CampsiteListScreen(navController, ownerID, getOwnerSites = {campsiteList.getOwnerSites(it)})
        }

        composable("CampsiteDetailScreen/{CampID}") {
            val siteID = it.arguments!!.getInt("CampID")
            CampsiteDetailScreen(navController, siteID, getSite = {campsiteList.getCampsite(it)})
        }

        composable("CampsiteInputScreen/{CampID}") {
            val siteID = it.arguments?.getInt("CampID")
            CampsiteInputScreen(navController, siteID, getSite = {campsiteList.getCampsite(it)})
        }

        composable("AdminMenu") { AdminMenuScreen(navController) }
    }

}

