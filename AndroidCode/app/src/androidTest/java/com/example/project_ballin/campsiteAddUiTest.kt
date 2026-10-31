package com.example.project_ballin

import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
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



    }

    @Test
    fun seeCampsiteDetails_updateCampsite_AlterField_ConfirmCampsiteIsUpdated(){
    }

    @Test
    fun viewCampsiteDetails_backToCampsites_AddNewCampsite_viewNewCampsiteDetails(){

    }


}