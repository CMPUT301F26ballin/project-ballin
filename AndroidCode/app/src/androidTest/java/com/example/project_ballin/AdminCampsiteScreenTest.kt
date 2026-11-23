package com.example.project_ballin

import android.graphics.Bitmap
import android.graphics.Color
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.firebase.firestore.Blob
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.ByteArrayOutputStream

/**
 * Tests for the admin campsite screens (US 03.04.01, US 03.04.02, US 03.04.04, US 03.05.01).
 * Uses local lists, so no database needed.
 */
@RunWith(AndroidJUnit4::class)
class AdminCampsiteScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val adminCampsiteScreens = AdminCampsiteScreens()

    private val site1 = Campsite(
        name = "Waterton Campground 1",
        ID = "site1",
        locationName = "Waterton Lakes",
        tags = mutableListOf("Lakeside", "Tents"),
        price = 25f,
        rating = 4.5f,
        rules = "No fires after 11pm",
        description = "A quiet lakeside site",
        occupancy = 6,
        ownerID = "owner1"
    )
    private val site2 = Campsite(
        name = "Banff Hilltop",
        ID = "site2",
        locationName = "Banff",
        price = 40f,
        description = "Great views",
        occupancy = 4,
        ownerID = "owner2"
    )

    /** Makes a small green test image, saved the same way campsites save images. */
    private fun testImage(): Blob {
        val bitmap = Bitmap.createBitmap(10, 10, Bitmap.Config.ARGB_8888)
        bitmap.eraseColor(Color.GREEN)
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return Blob.fromBytes(stream.toByteArray())
    }

    // ---------- Campsite list ----------

    @Test
    fun browseCampsites_showsEveryCampsite() {
        composeRule.setContent {
            adminCampsiteScreens.AdminCampsiteListView(listOf(site1, site2), onCampsiteClick = {}, onDelete = {})
        }
        composeRule.onNodeWithText("Waterton Campground 1").assertIsDisplayed()
        composeRule.onNodeWithText("Banff Hilltop").assertIsDisplayed()
        composeRule.onNodeWithText("$40.00 / night").assertIsDisplayed()
    }

    @Test
    fun browseCampsites_emptyListShowsMessage() {
        composeRule.setContent {
            adminCampsiteScreens.AdminCampsiteListView(emptyList(), onCampsiteClick = {}, onDelete = {})
        }
        composeRule.onNodeWithTag("noCampsites").assertIsDisplayed()
    }

    @Test
    fun tappingCampsite_opensThatCampsite() {
        var clicked: Campsite? = null
        composeRule.setContent {
            adminCampsiteScreens.AdminCampsiteListView(
                listOf(site1, site2), onCampsiteClick = { clicked = it }, onDelete = {}
            )
        }
        composeRule.onNodeWithTag("adminCampsiteItem_${site2.ID}").performClick()
        assertEquals(site2, clicked)
    }

    @Test
    fun removeCampsite_confirmRemovesIt() {
        val sites = mutableStateListOf(site1, site2)
        composeRule.setContent {
            adminCampsiteScreens.AdminCampsiteListView(sites, onCampsiteClick = {}, onDelete = { sites.remove(it) })
        }
        composeRule.onNodeWithTag("removeCampsite_${site1.ID}").performClick()
        composeRule.onNodeWithTag("confirmDeleteDialog").assertIsDisplayed()
        composeRule.onNodeWithTag("confirmDelete").performClick()

        composeRule.onNodeWithText("Waterton Campground 1").assertDoesNotExist()
        composeRule.onNodeWithText("Banff Hilltop").assertIsDisplayed()
    }

    @Test
    fun removeCampsite_cancelKeepsIt() {
        val sites = mutableStateListOf(site1, site2)
        composeRule.setContent {
            adminCampsiteScreens.AdminCampsiteListView(sites, onCampsiteClick = {}, onDelete = { sites.remove(it) })
        }
        composeRule.onNodeWithTag("removeCampsite_${site1.ID}").performClick()
        composeRule.onNodeWithTag("cancelDelete").performClick()

        composeRule.onNodeWithTag("confirmDeleteDialog").assertDoesNotExist()
        composeRule.onNodeWithText("Waterton Campground 1").assertIsDisplayed()
        assertEquals(2, sites.size)
    }

    // ---------- Campsite details ----------

    @Test
    fun campsiteDetails_showsAllInformation() {
        composeRule.setContent {
            adminCampsiteScreens.AdminCampsiteDetailView(site1, onDelete = {})
        }
        // The details page scrolls, so scroll to each line before checking it
        composeRule.onNodeWithText("Waterton Campground 1").performScrollTo().assertIsDisplayed()
        composeRule.onNodeWithText("Waterton Lakes").performScrollTo().assertIsDisplayed()
        composeRule.onNodeWithText("A quiet lakeside site").performScrollTo().assertIsDisplayed()
        composeRule.onNodeWithText("$25.00").performScrollTo().assertIsDisplayed()
        composeRule.onNodeWithText("4.5").performScrollTo().assertIsDisplayed()
        composeRule.onNodeWithText("Lakeside, Tents").performScrollTo().assertIsDisplayed()
        composeRule.onNodeWithText("No fires after 11pm").performScrollTo().assertIsDisplayed()
        composeRule.onNodeWithText("owner1").performScrollTo().assertIsDisplayed()
    }

    @Test
    fun campsiteDetails_missingValuesShowMessages() {
        composeRule.setContent {
            adminCampsiteScreens.AdminCampsiteDetailView(site2, onDelete = {})
        }
        composeRule.onNodeWithText("No ratings yet").performScrollTo().assertIsDisplayed()
        // Banff has no tags and no rules, so both show "None"
        composeRule.onAllNodesWithText("None").assertCountEquals(2)
    }

    @Test
    fun campsiteDetails_deletedCampsiteShowsMessage() {
        composeRule.setContent {
            adminCampsiteScreens.AdminCampsiteDetailView(null, onDelete = {})
        }
        composeRule.onNodeWithText("This campsite no longer exists.").assertIsDisplayed()
    }

    @Test
    fun campsiteDetails_removeAfterConfirm() {
        var deleted: Campsite? = null
        composeRule.setContent {
            adminCampsiteScreens.AdminCampsiteDetailView(site1, onDelete = { deleted = it })
        }
        composeRule.onNodeWithTag("removeCampsiteDetail").performScrollTo().performClick()
        composeRule.onNodeWithTag("confirmDelete").performClick()
        assertEquals(site1, deleted)
    }

    // ---------- Image gallery ----------

    @Test
    fun campsiteDetails_noImagesShowsMessage() {
        composeRule.setContent {
            adminCampsiteScreens.AdminCampsiteDetailView(site1, onDelete = {})
        }
        composeRule.onNodeWithTag("noCampsiteImages").performScrollTo().assertIsDisplayed()
    }

    @Test
    fun campsiteDetails_showsImages() {
        val siteWithImages = Campsite(
            name = "Pictured Site",
            ID = "site3",
            images = mutableListOf(testImage(), testImage()),
            ownerID = "owner1"
        )
        composeRule.setContent {
            adminCampsiteScreens.AdminCampsiteDetailView(siteWithImages, onDelete = {})
        }
        composeRule.onNodeWithTag("campsiteImageGallery").performScrollTo().assertIsDisplayed()
        composeRule.onNodeWithTag("campsiteImage_0").assertIsDisplayed()
        composeRule.onNodeWithTag("campsiteImage_1").assertIsDisplayed()
    }

    @Test
    fun campsiteDetails_brokenImageDoesNotCrash() {
        val siteWithBadImage = Campsite(
            name = "Broken Image Site",
            ID = "site4",
            images = mutableListOf(Blob.fromBytes(byteArrayOf(1, 2, 3))),
            ownerID = "owner1"
        )
        composeRule.setContent {
            adminCampsiteScreens.AdminCampsiteDetailView(siteWithBadImage, onDelete = {})
        }
        composeRule.onNodeWithText("Can't show image").performScrollTo().assertIsDisplayed()
    }

    // ---------- Price formatting ----------

    @Test
    fun formatPrice_showsTwoDecimals() {
        assertEquals("$25.00", adminCampsiteScreens.formatPrice(25f))
        assertEquals("$7.50", adminCampsiteScreens.formatPrice(7.5f))
        assertEquals("$0.00", adminCampsiteScreens.formatPrice(0f))
    }
}
