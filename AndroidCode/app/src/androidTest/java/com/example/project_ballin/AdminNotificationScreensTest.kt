package com.example.project_ballin

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.project_ballin.admin.adminui.NotificationDetailScreen
import com.example.project_ballin.admin.adminui.NotificationListScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Date
import kotlin.uuid.Uuid


@RunWith(AndroidJUnit4::class)
class AdminNotificationScreensTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val uuidRandomID: Uuid = Uuid.random()
    private val sampleNotifications = mutableListOf(
        Notification("Hello", Date(), uuidRandomID)
    )

    @Test
    fun navigate_to_admin_notifications_screen_from_signin_screen() {
        composeTestRule.setContent {
            BallinApp()
        }

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

    @Test
    fun notifications_screen_appear_correctly() {
        composeTestRule.setContent {
            NotificationListScreen(
                navController = rememberNavController(),
                notifications = sampleNotifications
            )
        }

        composeTestRule
            .onNodeWithTag("checkNotification1")
            .assertExists()
    }

    @Test
    fun details_of_notification_appear_correctly() {
        composeTestRule.setContent {
            NotificationDetailScreen(
                navController = rememberNavController(),
                notification = sampleNotifications[0],
                onDelete = {}
            )
        }

        composeTestRule
            .onNodeWithTag("dateSent${sampleNotifications[0].getFormattedDate()}")
            .assertExists()

        composeTestRule
            .onNodeWithTag("notificationContent${sampleNotifications[0].content}")
            .assertExists()

        composeTestRule
            .onNodeWithTag("notificationID${sampleNotifications[0].ID}")
            .assertExists()
    }

    @Test
    fun delete_notification_verify_deleted_and_logs_screen_updated() {
        composeTestRule.setContent {
            NotificationDetailScreen(
                navController = rememberNavController(),
                notification = sampleNotifications[0],
                onDelete = { sampleNotifications.remove(it) }
            )
        }

        composeTestRule
            .onNodeWithTag("deleteNotification")
            .performClick()

        composeTestRule
            .onNodeWithTag("confirmDeleteDialog")
            .assertExists()

        composeTestRule
            .onNodeWithTag("confirmDelete")
            .performClick()

        composeTestRule
            .onNodeWithTag("checkNotification1")
            .assertDoesNotExist()
    }
}