package com.example.project_ballin

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AdminNotificationScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun navigate_to_admin_notifications_screen_from_signin_screen() {
        composeTestRule.mainClock.advanceTimeBy(2500)
        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithTag("adminSignin")
            .assertExists()

        composeTestRule
            .onNodeWithTag("adminSignin")
            .performClick()

        composeTestRule
            .onNodeWithTag("adminNotificationsButton")
            .assertExists()

        composeTestRule
            .onNodeWithTag("adminNotificationsButton")
            .performClick()
    }
}