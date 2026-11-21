package com.example.project_ballin

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import com.example.project_ballin.lottery.Lottery
import com.example.project_ballin.owner.ownerUI.LotteryDetailScreen
import com.example.project_ballin.owner.ownerUI.LotteryListScreen
import junit.framework.TestCase.assertFalse
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class LotteryScreenTest {
    private lateinit var lottery: Lottery
    private lateinit var campsite: Campsite

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun createLottery(){
        lottery = Lottery(
            lotteryID = "L1",
            lotteryOwnerID = "owner1",
            numWinners = 2,
            associatedSites = mutableListOf("S1"),
            registrationStart = 1_700_000_000_000L,
            registrationEnd = 1_700_500_000_000L,
            bookingStart = 1_701_000_000_000L,
            bookingEnd = 1_701_500_000_000L
        )
    }

    @Before
    fun createCampsite(){
        campsite = Campsite(
            name = "Waterton Campground 1",
            ID = "S1",
            description = "A quiet lakeside site",
            ownerID = "owner1"
        )
    }

    @Before
    fun setup() {
        composeTestRule.mainClock.advanceTimeBy(2500)
        composeTestRule.waitForIdle()
    }

    @Test
    fun lottery_list_shows_owner_lotteries(){
        composeTestRule.setContent {
            LotteryListScreen().LotteryListView(
                navController = rememberNavController(),
                ownerID = "owner1",
                lotteries = listOf(lottery),
                getCampsite = { null }
            )
        }

        composeTestRule
            .onNodeWithTag("lotteryItem_${lottery.lotteryID}")
            .assertExists()

        composeTestRule
            .onNodeWithTag("newLotteryButton")
            .assertExists()
    }

    @Test
    fun lottery_list_empty_shows_message(){
        composeTestRule.setContent {
            LotteryListScreen().LotteryListView(
                navController = rememberNavController(),
                ownerID = "owner1",
                lotteries = emptyList(),
                getCampsite = { null }
            )
        }

        composeTestRule
            .onNodeWithText("No active lotteries")
            .assertExists()
    }

    @Test
    fun confirm_with_empty_fields_does_not_add_lottery(){
        var added = false
        composeTestRule.setContent {
            LotteryDetailScreen().LotteryDetailScreen(
                navController = rememberNavController(),
                lottery = null,
                ownerID = "owner1",
                ownerSites = emptyList(),
                getCampsite = { null },
                addLottery = { added = true },
                updateLottery = { _, _ -> }
            )
        }

        composeTestRule
            .onNodeWithTag("confirmLotteryButton")
            .performClick()

        assertFalse(added)
    }

    @Test
    fun edit_lottery_valid_details_saves_lottery(){
        var updated: Lottery? = null
        composeTestRule.setContent {
            LotteryDetailScreen().LotteryDetailScreen(
                navController = rememberNavController(),
                lottery = lottery,
                ownerID = "owner1",
                ownerSites = listOf(campsite),
                getCampsite = { null },
                addLottery = {},
                updateLottery = { _, new -> updated = new }
            )
        }

        composeTestRule
            .onNodeWithTag("WinnerCountEntry")
            .performScrollTo()
            .performTextClearance()

        composeTestRule
            .onNodeWithTag("WinnerCountEntry")
            .performTextInput("5")

        composeTestRule
            .onNodeWithTag("confirmLotteryButton")
            .performClick()

        assertNotNull(updated)
        assertEquals(5, updated!!.numWinners)
        assertEquals(lottery.lotteryID, updated!!.lotteryID)
    }
}