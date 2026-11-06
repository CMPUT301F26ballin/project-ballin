package com.example.project_ballin


import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.project_ballin.entrant.Entrant
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class EntrantSearchTest {
    private lateinit var campsiteList: CampsiteList
    private lateinit var entrant: Entrant
    private lateinit var campsite: Campsite


    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun createEntrant(){
        entrant = Entrant()
    }

    @Before
    fun createCampsite(){
        campsite = Campsite(
            "Waterton Campground 1",
            "1",
            1.toDouble(),
            1.toDouble(),
            "OnePlace",
            mutableListOf("One", "1"),
            1.toFloat(),
            1.toFloat(),
            "only 1",
            "one and only",
            1,
        )
    }

    @Before
    fun createCampsiteList(){
        campsiteList = CampsiteList()
        campsiteList.addCampsite(campsite)
    }



    @Test
    fun NavigateToSearchScreen_EnterText_ResultsShow(){
        composeTestRule
            .onNodeWithTag("entrantSignin")
            .performClick()

        composeTestRule
            .onNodeWithText("BROWSE CAMPSITES")
            .assertExists()

        composeTestRule
            .onNodeWithText("BROWSE CAMPSITES")
            .performClick()

        composeTestRule
            .onNodeWithText("Search Campsites")
            .assertExists()

        composeTestRule
            .onNodeWithTag("Search Bar")
            .performTextInput("Waterton")


        composeTestRule
            .onNodeWithTag("Campsite: Waterton Campground 1")
            .assertExists()
    }

    @After
    fun cleanUp() {
        campsiteList.deleteCampsite(campsite)
    }
}