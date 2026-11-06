package com.example.project_ballin

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.project_ballin.entrant.Entrant
import com.example.project_ballin.lottery.Lottery
import com.example.project_ballin.lottery.LotteryList
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class EntrantLeaveWaitListTest {
    private lateinit var campsiteList: CampsiteList
    private lateinit var entrant: Entrant
    private lateinit var campsite: Campsite
    private lateinit var lottery: Lottery
    private lateinit var lotteryList: LotteryList

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun createEntrant(){
        entrant = Entrant()
    }

    @Before
    fun createCampsite(){
        campsite = Campsite(
            "Waterton Campground 1",
            "1",
            1.toDouble(),
            1.toDouble(),
            "OnePlace",
            mutableListOf("One", "1"),
            1.toFloat(),
            1.toFloat(),
            "only 1",
            "one and only",
            1,
        )
    }

    @Before
    fun createCampsiteList(){
        campsiteList = CampsiteList()
        campsiteList.addCampsite(campsite)
    }

    @Before
    fun createLotteries(){
        lottery = Lottery(
            mutableListOf(campsite.ID),
            1,
            1,
            12343,
            12345,
            123456,
            1234567,
            mutableListOf(campsite.ID),
            "12",
            "1234567890",
            mutableListOf(),
            mutableListOf()
        )
    }

    @Before
    fun createLotteryList(){
        lotteryList = LotteryList()
        lotteryList.addLottery(lottery)

        entrant.activeLotteries.add(lottery)
    }

    @Test
    fun NavigateToBrowseReservations_BrowseReservationsScreenIsDisplayed_WonAndLostLotteriesShow() {
        composeTestRule
            .onNodeWithTag("entrantSignin")
            .performClick()

        composeTestRule
            .onNodeWithText("MY RESERVATIONS")
            .assertExists()
            .performClick()

        composeTestRule
            .onNodeWithText("Browse Reservations")
            .assertExists()

        composeTestRule
            .onNodeWithTag("Active Reservation Button")
            .assertExists()
            .performClick()

        composeTestRule
            .onNodeWithText("Active Reservations")
            .assertExists()

        composeTestRule
            .onNodeWithTag("Active Lottery Card")
            .assertExists()

        composeTestRule
            .onNodeWithTag("Leave Waiting List Button")
            .assertExists()

        composeTestRule
            .onNodeWithTag("Owner Messages Button")
            .assertExists()

        composeTestRule
            .onNodeWithTag("Leave Waiting List Button")
            .performClick()

        composeTestRule
            .onNodeWithTag("Confirm Action Dialog")
            .assertExists()

        composeTestRule
            .onNodeWithTag("Cancel Button")
            .assertExists()
            .performClick()

        composeTestRule
            .onNodeWithTag("Active Lottery Card")
            .assertExists()

        composeTestRule
            .onNodeWithTag("Leave Waiting List Button")
            .assertExists()
            .performClick()

        composeTestRule
            .onNodeWithTag("Confirm Action Dialog")
            .assertExists()

        composeTestRule
            .onNodeWithTag("Confirm Button")
            .assertExists()
            .performClick()

        composeTestRule
            .onNodeWithTag("Active Lottery Card")
            .assertDoesNotExist()

    }


    @After
    fun cleanUp() {
        campsiteList.deleteCampsite(campsite)
        lotteryList.deleteLottery(lottery)
    }
}