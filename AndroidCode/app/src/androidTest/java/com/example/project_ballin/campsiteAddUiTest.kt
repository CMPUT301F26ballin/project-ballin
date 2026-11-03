package com.example.project_ballin

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class campsiteAddUiTest {

    private lateinit var campsiteList: CampsiteList

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun createCampsiteList(){
        campsiteList = CampsiteList()
    }

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
            .onNodeWithTag("addSiteButton")
            .performClick()

        composeTestRule
            .onNodeWithTag("Campsite:Hello")
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

        //add campsite

        composeTestRule
            .onNodeWithTag("Campsite:Hello")
            .assertExists()

        composeTestRule
            .onNodeWithTag("Campsite:Hello")
            .performClick()

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
            .performClick()
    }

    @Test
    fun viewCampsiteDetails_backToCampsites_AddNewCampsite_viewNewCampsiteDetails(){

    }


}