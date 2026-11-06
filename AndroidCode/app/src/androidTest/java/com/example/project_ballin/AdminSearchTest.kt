//package com.example.project_ballin
//
//import androidx.compose.ui.test.assertIsDisplayed
//import androidx.compose.ui.test.junit4.createComposeRule
//import androidx.compose.ui.test.onNodeWithTag
//import androidx.compose.ui.test.onNodeWithText
//import androidx.compose.ui.test.performClick
//import androidx.compose.ui.test.performTextInput
//import androidx.navigation.compose.rememberNavController
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import com.example.project_ballin.admin.adminui.NotificationListScreen
//import org.junit.Assert.assertFalse
//import org.junit.Assert.assertTrue
//import org.junit.Rule
//import org.junit.Test
//import org.junit.runner.RunWith
//import java.util.Date
//
///**
// * Tests for searching the admin's lists (US 03.02.02, US 03.04.03, US 03.06.02, US 03.07.02).
// * Uses local lists, so no database needed.
// */
//@RunWith(AndroidJUnit4::class)
//class AdminSearchTest {
//
//    @get:Rule
//    val composeRule = createComposeRule()
//
//    private val john = Profile(
//        firstName = "John", lastName = "Ballin", email = "john@example.com",
//        role = "Entrant", userID = "user1"
//    )
//    private val jane = Profile(
//        firstName = "Jane", lastName = "Keller", email = "jane@example.com",
//        role = "Owner", userID = "user2"
//    )
//
//    private val waterton = Campsite(
//        name = "Waterton Campground", ID = "site1", locationName = "Waterton Lakes",
//        tags = mutableListOf("Lakeside"), ownerID = "owner1"
//    )
//    private val banff = Campsite(
//        name = "Banff Hilltop", ID = "site2", locationName = "Banff",
//        tags = mutableListOf("Mountain"), ownerID = "owner2"
//    )
//
//    // ---------- Matching rules ----------
//
//    @Test
//    fun matchesQuery_ignoresCaseAndSpaces() {
//        assertTrue(matchesQuery("  WATER ", "Waterton Lakes"))
//        assertFalse(matchesQuery("banff", "Waterton Lakes"))
//    }
//
//    @Test
//    fun matchesQuery_emptySearchMatchesEverything() {
//        assertTrue(matchesQuery("", "anything"))
//        assertTrue(matchesQuery("   ", "anything"))
//    }
//
//    @Test
//    fun profileSearch_checksNameEmailAndRole() {
//        assertTrue(john.matchesSearch("john ballin"))
//        assertTrue(jane.matchesSearch("jane@"))
//        assertTrue(jane.matchesSearch("owner"))
//        assertFalse(john.matchesSearch("owner"))
//    }
//
//    @Test
//    fun campsiteSearch_checksNameLocationAndTags() {
//        assertTrue(waterton.matchesSearch("lakes"))
//        assertTrue(banff.matchesSearch("mountain"))
//        assertFalse(banff.matchesSearch("lakeside"))
//    }
//
//    // ---------- Profiles screen ----------
//
//    @Test
//    fun searchProfiles_showsOnlyMatches() {
//        composeRule.setContent {
//            AdminProfileListView(listOf(john, jane), onProfileClick = {}, onDelete = {})
//        }
//        composeRule.onNodeWithTag("adminSearch").performTextInput("keller")
//
//        composeRule.onNodeWithText("Jane Keller").assertIsDisplayed()
//        composeRule.onNodeWithText("John Ballin").assertDoesNotExist()
//    }
//
//    @Test
//    fun searchProfiles_noMatchShowsMessage_andClearBringsEveryoneBack() {
//        composeRule.setContent {
//            AdminProfileListView(listOf(john, jane), onProfileClick = {}, onDelete = {})
//        }
//        composeRule.onNodeWithTag("adminSearch").performTextInput("zzz")
//        composeRule.onNodeWithTag("noSearchResults").assertIsDisplayed()
//
//        composeRule.onNodeWithTag("clearSearch").performClick()
//        composeRule.onNodeWithText("John Ballin").assertIsDisplayed()
//        composeRule.onNodeWithText("Jane Keller").assertIsDisplayed()
//    }
//
//    // ---------- Campsites screen ----------
//
//    @Test
//    fun searchCampsites_showsOnlyMatches() {
//        composeRule.setContent {
//            AdminCampsiteListView(listOf(waterton, banff), onCampsiteClick = {}, onDelete = {})
//        }
//        composeRule.onNodeWithTag("adminSearch").performTextInput("banff")
//
//        composeRule.onNodeWithText("Banff Hilltop").assertIsDisplayed()
//        composeRule.onNodeWithText("Waterton Campground").assertDoesNotExist()
//    }
//
//    // ---------- Messages screen ----------
//
//    @Test
//    fun searchMessages_showsOnlyMatches() {
//        val messages = listOf(
//            Message("owner1", "entrant1", "Fire ban this weekend", Date(), "m1"),
//            Message("owner2", "entrant2", "Welcome to the campsite", Date(), "m2")
//        )
//        composeRule.setContent {
//            MessageListScreen(rememberNavController(), messages)
//        }
//        composeRule.onNodeWithTag("adminSearch").performTextInput("fire")
//
//        composeRule.onNodeWithTag("checkMessage1").assertIsDisplayed()
//        composeRule.onNodeWithTag("checkMessage2").assertDoesNotExist()
//    }
//
//    @Test
//    fun searchMessages_noMatchShowsMessage() {
//        val messages = listOf(Message("owner1", "entrant1", "Hello", Date(), "m1"))
//        composeRule.setContent {
//            MessageListScreen(rememberNavController(), messages)
//        }
//        composeRule.onNodeWithTag("adminSearch").performTextInput("zzz")
//        composeRule.onNodeWithTag("noSearchResults").assertIsDisplayed()
//    }
//
//    // ---------- Notifications screen ----------
//
//    @Test
//    fun searchNotifications_showsOnlyMatches() {
//        val notifications = listOf(
//            Notification("You won the lottery!", Date(), "n1", "entrant1"),
//            Notification("Your reservation was cancelled", Date(), "n2", "entrant2")
//        )
//        composeRule.setContent {
//            NotificationListScreen(rememberNavController(), notifications)
//        }
//        composeRule.onNodeWithTag("adminSearch").performTextInput("lottery")
//
//        composeRule.onNodeWithTag("checkNotification1").assertIsDisplayed()
//        composeRule.onNodeWithTag("checkNotification2").assertDoesNotExist()
//    }
//}
