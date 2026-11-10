package com.example.project_ballin

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.project_ballin.admin.adminui.NotificationDetailScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Date
import kotlin.uuid.Uuid

@RunWith(AndroidJUnit4::class)
class AdminMessageScreensTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()
    val composeTestRule1 = createComposeRule()
    private val sampleMessages = mutableListOf(
        Message("1","1","Hello", Date(), "1"),
    )


    @Test
    fun navigate_to_admin_messages_screen_from_signin_screen(){
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

    @Test
    fun messages_screen_appear_correctly(){
        composeTestRule
            .onNodeWithTag("adminSignin")
            .performClick()

        composeTestRule
            .onNodeWithTag("adminMessagesButton")
            .performClick()

        composeTestRule
            .onNodeWithTag("checkMessage1")
            .assertExists()
    }

    @Test
    fun details_of_message_appear_correctly() {
        composeTestRule1.setContent {
            MessageDetailScreen().MessageDetail(
                navController = rememberNavController(),
                message = sampleMessages[0],
                onDelete = {}
            )
        }

        composeTestRule1
            .onNodeWithTag("sentBy${sampleMessages[0].ownerID}")
            .assertExists()

        composeTestRule1
            .onNodeWithTag("recievedBy${sampleMessages[0].entrantID}")
            .assertExists()

        composeTestRule1
            .onNodeWithTag("dateSent${sampleMessages[0].getFormattedDate()}")
            .assertExists()

        composeTestRule1
            .onNodeWithTag("messageContent${sampleMessages[0].content}")
            .assertExists()

        composeTestRule1
            .onNodeWithTag("deleteMessage")
            .assertExists()
    }

    @Test
    fun delete_message_verify_deleted_and_logs_screen_updated() {
        composeTestRule.setContent {
            MessageDetailScreen().MessageDetail(
                navController = rememberNavController(),
                message = sampleMessages[0],
                onDelete = { sampleMessages.remove(it) }
            )
        }

        composeTestRule1
            .onNodeWithTag("deleteNotification")
            .performClick()

        composeTestRule1
            .onNodeWithTag("deleteMessage")
            .performClick()

        composeTestRule1
            .onNodeWithTag("confirmDeleteDialog")
            .assertExists()

        composeTestRule1
            .onNodeWithTag("confirmDelete")
            .performClick()

        composeTestRule1
            .onNodeWithTag("checkMessage1")
            .assertDoesNotExist()
    }
}