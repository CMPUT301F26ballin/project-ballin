package com.example.project_ballin

import android.annotation.SuppressLint
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
import com.example.project_ballin.admin.adminui.AdminSwitchModesScreen
import com.example.project_ballin.admin.adminui.AdminViolationChoiceScreen
import com.example.project_ballin.admin.adminui.AdminViolationListScreen
import com.example.project_ballin.admin.adminui.NotificationDetailScreen
import com.example.project_ballin.admin.adminui.NotificationListScreen
import com.example.project_ballin.entrant.Entrant
import com.example.project_ballin.entrant.QRCodeScanner
import com.example.project_ballin.entrant.Reservation
import com.example.project_ballin.entrant.entantui.ActiveReservationScreen
import com.example.project_ballin.entrant.entantui.EntrantBookingConfirmation
import com.example.project_ballin.entrant.entantui.EntrantBrowseCampsites
import com.example.project_ballin.entrant.entantui.EntrantBrowseReservations
import com.example.project_ballin.entrant.entantui.EntrantConfirmBooking
import com.example.project_ballin.entrant.entantui.EntrantLeaveCampsiteReview
import com.example.project_ballin.entrant.entantui.EntrantSiteBooker
import com.example.project_ballin.entrant.entantui.EntrantSiteSelector
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
import com.example.project_ballin.reusableui.PolicyGuidelinesScreen
import com.example.project_ballin.reusableui.UserProfile
import com.example.project_ballin.reusableui.UserReportScreen
import com.google.firebase.firestore.Blob
import kotlin.uuid.Uuid
import com.example.project_ballin.owner.ownerUI.NotificationSendScreen
import com.example.project_ballin.owner.ownerUI.OwnerNotificationListScreen
import com.example.project_ballin.owner.ownerUI.WaitlistListScreen

/**
 * The main class that houses the NavHost for the nav controller. All screen changes go through this class view.
 * */
class BallinApp() {
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
     * Title: Passing data between screens | Navigation : Jetpack Compose - 34
     * Posted By: CodeTutor
     * Date: Feb 28, 2024
     * https://www.youtube.com/watch?v=AoU9EoHWpno
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
        val reviewList = remember { ReviewList() }
        val ratingList = remember{ RatingList() }

        var currentEntrant by remember {
            mutableStateOf<Entrant?>(null)
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
        val entrantLotteryList = EntrantLotteryListScreen()

        val signUpScreen = SignUp()
        val userProfile = UserProfile()

        //RESERVATION SCREEN OBJECTS
        val ownerReservationScreen = OwnerReservationScreen()
        val reservationDetailScreen = ReservationDetailScreen()

        //MessagingScreen
        val messagingScreen = MessagingScreen()

        //Reporting/Violation Screens
        val adminViolationChoiceScreen = AdminViolationChoiceScreen()
        val adminViolationListScreen = AdminViolationListScreen()
        val adminViolationDetailScreen = AdminViolationDetailScreen()

        NavHost(navController = navController, startDestination = "AdminMenu/{400400400}") {
            composable("signup") { signUpScreen.Signup(navController) }
            composable("createprofile") { userProfile.CreateProfile(navController) }
            composable("editprofile") { userProfile.EditProfile(navController) }


            composable("OwnerMenu/{OwnerID}") {
                val ownerIDString = it.arguments!!.getString("OwnerID")
                val hasAdminPrivileges = (adminList.getAdmin(ownerIDString!!) != null)
                mainOwnerMenu.OwnerMenuScreen(navController, ownerIDString!!, hasAdminPrivileges)
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

                if (type == "Past") {
                    resList = reservationList.getReservationsBefore(
                        siteIDString!!,
                        System.currentTimeMillis()
                    )
                } else if (type == "Active") {
                    resList = reservationList.getReservationsAfter(
                        siteIDString!!,
                        System.currentTimeMillis()
                    )
                } else if (type == "Cancelled") {
                    resList = reservationList.getCancelledReservations(siteIDString!!)
                }

                ownerReservationScreen.OwnerReservationScreen(
                    navController,
                    resList,
                    ownerID!!,
                    false
                )

            }

            composable("AllOwnerCampReservations/{OwnerID}") {
                val ownerIDString = it.arguments!!.getString("OwnerID")
                val resList = mutableListOf<Reservation>()
                val campList: List<Campsite> = campsiteList.getOwnerSites(ownerIDString!!)
                campList.forEach { campsite ->
                    val tempResList = reservationList.getReservationsAfter(
                        campsite.ID,
                        System.currentTimeMillis()
                    )
                    tempResList.forEach { res -> resList.add(res) }
                }
                ownerReservationScreen.OwnerReservationScreen(
                    navController,
                    resList,
                    ownerIDString,
                    true
                )

            }

            composable("reservationDetailScreen/{resID}/{OwnerID}") {
                val resIDString = it.arguments!!.getString("resID")
                val res = reservationList.getReservation(resIDString!!)
                val ownerID = it.arguments!!.getString("OwnerID")

                reservationDetailScreen.ReservationDetailScreen(
                    navController,
                    res!!,
                    ownerID!!,
                    onCancel = { reservationList.cancelReservation(res) })
            }

            composable("messagingScreen/{senderID}/{receiverID}") {
                val senderID = it.arguments!!.getString("UserID")
                val receiverID = it.arguments!!.getString("OwnerID")

                var recipientName: String
                val user = entrantList.getEntrant(receiverID!!)
                if(user == null){
                    val user = ownerList.getOwner(receiverID)
                    recipientName = user!!.firstName
                }
                else{
                    recipientName = user.firstName
                }


                messagingScreen.MessagingScreen(navController, senderID!!, receiverID!!, recipientName,
                    onAddMessage = {userID, ownerID, messageContent -> messageList.addMessage(
                        Message(
                            senderID = ownerID,
                            recipientID = userID,
                            content = messageContent,
                            dateSent = System.currentTimeMillis()
                        ))},
                    onGetMessages = {senderID, receiverID -> messageList.getMessagesBetween(senderID, receiverID)}
                    )


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
                    ownerLotteries = lotteryList.getOwnerLotteries(ownerID),
                    groupLotteries = lotteryList.getAllGroupLotteries(ownerID),
                    enrolledGroupLotteries = lotteryList.getEnrolledGroupLotteries(ownerID),
                    getCampsite = { campsiteList.getCampsite(it) }
                )
            }

            composable("CampsiteLotteryScreen/{userID}/{siteID}"){
                val userID = it.arguments!!.getString("userID")
                val siteID = it.arguments!!.getString("siteID")

                entrantLotteryList.EntrantLotteryListScreen(
                    navController,
                    userID!!,
                    siteID!!,
                    getLotteries = {lotteryList.getCampsiteLotteries(it)},
                    onEnroll = {lotteryID, entrantID -> lotteryList.enrollUserInLottery(lotteryID, entrantID)}
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
                    sendNotification = { content ->
                        val entrantIDs = lotteryList.getLottery(lotteryID)?.waitList?.toList() ?: emptyList()
                        notificationList.sendToEntrants(content, lotteryID, entrantIDs)
                    }
                )
            }

            composable("OwnerNotificationListScreen/{OwnerID}") {
                val ownerID = it.arguments!!.getString("OwnerID")!!
                val ownerEnrolledLotteries = lotteryList.getOwnerLotteries(ownerID) + lotteryList.getEnrolledGroupLotteries(ownerID)
                val ownerLotteryIDs = ownerEnrolledLotteries.map{
                        l -> l.lotteryID
                }
                val notifs = notificationList.getAllNotifications()
                    .filter { it.lotteryID in ownerLotteryIDs }
                    .distinctBy { it.lotteryID to it.dateSent to it.content }
                OwnerNotificationListScreen().OwnerNotificationListScreen(
                    navController,
                    ownerID,
                    notifs
                )
            }

            composable("WaitlistListScreen/{LotteryID}"){
                val lotteryID = it.arguments!!.getString("LotteryID")!!
                val entrantIDs = lotteryList.getLottery(lotteryID)?.waitList ?: emptyList()
                val entrants = entrantIDs.mapNotNull { id ->
                    entrantList.getEntrant(id)
                }
                WaitlistListScreen().WaitlistListScreen(
                    navController,
                    entrants
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

            composable("AdminQRCodes") {
                AdminQRCodeScreens().AdminQRCodeListScreen(
                    navController,
                    campsites = campsiteList.campsites
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

            composable("AdminViolationChoice") {
                adminViolationChoiceScreen.AdminViolationChoice(navController)
            }

            composable("AdminViolationList/{subject}") {
                val subject = it.arguments!!.getString("subject")
                val reportsFromSubject = userReportList.getReportsInSubject(subject!!)
                adminViolationListScreen.AdminViolationList(
                    navController,
                    reportsFromSubject,
                    onDelete = {  report ->
                        when (subject) {
                            "Campsite Owner" -> {
                                userReportList.deleteReportInSubjectOwner(report)
                            }
                            "Review" -> {
                                userReportList.deleteReportInSubjectReview(report)
                            }
                            "Rating" -> {
                                userReportList.deleteReportInSubjectRating(report)
                            }
                        }
                    }
                )
            }

            composable("AdminViolationDetail/{reportID}") {
                val reportID = it.arguments!!.getString("reportID")
                val report = userReportList.getReport(reportID!!)
                adminViolationDetailScreen.AdminViolationDetail(
                    navController,
                    report!!,
                    onDelete = { report ->
                        when (report.reportSubject) {
                            "Campsite Owner" -> {
                                userReportList.deleteReportInSubjectOwner(report)
                            }
                            "Review" -> {
                                userReportList.deleteReportInSubjectReview(report)
                            }
                            "Rating" -> {
                                userReportList.deleteReportInSubjectRating(report)
                            }
                        }
                    }
                )
            }


            composable("EntrantMenu/{EntrantID}") {
                val entrantIDString = it.arguments!!.getString("EntrantID")
                currentEntrant = entrantList.getEntrant(entrantIDString!!)
                mainEntrantMenu.EntrantMenuScreen(navController, entrantIDString!!)
                val hasAdminPrivileges = (adminList.getAdmin(entrantIDString!!) != null)
                mainEntrantMenu.EntrantMenuScreen(navController, entrantIDString!!, hasAdminPrivileges)
            }

            composable("AdminSwitchModes/{AdminID}") {
                val AdminID = it.arguments!!.getString("AdminID")
                AdminSwitchModesScreen().AdminSwitchModes(AdminID!!, navController, entrantList, ownerList, adminList)
            }


            composable("UserReportScreen/{subjectReport}/{clickedID}") {
                val subjectReport = it.arguments!!.getString("subjectReport")
                val clickedID = it.arguments!!.getString("clickedID")
                UserReportScreen().UserReportReason(
                    navController,
                    subjectReport!!,
                    clickedID!!,
                    onAdd = { userReportList.addReport(it) }
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
                        campsiteList,
                        reservationList
                    )
                }
            }

            composable("PastReservationScreen") {
                val entrant = currentEntrant
                if (entrant != null) {
                    PastReservationScreen().PastReservations(
                        navController,
                        entrant,
                        campsiteList,
                        reservationList
                    )
                }
            }

            composable("EntrantSiteBooker/{campID}/{lotteryID}") { siteToBook ->
                val entrant = currentEntrant
                val campID = siteToBook.arguments?.getString("campID")
                val lotteryID = siteToBook.arguments?.getString("lotteryID")
                val lottery = lotteryList.getLottery(lotteryID!!)

                if (entrant != null) {
                    EntrantSiteBooker().SiteBooker(navController, campID!!, entrant, lottery!!)
                }
            }

            composable("Browse Campsites") {
                entrantBrowseCampsites.EntrantBrowseCampsites(
                    navController,
                    campsiteList,
                    currentEntrant!!.userID
                )
            }

            composable("PoliciesAndGuidelines") {
                PolicyGuidelinesScreen().PolicyGuidelines(navController)
            }

            composable("ScanQRCode/{userID}") {
                val userID = it.arguments?.getString("userID")
                QRCodeScanner.QRCodeScanner(navController, userID!!)
            }

            composable("confirmBooking/{campID}/{chosenDates}/{lotteryID}") {
                val campID = it.arguments?.getString("campID")
                val chosenDates = it.arguments?.getString("chosenDates")
                val lotteryID = it.arguments?.getString("lotteryID")
                if (campID != null) {
                    if (chosenDates != null) {
                        val campsite = campsiteList.getCampsite(campID)
                        val entrant = currentEntrant
                        val chosenDatesList = chosenDates.split(",").map { it.toLong() }
                        val lottery = lotteryList.getLottery(lotteryID!!)

                        EntrantConfirmBooking().ConfirmBooking(
                            navController,
                            campsite!!,
                            entrant!!,
                            chosenDatesList,
                            reservationList,
                            lottery!!
                        )
                    }
                }
            }

            composable("siteSelector/{lotteryID}") {
                val lotteryID = it.arguments?.getString("lotteryID")
                val lottery = lotteryList.getLottery(lotteryID!!)
                EntrantSiteSelector().SiteSelector(
                    navController,
                    campsiteList,
                    currentEntrant!!,
                    lottery
                )
            }

            composable("entrantReviewScreen/{campsiteID}") {
                val campsiteID = it.arguments?.getString("campsiteID")
                val entrant = currentEntrant
                if (campsiteID != null && entrant != null) {
                    EntrantLeaveCampsiteReview().EntrantGiveCampsiteReviewScreen(
                        navController,
                        entrant,
                        campsiteID,
                        reviewList,
                        ratingList
                    )
                }
            }

            composable("bookingConfirmationScreen/{reservationID}"){
                val reservationID = it.arguments?.getString("reservationID")
                val reservation = reservationList.getReservation(reservationID!!)
                val campsite = campsiteList.getCampsite(reservation?.campsiteID)
                val entrant = currentEntrant


                EntrantBookingConfirmation().BookingConfirmation(navController, reservation, campsite, entrant)
            }
        }
    }
}