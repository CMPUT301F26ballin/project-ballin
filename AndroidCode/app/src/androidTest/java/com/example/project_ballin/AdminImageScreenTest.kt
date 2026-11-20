package com.example.project_ballin

import android.graphics.Bitmap
import android.graphics.Color
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.firebase.firestore.Blob
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.ByteArrayOutputStream

/**
 * Tests for the admin's browse and remove images screen (US 03.03.01, US 03.03.02).
 * Uses local lists, so no database needed.
 */
@RunWith(AndroidJUnit4::class)
class AdminImageScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val adminImageScreens = AdminImageScreens()

    /** Makes a small test image, saved the same way campsites save images. */
    private fun testImage(color: Int): Blob {
        val bitmap = Bitmap.createBitmap(10, 10, Bitmap.Config.ARGB_8888)
        bitmap.eraseColor(color)
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return Blob.fromBytes(stream.toByteArray())
    }

    private fun sampleSites() = listOf(
        Campsite(
            name = "Waterton Campground", ID = "site1",
            images = mutableListOf(testImage(Color.GREEN), testImage(Color.BLUE))
        ),
        Campsite(name = "Banff Hilltop", ID = "site2", images = mutableListOf(testImage(Color.RED))),
        Campsite(name = "No Pictures Site", ID = "site3")
    )

    // ---------- Collecting and removing ----------

    @Test
    fun allCampsiteImages_collectsEveryImage() {
        val images = adminImageScreens.allCampsiteImages(sampleSites())
        assertEquals(3, images.size)
        assertEquals(listOf("site1_0", "site1_1", "site2_0"), images.map { it.key })
    }

    @Test
    fun removeImage_takesOnlyThatImageOut_andSavesTheCampsite() {
        val site = sampleSites()[0]
        val second = site.images[1]
        var saved: Campsite? = null

        adminImageScreens.removeImage(AdminImage(site, 0, site.images[0])) { _, newSite -> saved = newSite }

        assertEquals(1, site.images.size)
        assertEquals(second, site.images[0])
        assertEquals(site, saved)
    }

    @Test
    fun removeImage_alreadyRemovedDoesNothing() {
        val site = sampleSites()[1]
        var saveCount = 0

        adminImageScreens.removeImage(AdminImage(site, 5, testImage(Color.RED))) { _, _ -> saveCount++ }

        assertEquals(1, site.images.size)
        assertEquals(0, saveCount)
    }

    // ---------- Screen ----------

    @Test
    fun browseImages_showsEveryImageWithItsCampsite() {
        composeRule.setContent {
            adminImageScreens.AdminImageListView(adminImageScreens.allCampsiteImages(sampleSites()), onRemove = {})
        }
        composeRule.onNodeWithTag("adminImage_site1_0").assertIsDisplayed()
        composeRule.onNodeWithTag("adminImage_site1_1").assertIsDisplayed()
        composeRule.onNodeWithTag("adminImage_site2_0").assertIsDisplayed()
        composeRule.onNodeWithText("Banff Hilltop").assertIsDisplayed()
    }

    @Test
    fun browseImages_noImagesShowsMessage() {
        composeRule.setContent {
            adminImageScreens.AdminImageListView(emptyList(), onRemove = {})
        }
        composeRule.onNodeWithTag("noImages").assertIsDisplayed()
    }

    @Test
    fun browseImages_brokenImageDoesNotCrash() {
        val site = Campsite(
            name = "Broken Image Site", ID = "site4",
            images = mutableListOf(Blob.fromBytes(byteArrayOf(1, 2, 3)))
        )
        composeRule.setContent {
            adminImageScreens.AdminImageListView(adminImageScreens.allCampsiteImages(listOf(site)), onRemove = {})
        }
        composeRule.onNodeWithText("Can't show image").assertIsDisplayed()
    }

    @Test
    fun removeImage_confirmRemovesIt() {
        val images = mutableStateListOf(*adminImageScreens.allCampsiteImages(sampleSites()).toTypedArray())
        composeRule.setContent {
            adminImageScreens.AdminImageListView(images, onRemove = { images.remove(it) })
        }
        composeRule.onNodeWithTag("removeImage_site2_0").performClick()
        composeRule.onNodeWithTag("confirmDeleteDialog").assertIsDisplayed()
        composeRule.onNodeWithTag("confirmDelete").performClick()

        composeRule.onNodeWithTag("adminImage_site2_0").assertDoesNotExist()
        composeRule.onNodeWithTag("adminImage_site1_0").assertIsDisplayed()
    }

    @Test
    fun removeImage_cancelKeepsIt() {
        val images = mutableStateListOf(*adminImageScreens.allCampsiteImages(sampleSites()).toTypedArray())
        composeRule.setContent {
            adminImageScreens.AdminImageListView(images, onRemove = { images.remove(it) })
        }
        composeRule.onNodeWithTag("removeImage_site2_0").performClick()
        composeRule.onNodeWithTag("cancelDelete").performClick()

        composeRule.onNodeWithTag("confirmDeleteDialog").assertDoesNotExist()
        composeRule.onNodeWithTag("adminImage_site2_0").assertIsDisplayed()
        assertEquals(3, images.size)
    }
}
