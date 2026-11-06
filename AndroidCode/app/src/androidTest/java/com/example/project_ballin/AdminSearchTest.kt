package com.example.project_ballin

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.project_ballin.admin.adminui.NotificationListScreen
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Date

/**
 * Tests for searching the admin's lists (US 03.02.02, US 03.04.03, US 03.06.02, US 03.07.02).
 * Uses local lists, so no database needed.
 */
@RunWith(AndroidJUnit4::class)
class AdminSearchTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val adminSearch = AdminSearch()

    private val john = Profile(
        firstName = "John", lastName = "Ballin", email = "john@example.com",
        phoneNum = "780-555-0101", role = "Entrant", userID = "user1"
    )
    private val jane = Profile(
        firstName = "Jane", lastName = "Keller", email = "jane@example.com",
        role = "Owner", userID = "user2"
    )

    private val waterton = Campsite(
        name = "Waterton Campground", ID = "site1", locationName = "Waterton Lakes",
        tags = mutableListOf("Lakeside"), description = "Quiet sites by the water", ownerID = "owner1"
    )
    private val banff = Campsite(
        name = "Banff Hilltop", ID = "site2", locationName = "Banff",
        tags = mutableListOf("Mountain"), ownerID = "owner2"
    )

    private val fireMessage = Message("owner1", "entrant1", "Fire ban this weekend", Date(), "m1")
    private val welcomeMessage = Message("owner2", "entrant2", "Welcome to the campsite", Date(), "m2")

    private val wonNotification = Notification("You won the lottery!", Date(), "n1", "entrant1")
    private val cancelledNotification = Notification("Your reservation was cancelled", Date(), "n2", "entrant2")

    // ---------- matchesQuery (the shared matching rule) ----------

    @Test
    fun matchesQuery_ignoresUpperAndLowerCase() {
        assertTrue(adminSearch.matchesQuery("WATERTON", "Waterton Lakes"))
        assertTrue(adminSearch.matchesQuery("waterton", "Waterton Lakes"))
    }

    @Test
    fun matchesQuery_ignoresSpacesAtStartAndEnd() {
        assertTrue(adminSearch.matchesQuery("  lakes  ", "Waterton Lakes"))
    }

    @Test
    fun matchesQuery_findsTextInTheMiddleOfAField() {
        assertTrue(adminSearch.matchesQuery("ball", "John Ballin"))
    }

    @Test
    fun matchesQuery_emptySearchMatchesEverything() {
        assertTrue(adminSearch.matchesQuery("", "anything"))
        assertTrue(adminSearch.matchesQuery("   ", "anything"))
    }

    @Test
    fun matchesQuery_checksEveryField() {
        assertTrue(adminSearch.matchesQuery("banff", "Waterton", "Banff", "Jasper"))
        assertFalse(adminSearch.matchesQuery("canmore", "Waterton", "Banff", "Jasper"))
    }

    // ---------- matchesSearch for each kind of item ----------

    @Test
    fun profileSearch_checksNameEmailPhoneRoleAndID() {
        with(AdminProfilesScreens()) {
            assertTrue(john.matchesSearch("john ballin")) // full name
            assertTrue(jane.matchesSearch("jane@"))       // email
            assertTrue(john.matchesSearch("555-0101"))    // phone number
            assertTrue(jane.matchesSearch("owner"))       // role
            assertTrue(jane.matchesSearch("user2"))       // user ID
            assertFalse(john.matchesSearch("owner"))
        }
    }

    @Test
    fun campsiteSearch_checksNameLocationDescriptionTagsAndOwner() {
        with(AdminCampsiteScreens()) {
            assertTrue(banff.matchesSearch("hilltop"))     // name
            assertTrue(waterton.matchesSearch("lakes"))    // location
            assertTrue(waterton.matchesSearch("quiet"))    // description
            assertTrue(banff.matchesSearch("mountain"))    // tag
            assertTrue(banff.matchesSearch("owner2"))      // owner ID
            assertFalse(banff.matchesSearch("lakeside"))
        }
    }

    @Test
    fun messageSearch_checksContentSenderAndReceiver() {
        with(MessageListScreen()) {
            assertTrue(fireMessage.matchesSearch("fire ban")) // content
            assertTrue(fireMessage.matchesSearch("owner1"))   // sender
            assertTrue(fireMessage.matchesSearch("entrant1")) // receiver
            assertFalse(fireMessage.matchesSearch("welcome"))
        }
    }

    @Test
    fun notificationSearch_checksContentAndRecipient() {
        with(NotificationListScreen()) {
            assertTrue(wonNotification.matchesSearch("lottery"))  // content
            assertTrue(wonNotification.matchesSearch("entrant1")) // recipient
            assertFalse(wonNotification.matchesSearch("cancelled"))
        }
    }

    // ---------- Profiles screen ----------

    @Test
    fun searchProfiles_showsOnlyMatches() {
        composeRule.setContent {
            AdminProfilesScreens().AdminProfileListView(listOf(john, jane), onProfileClick = {}, onDelete = {})
        }
        composeRule.onNodeWithTag("adminSearch").performTextInput("keller")

        composeRule.onNodeWithText("Jane Keller").assertIsDisplayed()
        composeRule.onNodeWithText("John Ballin").assertDoesNotExist()
    }

    @Test
    fun searchProfiles_noMatchShowsMessage() {
        composeRule.setContent {
            AdminProfilesScreens().AdminProfileListView(listOf(john, jane), onProfileClick = {}, onDelete = {})
        }
        composeRule.onNodeWithTag("adminSearch").performTextInput("zzz")

        composeRule.onNodeWithTag("noSearchResults").assertIsDisplayed()
        composeRule.onNodeWithText("John Ballin").assertDoesNotExist()
        composeRule.onNodeWithText("Jane Keller").assertDoesNotExist()
    }

    @Test
    fun searchProfiles_clearBringsEveryoneBack() {
        composeRule.setContent {
            AdminProfilesScreens().AdminProfileListView(listOf(john, jane), onProfileClick = {}, onDelete = {})
        }
        composeRule.onNodeWithTag("adminSearch").performTextInput("keller")
        composeRule.onNodeWithTag("clearSearch").performClick()

        composeRule.onNodeWithText("John Ballin").assertIsDisplayed()
        composeRule.onNodeWithText("Jane Keller").assertIsDisplayed()
        composeRule.onNodeWithTag("clearSearch").assertDoesNotExist() // Clear hides when the box is empty
    }

    // ---------- Campsites screen ----------

    @Test
    fun searchCampsites_showsOnlyMatches() {
        composeRule.setContent {
            AdminCampsiteScreens().AdminCampsiteListView(listOf(waterton, banff), onCampsiteClick = {}, onDelete = {})
        }
        composeRule.onNodeWithTag("adminSearch").performTextInput("banff")

        composeRule.onNodeWithText("Banff Hilltop").assertIsDisplayed()
        composeRule.onNodeWithText("Waterton Campground").assertDoesNotExist()
    }

    @Test
    fun searchCampsites_noMatchShowsMessage() {
        composeRule.setContent {
            AdminCampsiteScreens().AdminCampsiteListView(listOf(waterton, banff), onCampsiteClick = {}, onDelete = {})
        }
        composeRule.onNodeWithTag("adminSearch").performTextInput("zzz")

        composeRule.onNodeWithTag("noSearchResults").assertIsDisplayed()
    }

    // ---------- Messages screen ----------

    @Test
    fun searchMessages_showsOnlyMatches() {
        composeRule.setContent {
            MessageListScreen().MessageList(rememberNavController(), listOf(fireMessage, welcomeMessage))
        }
        composeRule.onNodeWithTag("adminSearch").performTextInput("fire")

        composeRule.onNodeWithTag("checkMessage1").assertIsDisplayed()
        composeRule.onNodeWithTag("checkMessage2").assertDoesNotExist()
    }

    @Test
    fun searchMessages_noMatchShowsMessage() {
        composeRule.setContent {
            MessageListScreen().MessageList(rememberNavController(), listOf(fireMessage, welcomeMessage))
        }
        composeRule.onNodeWithTag("adminSearch").performTextInput("zzz")

        composeRule.onNodeWithTag("noSearchResults").assertIsDisplayed()
    }

    // ---------- Notifications screen ----------

    @Test
    fun searchNotifications_showsOnlyMatches() {
        composeRule.setContent {
            NotificationListScreen().NotificationList(
                rememberNavController(), listOf(wonNotification, cancelledNotification)
            )
        }
        composeRule.onNodeWithTag("adminSearch").performTextInput("lottery")

        composeRule.onNodeWithTag("checkNotification1").assertIsDisplayed()
        composeRule.onNodeWithTag("checkNotification2").assertDoesNotExist()
    }

    @Test
    fun searchNotifications_noMatchShowsMessage() {
        composeRule.setContent {
            NotificationListScreen().NotificationList(
                rememberNavController(), listOf(wonNotification, cancelledNotification)
            )
        }
        composeRule.onNodeWithTag("adminSearch").performTextInput("zzz")

        composeRule.onNodeWithTag("noSearchResults").assertIsDisplayed()
    }
}
