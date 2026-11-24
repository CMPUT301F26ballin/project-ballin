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


@RunWith(AndroidJUnit4::class)
class AdminMessageDetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    
    private val sampleMessages = mutableListOf(
        Message("1","1","Hello", System.currentTimeMillis(), "1"),
    )

    @Test
    fun details_of_message_appear_correctly() {
        composeTestRule.setContent {
            MessageDetailScreen().MessageDetail(
                navController = rememberNavController(),
                message = sampleMessages[0],
                onDelete = {}
            )
        }

        composeTestRule
            .onNodeWithTag("sentBy${sampleMessages[0].senderID}")
            .assertExists()

        composeTestRule
            .onNodeWithTag("recievedBy${sampleMessages[0].recipientID}")
            .assertExists()

        composeTestRule
            .onNodeWithTag("dateSent${sampleMessages[0].dateSent}")
            .assertExists()

        composeTestRule
            .onNodeWithTag("messageContent${sampleMessages[0].content}")
            .assertExists()

        composeTestRule
            .onNodeWithTag("removeMessage")
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

        composeTestRule
            .onNodeWithTag("removeMessage")
            .assertExists()

        composeTestRule
            .onNodeWithTag("removeMessage")
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