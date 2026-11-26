package com.example.project_ballin

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.firebase.firestore.Blob
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Tests for browsing campsite QR codes as an admin (US 03.04.04).
 * Uses local campsites with QR codes made by QRCodeGenerator, so no database needed.
 */
@RunWith(AndroidJUnit4::class)
class AdminQRCodeScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val adminQRCodeScreens = AdminQRCodeScreens()
    private val qrGenerator = QRCodeGenerator()

    private val waterton = Campsite(
        name = "Waterton Campground", ID = "site1", locationName = "Waterton Lakes",
        ownerID = "owner1", qrCode = qrGenerator.encodeQRBlobFromString("site1")
    )
    private val banff = Campsite(
        name = "Banff Hilltop", ID = "site2", locationName = "Banff",
        ownerID = "owner2", qrCode = qrGenerator.encodeQRBlobFromString("site2")
    )

    // A campsite made before QR codes were added
    private val noQRCodeSite = Campsite(
        name = "Old Campsite", ID = "site3", locationName = "Jasper", ownerID = "owner1"
    )

    // A campsite whose QR code bytes aren't a real image
    private val brokenQRCodeSite = Campsite(
        name = "Broken QR Site", ID = "site4", ownerID = "owner2",
        qrCode = Blob.fromBytes(byteArrayOf(1, 2, 3))
    )

    // ---------- Browsing QR codes ----------

    @Test
    fun browseQRCodes_showsEveryCampsitesQRCode() {
        composeRule.setContent {
            adminQRCodeScreens.AdminQRCodeListView(listOf(waterton, banff), onCampsiteClick = {})
        }
        // Each card is clickable, which merges its children, so look for the QR images in the unmerged tree
        composeRule.onNodeWithTag("adminQRCode_${waterton.ID}", useUnmergedTree = true).assertIsDisplayed()
        composeRule.onNodeWithTag("adminQRCode_${banff.ID}", useUnmergedTree = true).assertIsDisplayed()
        composeRule.onNodeWithText("Waterton Campground").assertIsDisplayed()
        composeRule.onNodeWithText("Banff Hilltop").assertIsDisplayed()
        // Both QR codes are real images, so no error message shows
        composeRule.onNodeWithText("Can't show QR code").assertDoesNotExist()
    }

    @Test
    fun browseQRCodes_emptyListShowsMessage() {
        composeRule.setContent {
            adminQRCodeScreens.AdminQRCodeListView(emptyList(), onCampsiteClick = {})
        }
        composeRule.onNodeWithTag("noQRCodes").assertIsDisplayed()
    }

    @Test
    fun browseQRCodes_campsiteWithoutQRCodeShowsMessage() {
        composeRule.setContent {
            adminQRCodeScreens.AdminQRCodeListView(listOf(noQRCodeSite), onCampsiteClick = {})
        }
        composeRule.onNodeWithText("No QR code").assertIsDisplayed()
        composeRule.onNodeWithText("Old Campsite").assertIsDisplayed()
    }

    @Test
    fun browseQRCodes_brokenQRCodeDoesNotCrash() {
        composeRule.setContent {
            adminQRCodeScreens.AdminQRCodeListView(listOf(brokenQRCodeSite), onCampsiteClick = {})
        }
        composeRule.onNodeWithText("Can't show QR code").assertIsDisplayed()
    }

    @Test
    fun tappingQRCode_opensThatCampsite() {
        var clicked: Campsite? = null
        composeRule.setContent {
            adminQRCodeScreens.AdminQRCodeListView(listOf(waterton, banff), onCampsiteClick = { clicked = it })
        }
        composeRule.onNodeWithTag("adminQRCodeItem_${banff.ID}").performClick()
        assertEquals(banff, clicked)
    }

    // ---------- Searching QR codes ----------

    @Test
    fun qrCodeSearch_checksNameLocationIDAndOwner() {
        with(adminQRCodeScreens) {
            assertTrue(waterton.matchesSearch("waterton campground")) // name
            assertTrue(waterton.matchesSearch("lakes"))               // location
            assertTrue(banff.matchesSearch("site2"))                  // campsite ID
            assertTrue(banff.matchesSearch("owner2"))                 // owner ID
            assertFalse(waterton.matchesSearch("banff"))
        }
    }

    @Test
    fun searchQRCodes_showsOnlyMatches() {
        composeRule.setContent {
            adminQRCodeScreens.AdminQRCodeListView(listOf(waterton, banff), onCampsiteClick = {})
        }
        composeRule.onNodeWithTag("adminSearch").performTextInput("banff")

        composeRule.onNodeWithTag("adminQRCode_${banff.ID}", useUnmergedTree = true).assertIsDisplayed()
        composeRule.onNodeWithTag("adminQRCode_${waterton.ID}", useUnmergedTree = true).assertDoesNotExist()
    }

    @Test
    fun searchQRCodes_noMatchShowsMessage() {
        composeRule.setContent {
            adminQRCodeScreens.AdminQRCodeListView(listOf(waterton, banff), onCampsiteClick = {})
        }
        composeRule.onNodeWithTag("adminSearch").performTextInput("zzz")

        composeRule.onNodeWithTag("noSearchResults").assertIsDisplayed()
    }

    // ---------- QR code on the campsite details page ----------

    @Test
    fun campsiteDetails_showsQRCode() {
        composeRule.setContent {
            AdminCampsiteScreens().AdminCampsiteDetailView(waterton, onDelete = {})
        }
        composeRule.onNodeWithTag("campsiteQRCode").performScrollTo().assertIsDisplayed()
        composeRule.onNodeWithText("No QR code").assertDoesNotExist()
    }

    @Test
    fun campsiteDetails_noQRCodeShowsMessage() {
        composeRule.setContent {
            AdminCampsiteScreens().AdminCampsiteDetailView(noQRCodeSite, onDelete = {})
        }
        composeRule.onNodeWithText("No QR code").performScrollTo().assertIsDisplayed()
    }
}
