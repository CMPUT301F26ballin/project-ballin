package com.example.project_ballin

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.project_ballin.admin.adminui.AdminSwitchModesScreen
import com.example.project_ballin.admin.adminui.NotificationDetailScreen
import com.example.project_ballin.admin.adminui.NotificationListScreen
import com.example.project_ballin.entrant.Entrant
import com.example.project_ballin.entrant.QRCodeScanner
import com.example.project_ballin.entrant.Reservation
import com.example.project_ballin.entrant.entantui.ActiveReservationScreen
import com.example.project_ballin.entrant.entantui.EditProfileScreen
import com.example.project_ballin.entrant.entantui.EntrantBrowseCampsites
import com.example.project_ballin.entrant.entantui.EntrantBrowseReservations
import com.example.project_ballin.entrant.entantui.EntrantSiteBooker
import com.example.project_ballin.entrant.entantui.MainEntrantMenu
import com.example.project_ballin.entrant.entantui.PastReservationScreen
import com.example.project_ballin.lottery.Lottery
import com.example.project_ballin.lottery.LotteryList
import com.example.project_ballin.owner.Owner
//import com.example.project_ballin.owner.ownerUI.CampsiteDetailScreen commented out cuz its giving me an error -Nick
import com.example.project_ballin.owner.ownerUI.CampsiteInputScreen
import com.example.project_ballin.owner.ownerUI.CampsiteListScreen
import com.example.project_ballin.owner.ownerUI.LotteryDetailScreen
import com.example.project_ballin.owner.ownerUI.LotteryListScreen
import com.example.project_ballin.owner.ownerUI.MainOwnerMenu
import com.example.project_ballin.owner.ownerUI.OwnerReservationScreen
import com.example.project_ballin.reusableui.InitialScreen
import com.example.project_ballin.reusableui.PolicyGuidelinesScreen
import com.example.project_ballin.reusableui.UserDetailModel
import com.example.project_ballin.reusableui.UserProfile
import com.example.project_ballin.reusableui.UserReportScreen
import com.google.firebase.firestore.Blob
import kotlin.uuid.Uuid
import java.util.Date
import com.example.project_ballin.owner.ownerUI.NotificationSendScreen

/**
 * The main class that houses the NavHost for the nav controller. All screen changes go through this class view.
 * */
class BallinApp(){
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
     *
     *
     * */


    @SuppressLint("NotConstructor")
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
        val userReportList = remember { ReportList() }
        val reservationList = remember { ReservationList() }

        //temporary for testing
        var currentEntrant by remember {
            mutableStateOf<Entrant?>(
                Entrant(
                    firstName = "Nick",
                    phoneNum = "123567890",
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
                    userID = "Test1"
                )
            )
        }

        var campsite by remember {
            mutableStateOf<Campsite>(
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
            )
        }

        var campsite2 by remember {
            mutableStateOf<Campsite>(
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
            )
        }

        var campsite3 by remember {
            mutableStateOf<Campsite>(
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
            )
        }

        var lottery by remember {
            mutableStateOf<Lottery>(
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
            )
        }

        var lottery2 by remember {
            mutableStateOf<Lottery>(
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
            )
        }

        var lottery3 by remember {
            mutableStateOf<Lottery>(
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
            )
        }

        var message1 by remember {
            mutableStateOf<Message>(
                Message(
                    Uuid.random().toString(),
                    Uuid.random().toString(),
                    "You are booked!",
                    Date(),
                    Uuid.random().toString()
                )
            )
        }

        var notification1 by remember {
            mutableStateOf<Notification>(
                Notification(
                    "You won the lottery!",
                    Date(),
                    Uuid.random().toString(),
                    Uuid.random().toString()
                )
            )
        }

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


        val campsiteInputScreen = CampsiteInputScreen()
        val campsiteDetailScreen = CampsiteDetailScreen()
        val campsiteListScreen = CampsiteListScreen()

        val QRCodeScanner = QRCodeScanner()

        //PROFILE SCREEN OBJECTS
        //val editProfileScreen = EditProfileScreen()

        //MENU SCREEN OBJECTS
        val mainOwnerMenu = MainOwnerMenu()
        val mainAdminMenu = MainAdminMenu()
        val mainEntrantMenu = MainEntrantMenu()

        val entrantBrowseCampsites = EntrantBrowseCampsites()

        val signUpScreen = SignUp()
        val userProfile = UserProfile()

        //RESERVATION SCREEN OBJECTS
        val ownerReservationScreen = OwnerReservationScreen()
        val reservationDetailScreen = ReservationDetailScreen()

        NavHost(navController = navController, startDestination = "signup") {
            composable("signup") { signUpScreen.Signup(navController) }
            composable("createprofile") { userProfile.CreateProfile(navController) }
            composable("editprofile") { userProfile.EditProfile(navController) }


            composable("OwnerMenu/{OwnerID}") {
                val ownerIDString = it.arguments!!.getString("OwnerID")
                mainOwnerMenu.OwnerMenuScreen(navController, ownerIDString!!)
            }

            composable("CampsiteListScreen/{OwnerID}") {
                val ownerIDString = it.arguments!!.getString("OwnerID")
                campsiteListScreen.CampsiteListScreen(
                    navController,
                    ownerIDString!!,
                    getOwnerSites = { campsiteList.getOwnerSites(it) })
            }

            composable("CampsiteDetailScreen/{CampID}/{userID}") {
                val siteIDString = it.arguments!!.getString("CampID")
                val campsite = campsiteList.getCampsite(siteIDString!!)
                val userID = it.arguments!!.getString("userID")
                campsiteDetailScreen.CampsiteDetailScreen(navController, campsite!!, userID!!)
            }

            composable("ownerReservationScreen/{CampID}/{Type}/{OwnerID}") {
                val siteIDString = it.arguments!!.getString("CampID")
                val type = it.arguments!!.getString("Type")
                var resList = listOf<Reservation>()
                val ownerID = it.arguments!!.getString("OwnerID")

                if(type == "Past"){
                    resList = reservationList.getReservationsBefore(siteIDString!!, System.currentTimeMillis())
                }
                else if(type == "Active"){
                    resList = reservationList.getReservationsAfter(siteIDString!!, System.currentTimeMillis())
                }
                else if(type == "Cancelled"){
                    resList = reservationList.getCancelledReservations(siteIDString!!)
                }

                ownerReservationScreen.OwnerReservationScreen(navController, resList,ownerID!!, false)

            }

            composable("AllOwnerCampReservations/{OwnerID}") {
                val ownerIDString = it.arguments!!.getString("OwnerID")
                val resList = mutableListOf<Reservation>()
                val campList: List<Campsite> = campsiteList.getOwnerSites(ownerIDString!!)
                campList.forEach { campsite ->
                    val tempResList = reservationList.getReservationsAfter(campsite.ID, System.currentTimeMillis())
                    tempResList.forEach { res -> resList.add(res) }
                }
                ownerReservationScreen.OwnerReservationScreen(navController, resList, ownerIDString, true)

            }

            composable("reservationDetailScreen/{resID}/{OwnerID}"){
                val resIDString = it.arguments!!.getString("resID")
                val res = reservationList.getReservation(resIDString!!)
                val ownerID = it.arguments!!.getString("OwnerID")

                reservationDetailScreen.ReservationDetailScreen(navController, res!!,ownerID!!, onCancel = {reservationList.cancelReservation(res)})
            }

            composable("messagingScreen/{UserID}/{OwnerID}"){
                val userIDString = it.arguments!!.getString("UserID")
                val ownerID = it.arguments!!.getString("OwnerID")


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

                Log.d("navigation", "To input!")

                campsiteInputScreen.CampsiteInputScreen(
                    navController, campsite, ownerID!!,
                    addSite = {
                        Log.d("Site Addition", "Adding Site")
                        campsiteList.addCampsite(it)
                    },
                    updateSite = { oldSite, newSite ->
                        campsiteList.updateCampsite(
                            oldSite = oldSite,
                            newSite
                        )
                    })
            }
            composable("CampsiteActiveReservations/{SiteID}") {
                val siteIDString = it.arguments!!.getString("SiteID")

            }

            composable("LotteryListScreen/{OwnerID}") {
                val ownerID = it.arguments!!.getString("OwnerID")
                LotteryListScreen().LotteryListScreen(
                    navController,
                    ownerID!!,
                    getOwnerLotteries = { lotteryList.getOwnerLotteries(it) },
                    getCampsite = { campsiteList.getCampsite(it) }
                )
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
                val lottery: Lottery? =
                    if (lotteryID == null) null else lotteryList.getLottery(lotteryID)
                LotteryDetailScreen().LotteryDetailScreen(
                    navController, lottery, ownerID!!,
                    ownerSites = campsiteList.getOwnerSites(ownerID),
                    getCampsite = { campsiteList.getCampsite(it) },
                    addLottery = { lotteryList.addLottery(it) },
                    updateLottery = { old, new -> lotteryList.updateLottery(old, new) })
            }

            composable("NotificationSendScreen/{OwnerID}/{LotteryID}") {
                val ownerID = it.arguments!!.getString("OwnerID")!!
                val lotteryID = it.arguments!!.getString("LotteryID")!!
                NotificationSendScreen().NotificationSendScreen(
                    navController,
                    ownerID,
                    lotteryID,
                    sendNotification = { id, content ->
                        lotteryList.getLottery(id) != null &&
                        notificationList.sendToLottery(content, id)
                    }
                )
            }

            composable("AdminMenu/{AdminID}") {
                val adminIDString = it.arguments!!.getString("AdminID")
                mainAdminMenu.AdminMenuScreen(navController, adminIDString!!)
            }

            composable("AdminCampsites") {
                AdminCampsiteScreens().AdminCampsiteListScreen(
                    navController,
                    campsites = campsiteList.campsites,
                    onDelete = { campsiteList.deleteCampsite(it) }
                )
            }

            composable("AdminCampsiteDetail/{CampID}") {
                val siteID = it.arguments?.getString("CampID") ?: ""
                AdminCampsiteScreens().AdminCampsiteDetailScreen(
                    navController,
                    site = campsiteList.getCampsite(siteID),
                    onDelete = { campsiteList.deleteCampsite(it) }
                )
            }

            composable("AdminImages") {
                val adminImageScreens = AdminImageScreens()
                adminImageScreens.AdminImageListScreen(
                    navController,
                    campsites = campsiteList.campsites,
                    onRemove = { image ->
                        adminImageScreens.removeImage(image) { oldSite, newSite ->
                            campsiteList.updateCampsite(oldSite, newSite)
                        }
                    }
                )
            }

            composable("AdminProfiles") {
                AdminProfilesScreens().AdminProfileListScreen(
                    navController,
                    profiles = profileList.profiles,
                    onDelete = { profileList.deleteProfile(it) }
                )
            }

            composable("AdminProfileDetail/{UserID}") {
                val userID = it.arguments?.getString("UserID") ?: ""
                AdminProfilesScreens().AdminProfileDetailScreen(
                    navController,
                    profile = profileList.getProfile(userID),
                    onDelete = { profileList.deleteProfile(it) }
                )
            }

            composable("AdminMessages") {
                MessageListScreen().MessageList(
                    navController,
                    messageList.getAllMessages(),
                    onDelete = { messageList.deleteMessage(it) }
                )
            }

            composable("AdminMessageDetails/{MessageID}") {
                val messageID = it.arguments!!.getString("MessageID")
                val message = messageList.getMessage(messageID!!)
                if (message != null) {
                    MessageDetailScreen().MessageDetail(
                        navController,
                        message,
                        onDelete = { messageList.deleteMessage(it) }
                    )
                }
            }

            composable("AdminNotifications") {
                NotificationListScreen().NotificationList(
                    navController,
                    notificationList.getAllNotifications(),
                    onDelete = { notificationList.deleteNotification(it) }
                )
            }

            composable("AdminNotificationDetails/{NotificationID}") {
                val notificationID = it.arguments!!.getString("NotificationID")
                val notification = notificationList.getNotification(notificationID!!)
                if (notification != null) {
                    NotificationDetailScreen().NotificationDetail(
                        navController,
                        notification,
                        onDelete = { notificationList.deleteNotification(it) })
                }
            }

            composable("EntrantMenu/{EntrantID}") {
                val entrantIDString = it.arguments!!.getString("EntrantID")
                mainEntrantMenu.EntrantMenuScreen(navController, entrantIDString!!)
            }

            composable("AdminSwitchModes") {
                AdminSwitchModesScreen().AdminSwitchModes(navController)
            }


            composable("UserReportScreen/{subjectReport}/{clickedID}") {
                val subjectReport = it.arguments!!.getString("subjectReport")
                val clickedID = it.arguments!!.getString("clickedID")
                UserReportScreen().UserReportReason(
                    navController,
                    subjectReport!!,
                    clickedID!!,
                    onAdd = {userReportList.addReport(it)}
                )
            }

            composable("MainEntrantMenu") {
                mainEntrantMenu.EntrantMenuScreen(navController, "")
            }

            composable("EntrantBrowseReservations") {
                val entrant = currentEntrant
                if (entrant != null) {
                    EntrantBrowseReservations().BrowseReservations(
                        navController,
                        campsiteList,
                        entrant
                    )
                }
            }

            composable("ActiveReservationScreen") {
                val entrant = currentEntrant
                if (entrant != null) {
                    ActiveReservationScreen().ActiveReservations(
                        navController,
                        entrant,
                        campsiteList
                    )
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

            composable("Browse Campsites") {
                entrantBrowseCampsites.EntrantBrowseCampsites(navController, campsiteList, currentEntrant!!.userID)
            }

            composable("PoliciesAndGuidelines") {
                PolicyGuidelinesScreen().PolicyGuidelines(navController)
            }

            composable("Scan QR Code") {
                QRCodeScanner.QRCodeScanner(navController)
            }
        }
    }
}
