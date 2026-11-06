package com.example.project_ballin

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.project_ballin.entrant.entantui.EntrantBrowseCampsites
import com.example.project_ballin.entrant.entantui.EntrantBrowseReservations
import com.example.project_ballin.entrant.entantui.EntrantMenuScreen
import com.example.project_ballin.entrant.entantui.EntrantSiteBooker
import com.example.project_ballin.entrant.entantui.PastReservationScreen
import com.example.project_ballin.lottery.Lottery

import com.example.project_ballin.lottery.LotteryList
import com.example.project_ballin.owner.Owner
//import com.example.project_ballin.owner.ownerUI.CampsiteDetailScreen commented out cuz its giving me an error -Nick
import com.example.project_ballin.owner.ownerUI.CampsiteInputScreen
import com.example.project_ballin.owner.ownerUI.CampsiteListScreen
import com.example.project_ballin.owner.ownerUI.LotteryDetailScreen
import com.example.project_ballin.owner.ownerUI.LotteryListScreen
import com.example.project_ballin.owner.ownerUI.OwnerMenuScreen
import com.google.firebase.firestore.Blob
import kotlin.uuid.Uuid
import java.util.Date


/**
 * The main composable function that holds the mappings for the navController.
 * Any function that calls navController to a new location passes through here
 *
 *
 *
 * Sources Used:
 *
 * Title: Pass Arguments to Destinations : Jetpack Compose Navigation
 * Author: Sagar Malhotra
 * Published: April 15, 2023
 * https://sagar0-0.medium.com/pass-arguments-to-destinations-jetpack-compose-navigation-7c29d4c73ce5
 *
 *
 *
 * */



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
    val lotteryList = remember { LotteryList() }

    //temporary for testing
    var currentEntrant by remember {
        mutableStateOf<Entrant?>(
            Entrant(
                firstName = "Nick",
                phoneNum = "123",
                email = "nick@ualberta.ca",
                deviceId = "123",
                profilePic = "a",
                lastName = "",
                userID = "1"
            )
        )
    }
    //temporary for testing
    var currentOwner by remember {
        mutableStateOf<Owner?>(
            Owner(
                firstName = "John",
                phoneNum = "123",
                email = "John@ballin.ca",
                deviceId = "123",
                profilePic = "a",
                lastName = "",
                userID = Uuid.random().toString()
            )
        )
    }

    var campsite by remember { mutableStateOf<Campsite>(
        Campsite(
            "campsite1",
            "2",
            //Location(""),
            lat = 0.0,
            lon = 0.0,
            "Jasper",
            mutableListOf<String>(),
            1.00.toFloat(),
            5.toFloat(),
            "rules",
            "description",
            10,
            mutableListOf<Blob>(),
            "3",
            mutableListOf<Long>(),
            mutableListOf<String>()
        )
    ) }

    var campsite2 by remember { mutableStateOf<Campsite>(
        Campsite(
            "campsite2",
            "4",
            lat = 0.0,
            lon = 0.0,

            "Jasper",
            mutableListOf<String>(),
            1.00.toFloat(),
            5.toFloat(),
            "rules",
            "description",
            10,
            mutableListOf<Blob>(),
            "3",
            mutableListOf<Long>(),
            mutableListOf<String>()
        )
    ) }

    var campsite3 by remember { mutableStateOf<Campsite>(
        Campsite(
            "campsite3",
            "5",
            lat = 0.0,
            lon = 0.0,
            "Jasper",
            mutableListOf<String>(),
            1.00.toFloat(),
            5.toFloat(),
            "rules",
            "description",
            10,
            mutableListOf<Blob>(),
            "3",
            mutableListOf<Long>(),
            mutableListOf<String>()
        )
    ) }

    var lottery by remember { mutableStateOf<Lottery>(
        Lottery(
            mutableListOf<String>(),
            10,
            5,
            null,
            null,
            null,
            null,
            mutableListOf<String>(),
            "6",
            "3",
            mutableListOf<String>(),
            mutableListOf<String>()
        )
    ) }

    var lottery2 by remember { mutableStateOf<Lottery>(
        Lottery(
            mutableListOf<String>(),
            10,
            5,
            null,
            null,
            null,
            null,
            mutableListOf<String>(),
            "7",
            "3",
            mutableListOf<String>(),
            mutableListOf<String>()
        )
    ) }

    var lottery3 by remember { mutableStateOf<Lottery>(
        Lottery(
            mutableListOf<String>(),
            10,
            5,
            null,
            null,
            null,
            null,
            mutableListOf<String>(),
            "8",
            "3",
            mutableListOf<String>(),
            mutableListOf<String>()
        )
    ) }

    var message1 by remember { mutableStateOf<Message>(
        Message(
            Uuid.random().toString(),
            Uuid.random().toString(),
            "You are booked!",
            Date(),
            Uuid.random().toString()
        )
    )}

    var notification1 by remember { mutableStateOf<Notification>(
        Notification(
            "You won the lottery!",
            Date(),
            Uuid.random().toString(),
            Uuid.random().toString()
        )
    )}

    LaunchedEffect(Unit) {
        campsite.lotteries.add(lottery.lotteryID)
        campsite2.lotteries.add(lottery2.lotteryID)
        campsite3.lotteries.add(lottery3.lotteryID)
        lottery.associatedSites.add(campsite.ID)
        lottery2.associatedSites.add(campsite2.ID)
        lottery3.associatedSites.add(campsite3.ID)
        currentEntrant?.let {
            lottery.waitList.add(it.userID)
            it.activeLotteries.add(lottery)

            lottery2.winners.add(it.userID)
            it.wonLotteries.add(lottery2)
            it.activeLotteries.add(lottery2)

            lottery3.losers.add(it.userID)
            it.lostLotteries.add(lottery3)
            it.activeLotteries.add(lottery3)
        }
        campsiteList.addCampsite(campsite)
        campsiteList.addCampsite(campsite2)
        campsiteList.addCampsite(campsite3)
        lotteryList.addLottery(lottery)
        lotteryList.addLottery(lottery2)
        lotteryList.addLottery(lottery3)
        messageList.addMessage(message1)
        notificationList.addNotification(notification1)
    }



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
            CampsiteListScreen(
                navController,
                ownerIDString!!,
                getOwnerSites = { campsiteList.getOwnerSites(it) })
        }

        composable("CampsiteDetailScreen/{CampID}/{userID}") {
            val siteIDString = it.arguments!!.getString("CampID")
            val campsite = campsiteList.getCampsite(siteIDString!!)
            val userID = it.arguments!!.getString("userID")
            CampsiteDetailScreen(navController, campsite!!, userID!!)
        }

        composable("CampsiteInputScreen/{CampID}/{OwnerID}") {
            val siteIDString = it.arguments?.getString("CampID")
            var campsite: Campsite?
            if (siteIDString == null || siteIDString == "none") {
                Log.d("navigation", "no campsite")
                campsite = null
            } else {
                campsite = campsiteList.getCampsite(siteIDString)
            }
            val ownerID = it.arguments!!.getString("OwnerID")

            Log.d("navigation","To input!")

            CampsiteInputScreen(
                navController, campsite, ownerID!!,
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
            val messageID = it.arguments!!.getString("MessageID")
            val message = messageList.getMessage(messageID!!)
            if (message != null) {
                MessageDetailScreen(
                    navController,
                    message,
                    onDelete = { messageList.deleteMessage(it) })
            }
        }

        composable("AdminNotifications") { NotificationListScreen(navController, notificationList.getAllNotifications()) }

        composable("AdminNotificationDetails/{NotificationID}") {
            val notificationID = it.arguments!!.getString("NotificationID")
            val notification = notificationList.getNotification(notificationID!!)
            if (notification != null) {
                NotificationDetailScreen(
                    navController,
                    notification,
                    onDelete = { notificationList.deleteNotification(it) })
            }
        }

        composable("MainEntrantMenu") {
            EntrantMenuScreen(navController)
        }

        composable("EntrantBrowseReservations") {
            val entrant = currentEntrant
            if (entrant != null) {
                EntrantBrowseReservations().BrowseReservations(navController, campsiteList, entrant)
            }
        }

        composable("ActiveReservationScreen") {
            val entrant = currentEntrant
            if (entrant != null) {
                ActiveReservationScreen().ActiveReservations(navController, entrant, campsiteList)
            }
        }

        composable("PastReservationScreen") {
            val entrant = currentEntrant
            if (entrant != null) {
                PastReservationScreen().PastReservations(navController, entrant)
            }
        }

        composable("EntrantSiteBooker/{campID}") { siteToBook ->
            val entrant = currentEntrant
            val campID = siteToBook.arguments?.getString("campID")
            if (entrant != null) {
                EntrantSiteBooker().SiteBooker(navController, campID, campsiteList, entrant)
            }
        }

        composable("Browse Campsites"){
            EntrantBrowseCampsites(navController, campsiteList, currentEntrant!!.userID)
        }

    }
}
