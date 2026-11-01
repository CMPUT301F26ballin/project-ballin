package com.example.project_ballin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.project_ballin.entrant.entantui.EditProfileScreen
//import com.example.project_ballin.owner.ownerUI.CampsiteDetailScreen commented out cuz its giving me an error -Nick
import com.example.project_ballin.owner.ownerUI.CampsiteInputScreen
import com.example.project_ballin.owner.ownerUI.CampsiteListScreen
import com.example.project_ballin.owner.ownerUI.OwnerMenuScreen
import kotlin.uuid.Uuid

@Composable
fun BallinApp() {


    val navController = rememberNavController()

    val campsiteList = CampsiteList()

    // remember = only create one ProfileList
    val profileList = remember { ProfileList() }

    NavHost(navController = navController, startDestination = "signup") {
        composable("signup") { Signup(navController) }
        composable("profile"){ EditProfileScreen(navController) }

        composable("OwnerMenu/{OwnerID}") {
            val ownerIDString = it.arguments!!.getString("OwnerID")
            val ownerID = Uuid.parse(ownerIDString!!)
            OwnerMenuScreen(navController, ownerID)
        }

        composable("CampsiteListScreen/{OwnerID}") {
            val ownerIDString = it.arguments!!.getString("OwnerID")
            val ownerID = Uuid.parse(ownerIDString!!)
            CampsiteListScreen(navController, ownerID, getOwnerSites = {campsiteList.getOwnerSites(it)})
        }

        composable("CampsiteDetailScreen/{CampID}") {
            val siteIDString = it.arguments!!.getString("CampID")
            val siteID = Uuid.parse(siteIDString!!)
            val campsite = campsiteList.getCampsite(siteID)
            CampsiteDetailScreen(navController, campsite)
        }

        composable("CampsiteInputScreen/{CampID}") {
            val siteIDString = it.arguments?.getString("CampID")
            var campsite: Campsite?
            if(siteIDString == null){
                campsite = null
            }
            else{
                val siteID = Uuid.parse(siteIDString!!)
                campsite = campsiteList.getCampsite(siteID)
            }
            val ownerID = Uuid.random()

            println("To input!")

            CampsiteInputScreen(
                navController, campsite, ownerID,
                addSite = { campsiteList.addCampsite(it) },
                updateSite = { oldSite, newSite -> campsiteList.updateCampsite(oldSite = oldSite, newSite) })
        }

        composable("AdminMenu") { AdminMenuScreen(navController) }

        composable("AdminCampsites") {
            AdminCampsiteListScreen(
                navController,
                campsites = campsiteList.campsites,
                onDelete = { campsiteList.deleteCampsite(it) }
            )
        }

        composable("AdminCampsiteDetail/{CampID}") {
            val siteID = it.arguments?.getString("CampID") ?: ""
            AdminCampsiteDetailScreen(
                navController,
                site = campsiteList.campsites.find { site -> site.ID.toString() == siteID },
                onDelete = { campsiteList.deleteCampsite(it) }
            )
        }

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

        // composable("AdminMessageList") { MessageListScreen(navController, messageList)  }
    }

}

