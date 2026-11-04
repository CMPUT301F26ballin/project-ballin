package com.example.project_ballin

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.project_ballin.admin.adminui.NotificationDetailScreen
import com.example.project_ballin.admin.adminui.NotificationListScreen
import com.example.project_ballin.entrant.Entrant
import com.example.project_ballin.entrant.entantui.ActiveReservationScreen
import com.example.project_ballin.entrant.entantui.EditProfileScreen
import com.example.project_ballin.entrant.entantui.EntrantBrowseReservations
import com.example.project_ballin.entrant.entantui.EntrantMenuScreen
import com.example.project_ballin.entrant.entantui.EntrantSiteBooker
import com.example.project_ballin.entrant.entantui.PastReservationScreen
import com.example.project_ballin.lottery.Lottery
import com.example.project_ballin.lottery.LotteryList
//import com.example.project_ballin.owner.ownerUI.CampsiteDetailScreen commented out cuz its giving me an error -Nick
import com.example.project_ballin.owner.ownerUI.CampsiteInputScreen
import com.example.project_ballin.owner.ownerUI.CampsiteListScreen
import com.example.project_ballin.owner.ownerUI.LotteryDetailScreen
import com.example.project_ballin.owner.ownerUI.LotteryListScreen
import com.example.project_ballin.owner.ownerUI.OwnerMenuScreen
import kotlin.uuid.Uuid

@Composable
fun BallinApp() {


    val navController = rememberNavController()

    // remember = only create one ProfileList
    val campsiteList = remember { CampsiteList() }
    val messageList = remember { MessageList() }
    val notificationList = remember { NotificationList() }
    val profileList = remember { ProfileList() }
    val entrantList = remember { EntrantList() }
    val ownerList = remember { OwnerList() }
    val adminList = remember { AdminList() }

    //temporary for testing
    var currentEntrant by remember {
        mutableStateOf<Entrant?>(
            Entrant(
                name = "Nick", phoneNum = "123", email = "nick@ualberta.ca",
                deviceId = "123", profilePic = "a"
            )
        )
    }

    val lotteryList = LotteryList()

    NavHost(navController = navController, startDestination = "signup") {
        composable("signup") { Signup(navController) }
        composable("profile") { EditProfileScreen(navController) }

        composable("OwnerMenu/{OwnerID}") {
            val ownerIDString = it.arguments!!.getString("OwnerID")
            val ownerID = Uuid.parse(ownerIDString!!)
            OwnerMenuScreen(navController, ownerID)
        }

        composable("CampsiteListScreen/{OwnerID}") {
            val ownerIDString = it.arguments!!.getString("OwnerID")
            val ownerID = Uuid.parse(ownerIDString!!)
            CampsiteListScreen(
                navController,
                ownerID,
                getOwnerSites = { campsiteList.getOwnerSites(it) })
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
            if (siteIDString == null || siteIDString == "none") {
                Log.d("navigation", "no campsite")
                campsite = null
            } else {
                val siteID = Uuid.parse(siteIDString)
                campsite = campsiteList.getCampsite(siteID)
            }
            val ownerID = Uuid.random()

            Log.d("navigation","To input!")

            CampsiteInputScreen(
                navController, campsite, ownerID,
                addSite = { Log.d("Site Addition", "Adding Site")
                    campsiteList.addCampsite(it) },
                updateSite = { oldSite, newSite -> campsiteList.updateCampsite(oldSite = oldSite, newSite) })
        }

        composable("LotteryListScreen/{OwnerID}") {
            val ownerID = it.arguments!!.getString("OwnerID")
            LotteryListScreen(
                navController,
                ownerID!!,
                getOwnerLotteries = { lotteryList.getOwnerLotteries(it) })
        }

        composable(
            "LotteryDetailScreen/{OwnerID}?LotteryID={LotteryID}",
            arguments = listOf(navArgument("LotteryID") {
                nullable = true
                defaultValue = null
            })
        ) {
            val ownerID = it.arguments!!.getString("OwnerID")
            val lotteryID = it.arguments?.getString("LotteryID")
            val lottery: Lottery? = if (lotteryID == null) null else lotteryList.getLottery(lotteryID)
            LotteryDetailScreen(
                navController, lottery, ownerID!!,
                ownerSites = campsiteList.campsites.filter { it.ownerID.toString() == ownerID },
                addLottery = { lotteryList.addLottery(it) },
                updateLottery = { old, new -> lotteryList.updateLottery(old, new) })
        }

        composable("Admin Menu Screen") { AdminMenuScreen(navController) }

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

        composable("AdminMessages") { MessageListScreen(navController, messageList.getAllMessages()) }

        composable("AdminMessageDetails/{MessageID}") {
            val messageIDString = it.arguments!!.getString("MessageID")
            val messageID = Uuid.parse(messageIDString!!)
            MessageDetailScreen(navController, messageList.getMessage(messageID), onDelete = {messageList.deleteMessage(it)})
        }

        composable("AdminNotifications") { NotificationListScreen(navController, notificationList.getAllNotifications()) }

        composable("AdminNotificationDetails/{NotificationID}") {
            val notificationIDString = it.arguments!!.getString("NotificationID")
            val notificationID = Uuid.parse(notificationIDString!!)
            NotificationDetailScreen(navController, notificationList.getNotification(notificationID), onDelete = {notificationList.deleteNotification(it)})
        }

        composable("MainEntrantMenu") {
            EntrantMenuScreen(navController)
        }

        composable("EntrantBrowseReservations") {
            val entrant = currentEntrant
            if (entrant != null) {
                EntrantBrowseReservations().BrowseReservations(navController, entrant)
            }
        }

        composable("ActiveReservationScreen") {
            val entrant = currentEntrant
            if (entrant != null) {
                ActiveReservationScreen().ActiveReservations(navController, entrant)
            }
        }

        composable("PastReservationScreen") {
            val entrant = currentEntrant
            if (entrant != null) {
                PastReservationScreen().PastReservations(navController, entrant)
            }
        }

        composable("EntrantSiteBooker/{CampID}") { backStackEntry ->
            val entrant = currentEntrant
            val campID = Uuid.parse(backStackEntry.arguments!!.getString("CampID")!!)
            if (entrant != null) {
                EntrantSiteBooker().SiteBooker(navController, campID, entrant)
            }
        }

    }
}
