package com.example.project_ballin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun BallinApp() {


    val navController = rememberNavController()

    val campsiteList = CampsiteList()

    // remember = only create one ProfileList
    val profileList = remember { ProfileList() }
    val loggedInUser: User? = null

    NavHost(navController = navController, startDestination = "signup") {
        composable("signup") { Signup(navController) }
        composable("profile"){ EditProfileScreen(navController) }

        composable("OwnerMenu/{OwnerID}") {
            val ownerID = it.arguments!!.getInt("OwnerID")
            OwnerMenuScreen(navController, ownerID)
        }

        composable("CampsiteListScreen/{OwnerID}") {
            val ownerID = it.arguments!!.getInt("OwnerID")
            CampsiteListScreen(navController, ownerID, getOwnerSites = {campsiteList.getOwnerSites(it)})
        }

        composable("CampsiteDetailScreen/{CampID}") {
            val siteID = it.arguments!!.getInt("CampID")
            CampsiteDetailScreen(navController, siteID, getSite = {campsiteList.getCampsite(it)},
                )
        }

        composable("CampsiteInputScreen/{CampID}") {
            val siteID = it.arguments?.getInt("CampID")
            val ownerID = loggedInUser!!.deviceId
            CampsiteInputScreen(navController, siteID, ownerID, getSite = {campsiteList.getCampsite(it)},
                addSite = {campsiteList.addCampsite(it)},
                updateSite = {oldSite, newSite -> campsiteList.updateCampsite(oldSite, newSite)})
        }

        composable("AdminMenu") { AdminMenuScreen(navController) }

        composable("AdminProfiles") {
            AdminProfileListScreen(
                navController,
                profiles = profileList.profiles,
                onDelete = { profileList.deleteProfile(it) }
            )
        }

        composable("AdminProfileDetail/{DeviceID}") {
            val deviceID = it.arguments?.getString("DeviceID") ?: ""
            AdminProfileDetailScreen(
                navController,
                profile = profileList.getProfile(deviceID),
                onDelete = { profileList.deleteProfile(it) }
            )
        }
    }

}

