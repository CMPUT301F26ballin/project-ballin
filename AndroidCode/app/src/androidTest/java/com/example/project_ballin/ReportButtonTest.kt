package com.example.project_ballin

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollTo
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.project_ballin.reusableui.GeneralReportButton
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Tests that the report buttons show up in the right places.
 * Uses a local campsite, so no database needed.
 */
@RunWith(AndroidJUnit4::class)
class ReportButtonTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val site = Campsite(
        name = "Waterton Campground",
        ID = "site1",
        locationName = "Waterton Lakes",
        description = "A quiet lakeside site",
        ownerID = "owner1"
    )

    @Test
    fun campsitePage_someoneElseCanReportCampsiteAndOwner() {
        composeRule.setContent {
            CampsiteDetailScreen().CampsiteDetailScreen(rememberNavController(), site, userID = "entrant1")
        }
        composeRule.onNodeWithTag("reportCampsiteButton").performScrollTo().assertIsDisplayed()
        composeRule.onNodeWithText("Report Campsite").assertIsDisplayed()
        composeRule.onNodeWithTag("reportOwnerButton").performScrollTo().assertIsDisplayed()
        composeRule.onNodeWithText("Report Owner").assertIsDisplayed()
    }

    @Test
    fun campsitePage_ownerDoesNotSeeReportButtons() {
        composeRule.setContent {
            CampsiteDetailScreen().CampsiteDetailScreen(rememberNavController(), site, userID = "owner1")
        }
        composeRule.onNodeWithTag("reportCampsiteButton").assertDoesNotExist()
        composeRule.onNodeWithTag("reportOwnerButton").assertDoesNotExist()
    }

    @Test
    fun reportButton_defaultLabelIsReport() {
        composeRule.setContent {
            GeneralReportButton().ReportButton(
                subjectReport = "Owner",
                clickedID = "owner1",
                navController = rememberNavController()
            )
        }
        composeRule.onNodeWithTag("reportButton").assertIsDisplayed()
        composeRule.onNodeWithText("Report").assertIsDisplayed()
    }
}
