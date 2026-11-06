package com.example.project_ballin

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test


//Test to make sure path to each screen works correctly
class EntrantScreensTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun EntrantGoesToSearchCampsites(){
        composeTestRule
            .onNodeWithTag("entrantSignin")
            .performClick()

        composeTestRule
            .onNodeWithTag("BROWSE CAMPSITES")
            .assertExists()
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
            .onNodeWithTag("MY RESERVATIONS")
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
            .onNodeWithTag("back")
            .performClick()

        composeTestRule
            .onNodeWithText("Browse Reservations")
            .assertExists()

        composeTestRule
            .onNodeWithTag("Past Reservation Button")
            .performClick()

        composeTestRule
            .onNodeWithText("Past Reservations")
            .assertExists()

        composeTestRule
            .onNodeWithTag("back")
            .performClick()

        composeTestRule
            .onNodeWithText("Browse Reservations")
            .assertExists()

    }

    @Test
    fun EntrantGoesToScanQRCode(){
        composeTestRule
            .onNodeWithTag("entrantSignin")
            .performClick()

        composeTestRule
            .onNodeWithTag("SCAN QR CODE")
            .assertExists()
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
            .onNodeWithTag("POLICY AND GUIDELINES")
            .assertExists()
            .performClick()

        composeTestRule
            .onNodeWithText("Policy and Guidelines")
            .assertExists()
    }
}