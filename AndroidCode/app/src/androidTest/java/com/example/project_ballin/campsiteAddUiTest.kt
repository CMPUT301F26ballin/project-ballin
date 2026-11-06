package com.example.project_ballin

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class campsiteAddUiTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun addCampsite_inputAllFields_ConfirmCampsiteIsSeen(){

        composeTestRule
            .onNodeWithTag("ownerSignin")
            .performClick()

        composeTestRule
            .onNodeWithTag("toCampsiteList")
            .assertExists()

        composeTestRule
            .onNodeWithTag("toCampsiteList")
            .performClick()

        composeTestRule
            .onNodeWithTag("newCampsiteButton")
            .assertExists()

        composeTestRule
            .onNodeWithTag("newCampsiteButton")
            .performClick()

        composeTestRule
            .onNodeWithTag("campName")
            .assertExists()

        composeTestRule
            .onNodeWithTag("campName")
            .performTextInput("Hello")

        composeTestRule
            .onNodeWithTag("campPrice")
            .performTextInput("65")

        composeTestRule
            .onNodeWithTag("campRules")
            .performTextInput("Have Fun!")

        composeTestRule
            .onNodeWithTag("campDesc")
            .performTextInput("The best campsite ever!")

        composeTestRule
            .onNodeWithTag("tagInput")
            .performTextInput("Tents")

        composeTestRule
            .onNodeWithTag("addTag")
            .performClick()

        composeTestRule
            .onNodeWithTag("removeTag")
            .assertExists()

        composeTestRule
            .onNodeWithTag("removeTag")
            .performClick()

        composeTestRule
            .onNodeWithTag("removeTag")
            .assertDoesNotExist()

        composeTestRule
            .onNodeWithTag("tagInput")
            .performTextInput("Tents")

        composeTestRule
            .onNodeWithTag("addTag")
            .performClick()

        composeTestRule
            .onNodeWithTag("campOccupancy")
            .performTextInput("15")

        composeTestRule
            .onNodeWithTag("campLat")
            .performTextInput("12")

        composeTestRule
            .onNodeWithTag("campLon")
            .performTextInput("12")

        composeTestRule
            .onNodeWithTag("campLocName")
            .performTextInput("Waterton")

        composeTestRule
            .onNodeWithTag("addSiteButton")
            .performClick()

        composeTestRule
            .onNodeWithText("Hello")
            .assertExists()



    }

    @Test
    fun seeCampsiteDetails_updateCampsite_AlterField_ConfirmCampsiteIsUpdated(){
        composeTestRule
            .onNodeWithTag("ownerSignin")
            .performClick()

        composeTestRule
            .onNodeWithTag("toCampsiteList")
            .assertExists()

        composeTestRule
            .onNodeWithTag("toCampsiteList")
            .performClick()

        composeTestRule
            .onNodeWithTag("newCampsiteButton")
            .performClick()


        composeTestRule
            .onNodeWithTag("campName")
            .performTextInput("Hello")

        composeTestRule
            .onNodeWithTag("campPrice")
            .performTextInput("65")

        composeTestRule
            .onNodeWithTag("campRules")
            .performTextInput("Have Fun!")

        composeTestRule
            .onNodeWithTag("campDesc")
            .performTextInput("The best campsite ever!")

        composeTestRule
            .onNodeWithTag("tagInput")
            .performTextInput("Tents")

        composeTestRule
            .onNodeWithTag("addTag")
            .performClick()

        composeTestRule
            .onNodeWithTag("campOccupancy")
            .performTextInput("15")

        composeTestRule
            .onNodeWithTag("campLat")
            .performTextInput("12")

        composeTestRule
            .onNodeWithTag("campLon")
            .performTextInput("12")

        composeTestRule
            .onNodeWithTag("campLocName")
            .performTextInput("Waterton")

        composeTestRule
            .onNodeWithTag("addSiteButton")
            .performClick()

        composeTestRule
            .onNodeWithText("Hello")
            .assertExists()

        composeTestRule
            .onNodeWithText("Hello")
            .performClick()

        composeTestRule
            .onNodeWithText("Campsite Details")
            .assertExists()

        //check some other values

        composeTestRule
            .onNodeWithTag("updateCampsite")
            .assertExists()

        composeTestRule
            .onNodeWithTag("updateCampsite")
            .performClick()

        composeTestRule
            .onNodeWithTag("campName")
            .assertExists()

        composeTestRule.onNodeWithTag("campName")
            .performTextInput("Hello2")

        composeTestRule
            .onNodeWithTag("updateSiteButton")
            .assertExists()

        composeTestRule
            .onNodeWithTag("updateSiteButton")
            .performClick()

        composeTestRule
            .onNodeWithText("Campsite Name: Hello2")
            .assertExists()
    }

    @Test
    fun viewCampsiteDetails_backToCampsites_AddNewCampsite_viewNewCampsiteDetails(){

    }


}