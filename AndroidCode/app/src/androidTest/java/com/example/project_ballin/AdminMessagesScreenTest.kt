package com.example.project_ballin

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AdminMessagesScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()


    @Test
    fun navigate_to_admin_messages_screen_from_signin_screen(){
        // the following two lines of code are from Microsoft, Copilot,
        // "how to wait for composable to finish in kotlin, for testing",
        // 2026-11-10. I used it because ballinApp() has an initial screen loading
        composeTestRule.mainClock.advanceTimeBy(2500)
        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithTag("adminSignin")
            .assertExists()

        composeTestRule
            .onNodeWithTag("adminSignin")
            .performClick()

       composeTestRule
          .onNodeWithTag("adminMessagesButton")
          .assertExists()

       composeTestRule
           .onNodeWithTag("adminMessagesButton")
           .performClick()
    }
}