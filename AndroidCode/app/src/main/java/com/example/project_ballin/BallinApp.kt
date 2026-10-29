package com.example.project_ballin

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.project_ballin.entrant.entantui.EditProfileScreen
import kotlin.uuid.Uuid

@Composable
fun BallinApp() {


    val navController = rememberNavController()

    val campsiteList = CampsiteList()

    val lotteryList = LotteryList()

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
            CampsiteListScreen(navController, ownerIDString!!, getOwnerSites = {campsiteList.getOwnerSites(it)})
        }

        composable("CampsiteDetailScreen/{CampID}") {
            val siteIDString = it.arguments!!.getString("CampID")
            val campsite = campsiteList.getCampsite(siteIDString!!)
            CampsiteDetailScreen(navController, campsite)
        }

        composable("CampsiteInputScreen/{CampID}") {
            val siteIDString = it.arguments?.getString("CampID")
            var campsite: Campsite?
            if(siteIDString == null){
                campsite = null
            }
            else{
                campsite = campsiteList.getCampsite(siteIDString)
            }
            val ownerID = Uuid.random() //CHANGE THIS ONCE WE FIGURE OUT USER IDS!
            CampsiteInputScreen(navController, campsite, ownerID,
                addSite = {campsiteList.addCampsite(it)},
                updateSite = {oldSite, newSite -> campsiteList.updateCampsite(oldSite, newSite)})
        }

        composable("LotteryListScreen/{OwnerID}") {
            val ownerID = it.arguments!!.getString("OwnerID")
            LotteryListScreen(navController, ownerID!!, getOwnerLotteries = { lotteryList.getOwnerLotteries(it) })
        }

        composable("LotterySelectSitesScreen/{OwnerID}") {
            //
        }
        composable ("LotteryDetailScreen/{LotteryID}") {
            //
        }

        composable("AdminMenu") { AdminMenuScreen(navController) }

        // composable("AdminMessageList") { MessageListScreen(navController, messageList)  }
    }

}

