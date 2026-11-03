package com.example.project_ballin

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.project_ballin.owner.Owner
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Tests for the admin profile screens. Uses a local list, so no database needed.
 */
@RunWith(AndroidJUnit4::class)
class AdminProfileScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val john = Profile("device1", "John Ballin", "john@example.com", "780-555-0101", "", "Entrant")
    private val jane = Profile("device2", "Jane Keller", "jane@example.com", "", "", "Owner")

    @Test
    fun browseProfiles_showsEveryProfile() {
        composeRule.setContent {
            AdminProfileListView(listOf(john, jane), onProfileClick = {}, onDelete = {})
        }
        composeRule.onNodeWithText("John Ballin").assertIsDisplayed()
        composeRule.onNodeWithText("Jane Keller").assertIsDisplayed()
        composeRule.onNodeWithText("Owner").assertIsDisplayed()
    }

    @Test
    fun browseProfiles_emptyListShowsMessage() {
        composeRule.setContent {
            AdminProfileListView(emptyList(), onProfileClick = {}, onDelete = {})
        }
        composeRule.onNodeWithTag("noProfiles").assertIsDisplayed()
    }

    @Test
    fun tappingProfile_opensThatProfile() {
        var clicked: Profile? = null
        composeRule.setContent {
            AdminProfileListView(listOf(john, jane), onProfileClick = { clicked = it }, onDelete = {})
        }
        composeRule.onNodeWithTag("profileItem_device2").performClick()
        assertEquals(jane, clicked)
    }

    @Test
    fun removeProfile_confirmRemovesIt() {
        val profiles = mutableStateListOf(john, jane)
        composeRule.setContent {
            AdminProfileListView(profiles, onProfileClick = {}, onDelete = { profiles.remove(it) })
        }
        composeRule.onNodeWithTag("removeProfile_device1").performClick()
        composeRule.onNodeWithTag("confirmDeleteDialog").assertIsDisplayed()
        composeRule.onNodeWithTag("confirmDelete").performClick()

        composeRule.onNodeWithText("John Ballin").assertDoesNotExist()
        composeRule.onNodeWithText("Jane Keller").assertIsDisplayed()
    }

    @Test
    fun removeProfile_cancelKeepsIt() {
        val profiles = mutableStateListOf(john, jane)
        composeRule.setContent {
            AdminProfileListView(profiles, onProfileClick = {}, onDelete = { profiles.remove(it) })
        }
        composeRule.onNodeWithTag("removeProfile_device1").performClick()
        composeRule.onNodeWithTag("cancelDelete").performClick()

        composeRule.onNodeWithTag("confirmDeleteDialog").assertDoesNotExist()
        composeRule.onNodeWithText("John Ballin").assertIsDisplayed()
        assertEquals(2, profiles.size)
    }

    @Test
    fun profileDetails_showsAllInformation() {
        composeRule.setContent {
            AdminProfileDetailView(john, onDelete = {})
        }
        composeRule.onNodeWithText("John Ballin").assertIsDisplayed()
        composeRule.onNodeWithText("john@example.com").assertIsDisplayed()
        composeRule.onNodeWithText("780-555-0101").assertIsDisplayed()
        composeRule.onNodeWithText("device1").assertIsDisplayed()
    }

    @Test
    fun profileDetails_missingPhoneShowsNotProvided() {
        composeRule.setContent {
            AdminProfileDetailView(jane, onDelete = {})
        }
        composeRule.onNodeWithText("Not provided").assertIsDisplayed()
    }

    @Test
    fun profileDetails_removeAfterConfirm() {
        var deleted: Profile? = null
        composeRule.setContent {
            AdminProfileDetailView(john, onDelete = { deleted = it })
        }
        composeRule.onNodeWithTag("removeProfileDetail").performClick()
        composeRule.onNodeWithTag("confirmDelete").performClick()
        assertEquals(john, deleted)
    }

    @Test
    fun profileFromUser_keepsRole() {
        val owner = Owner("Jane Keller", "", "jane@example.com", "device2", "", "")
        val profile = Profile.fromUser(owner)
        assertEquals("Owner", profile.role)
        assertTrue(profile.deviceId == "device2")
    }
}
