package com.example.project_ballin

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Date
import kotlin.uuid.Uuid

@RunWith(AndroidJUnit4::class)
class AdminMessageScreensTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val uuidRandomOwnerID: Uuid = Uuid.random()
    private val uuidRandomEntrantID: Uuid = Uuid.random()
    private val uuidRandomID: Uuid = Uuid.random()
    private val sampleMessages = mutableListOf(
        Message(uuidRandomOwnerID,uuidRandomEntrantID,"Hello", Date(), uuidRandomID),
    )

    @Test
    fun navigate_to_admin_messages_screen_from_signin_screen(){
        composeTestRule.setContent {
            BallinApp()
        }

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
        composeTestRule.setContent {
            MessageListScreen(
                navController = rememberNavController(),
                messages = sampleMessages
            )
        }

        composeTestRule
            .onNodeWithTag("checkMessage1")
            .assertExists()
    }

    @Test
    fun details_of_message_appear_correctly() {
        composeTestRule.setContent {
            MessageDetailScreen(
                navController = rememberNavController(),
                message = sampleMessages[0],
                onDelete = {}
            )
        }

        composeTestRule
            .onNodeWithTag("sentBy${uuidRandomOwnerID}")
            .assertExists()

        composeTestRule
            .onNodeWithTag("recievedBy${uuidRandomEntrantID}")
            .assertExists()

        composeTestRule
            .onNodeWithTag("dateSent${sampleMessages[0].getFormattedDate()}")
            .assertExists()

        composeTestRule
            .onNodeWithTag("messageContent${sampleMessages[0].content}")
            .assertExists()

        composeTestRule
            .onNodeWithTag("deleteMessage")
            .assertExists()
    }

    @Test
    fun delete_message_verify_deleted_and_logs_screen_updated() {
        composeTestRule.setContent {
            MessageDetailScreen(
                navController = rememberNavController(),
                message = sampleMessages[0],
                onDelete = { sampleMessages.remove(it) }
            )
        }
        composeTestRule
            .onNodeWithTag("deleteMessage")
            .performClick()

        composeTestRule
            .onNodeWithTag("confirmDeleteDialog")
            .assertExists()

        composeTestRule
            .onNodeWithTag("confirmDelete")
            .performClick()

        composeTestRule
            .onNodeWithTag("checkMessage1")
            .assertDoesNotExist()
    }
}