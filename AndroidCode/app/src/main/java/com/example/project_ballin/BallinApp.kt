package com.example.project_ballin

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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

    val campsiteList = CampsiteList()
    val messageList = MessageList()
    val notificationList = NotificationList()

    // remember = only create one ProfileList
    val profileList = remember { ProfileList() }

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

        composable("EntrantBrowseReservations/{UserID}"){
            val userIDString = it.arguments!!.getString("UserID")
            val user = UserList().getUser(Uuid.parse(userIDString!!))
            EntrantBrowseReservations().BrowseReservations(navController, user as Entrant)
        }

        composable("EntrantSiteBooker/{UserID},{CampID}"){
            val userIDString = it.arguments!!.getString("UserID")
            val user = UserList().getUser(Uuid.parse(userIDString!!))

            val campIDString = it.arguments!!.getString("CampID")
            val campID = Uuid.parse(campIDString!!)
            val campsite = campsiteList.getCampsite(campID)

            EntrantSiteBooker().SiteBooker(navController, campsite, user as Entrant)
        }

        composable("ActiveReservationScreen/{UserID}"){
            val userIDString = it.arguments!!.getString("UserID")
            val user = UserList().getUser(Uuid.parse(userIDString!!))
            ActiveReservationScreen().ActiveReservations(navController, user as Entrant)
        }

        composable("PastReservationScreen/{UserID}") {
            val userIDString = it.arguments!!.getString("UserID")
            val user = UserList().getUser(Uuid.parse(userIDString!!))
            PastReservationScreen().PastReservations(navController, user as Entrant)
        }
    }
}
