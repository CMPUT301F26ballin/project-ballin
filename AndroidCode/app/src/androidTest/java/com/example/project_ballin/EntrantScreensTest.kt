package com.example.project_ballin

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class EntrantScreensTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

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
            .onNodeWithTag("Active Reservation Button")
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