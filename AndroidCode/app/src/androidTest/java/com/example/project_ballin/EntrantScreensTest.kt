package com.example.project_ballin

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.project_ballin.entrant.Entrant
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.uuid.Uuid

class EntrantScreensTest {
    private lateinit var campsiteList: CampsiteList
    private lateinit var entrant: Entrant


    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun createCampsiteList(){
        campsiteList = CampsiteList()
        campsiteList.addCampsite(
            Campsite(
                "Waterton Campground 1",
                Uuid.random().toString(),
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
        )
    }

    @Before
    fun createEntrant(){
        entrant = Entrant()
    }

    @Test
    fun EntrantGoesToSearchCampsites(){
        composeTestRule
            .onNodeWithTag("entrantSignin")
            .performClick()

        composeTestRule
            .onNodeWithText("BROWSE CAMPSITES")
            .assertExists()

        composeTestRule
            .onNodeWithText("BROWSE CAMPSITES")
            .performClick()

        composeTestRule
            .onNodeWithText("Search Campsites")
            .assertExists()

        composeTestRule
            .onNodeWithTag("Search Bar")
            .assertExists()
    }

    @Test
    fun EntrantGoesToBrowseReservations(){
        composeTestRule
            .onNodeWithTag("entrantSignin")
            .performClick()

        composeTestRule
            .onNodeWithText("MY RESERVATIONS")
            .assertExists()

        composeTestRule
            .onNodeWithText("MY RESERVATIONS")
            .performClick()

        composeTestRule
            .onNodeWithText("Browse Reservations")
            .assertExists()

        composeTestRule
            .onNodeWithTag("Active Reservation Button")
            .assertExists()

        composeTestRule
            .onNodeWithTag("Past Reservation Button")
            .assertExists()
    }

    @Test
    fun EntrantGoesToScanQRCode(){
        composeTestRule
            .onNodeWithTag("entrantSignin")
            .performClick()

        composeTestRule
            .onNodeWithText("SCAN QR CODE")
            .assertExists()

        composeTestRule
            .onNodeWithText("SCAN QR CODE")
            .performClick()

        composeTestRule
            .onNodeWithText("Scan QR Code")
            .assertExists()
    }

    @Test
    fun EntrantGoesToPolicyAndGuidelines(){
        composeTestRule
            .onNodeWithTag("entrantSignin")
            .performClick()

        composeTestRule
            .onNodeWithText("POLICY AND GUIDELINES")
            .assertExists()

        composeTestRule
            .onNodeWithText("POLICY AND GUIDELINES")
            .performClick()

        composeTestRule
            .onNodeWithText("Policy and Guidelines")
            .assertExists()
    }
}