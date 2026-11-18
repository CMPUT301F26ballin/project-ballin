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


//Test for declining a reservation in the browse reservations screen when a user wins a lottery
class EntrantDeclineReservationTest {
    private lateinit var campsiteList: CampsiteList
    private lateinit var entrant: Entrant
    private lateinit var campsite: Campsite
    private lateinit var lottery1: Lottery
    private lateinit var lottery2: Lottery
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
        lottery1 = Lottery(mutableListOf(campsite.ID),
            1,
            1,
            12343,
            12345,
            123456,
            1234567,
            mutableListOf(campsite.ID),
            "123",
            "1234567890",
            mutableListOf(entrant.userID),
            mutableListOf()
        )
        lottery2 = Lottery(mutableListOf(campsite.ID),
            1,
            1,
            12343,
            12345,
            123456,
            1234567,
            mutableListOf(campsite.ID),
            "1234",
            "1234567890",
            mutableListOf(),
            mutableListOf(entrant.userID)
        )
    }

    @Before
    fun createLotteryList(){
        lotteryList = LotteryList()
        lotteryList.addLottery(lottery1)
        lotteryList.addLottery(lottery2)

        entrant.activeLotteries.add(lottery1)
        entrant.wonLotteries.add(lottery2)
    }

    @Before
    fun setup() {
        composeTestRule.mainClock.advanceTimeBy(2500)
        composeTestRule.waitForIdle()
    }

    @Test
    fun NavigateToBrowseReservations_WonAndLostLotteriesShow_DeclineLottery() {
        composeTestRule
            .onNodeWithTag("entrantSignin")
            .performClick()

        composeTestRule
            .onNodeWithTag("MY RESERVATIONS")
            .assertExists()
            .performClick()

        composeTestRule
            .onNodeWithText("Browse Reservations")
            .assertExists()

        composeTestRule
            .onNodeWithTag("Won Lottery Card")
            .assertExists()

        composeTestRule
            .onNodeWithTag("Select Dates Button")
            .assertExists()

        composeTestRule
            .onNodeWithTag("Decline Button")
            .assertExists()
            .performClick()

        composeTestRule
            .onNodeWithTag("Confirm Action Dialog")
            .assertExists()

        composeTestRule
            .onNodeWithTag("Cancel Button")
            .assertExists()
            .performClick()

        composeTestRule
            .onNodeWithTag("Won Lottery Card")
            .assertExists()

        composeTestRule
            .onNodeWithTag("Decline Button")
            .performClick()

        composeTestRule
            .onNodeWithTag("Confirm Button")
            .performClick()

        composeTestRule
            .onNodeWithTag("Won Lottery Card")
            .assertDoesNotExist()

        composeTestRule
            .onNodeWithTag("Lost Lottery Card")
            .assertExists()

        composeTestRule
            .onNodeWithTag("Dismiss Button")
            .performClick()

        composeTestRule
            .onNodeWithTag("Lost Lottery Card")
            .assertDoesNotExist()
    }


    @After
    fun cleanUp() {
        campsiteList.deleteCampsite(campsite)
        lotteryList.deleteLottery(lottery1)
        lotteryList.deleteLottery(lottery2)
    }
}