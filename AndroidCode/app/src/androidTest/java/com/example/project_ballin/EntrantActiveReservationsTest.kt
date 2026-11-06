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


//Test for checking what shows up in the active reservations screen
class EntrantActiveReservationsTest {
    private lateinit var campsiteList: CampsiteList
    private lateinit var entrant: Entrant
    private lateinit var campsite: Campsite
    private lateinit var campsite2: Campsite
    private lateinit var campsite3: Campsite
    private lateinit var lottery: Lottery
    private lateinit var lottery2: Lottery
    private lateinit var lottery3: Lottery
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

        campsite2 = Campsite(
            "Waterton Campground 2",
            "2",
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

        campsite3 = Campsite(
            "Waterton Campground 3",
            "3",
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
        campsiteList.addCampsite(campsite2)
        campsiteList.addCampsite(campsite3)
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
        lottery2 = Lottery(mutableListOf(campsite.ID),
            1,
            1,
            12343,
            12345,
            123456,
            1234567,
            mutableListOf(campsite2.ID),
            "123",
            "1234567890",
            mutableListOf(entrant.userID),
            mutableListOf()
        )
        lottery3 = Lottery(mutableListOf(campsite.ID),
            1,
            1,
            12343,
            12345,
            123456,
            1234567,
            mutableListOf(campsite3.ID),
            "1234",
            "1234567890",
            mutableListOf(),
            mutableListOf(entrant.userID)
        )
    }

    @Before
    fun createLotteryList(){
        lotteryList = LotteryList()
        lotteryList.addLottery(lottery)
        lotteryList.addLottery(lottery2)
        lotteryList.addLottery(lottery3)

        entrant.activeLotteries.add(lottery)
        entrant.wonLotteries.add(lottery2)
        entrant.lostLotteries.add(lottery3)
    }

    @Test
    fun NavigateToActiveReservations_ActiveReservationsScreenIsDisplayed_ActiveLotteriesShow() {
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
            .onNodeWithTag("Active Reservation Button")
            .assertExists()

        composeTestRule
            .onNodeWithTag("Active Reservation Button")
            .performClick()

        composeTestRule
            .onNodeWithText("Active Reservations")
            .assertExists()

        composeTestRule
            .onNodeWithTag("Active Lottery Card")
            .assertExists()

        composeTestRule
            .onNodeWithText("Waterton Campground 2")
            .assertDoesNotExist()

        composeTestRule
            .onNodeWithText("Waterton Campground 3")
            .assertDoesNotExist()

    }


    @After
    fun cleanUp() {
        campsiteList.deleteCampsite(campsite)
        campsiteList.deleteCampsite(campsite2)
        campsiteList.deleteCampsite(campsite3)
        lotteryList.deleteLottery(lottery)
        lotteryList.deleteLottery(lottery2)
        lotteryList.deleteLottery(lottery3)
    }
}