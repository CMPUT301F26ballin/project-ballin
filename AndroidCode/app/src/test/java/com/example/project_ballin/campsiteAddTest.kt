package com.example.project_ballin

import android.net.Uri
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.uuid.Uuid

class campsiteAddTest() {

    private lateinit var campsiteList: CampsiteList
    private  lateinit var testSite: Campsite

    private lateinit var ownerUid: Uuid
    private lateinit var campUid: Uuid

    @Before
    fun setUpList(){
        campsiteList = CampsiteList()
        ownerUid = Uuid.random()
        campUid = Uuid.random()
        testSite = Campsite(name = "HelloSite",
            ID = campUid,
            locationName = "Waterton",
            tags = mutableListOf("tenting"),
            price = 65f,
            rating = null,
            rules = "have fun",
            description = "the best site ever",
            occupancy = 12,
            ownerID = ownerUid)
    }

    @Test
    fun addSite_checkIfSiteExists_GetSite(){


        campsiteList.addCampsite(testSite)



        val findSite = campsiteList.getCampsite(campUid)

        assertEquals(findSite, testSite)



    }

    @Test
    fun addSite_checkIfSiteExists_RemoveSite_ConfirmRemoval(){

        campsiteList.addCampsite(testSite)

        assert(testSite in campsiteList.campsites)

        campsiteList.deleteCampsite(testSite)

        assert(testSite !in campsiteList.campsites)


    }

    @Test
    fun addSite_checkIfSiteExists_updateSite_ConfirmUpdates(){

        val newsite = Campsite("HelloSite",
            campUid,
            locationName = "waterton",
            tags = mutableListOf("tenting"),
            price =  65f,
            rating = null,
            rules =  "Have Fun!",
            description =  "best",
            occupancy =  20,
            ownerID = ownerUid)

        campsiteList.addCampsite(testSite)
        assert(testSite in campsiteList.campsites)

        campsiteList.updateCampsite(testSite, newsite)
        val site = campsiteList.getCampsite(campUid)
        assertEquals(site, newsite)


    }


    @After
    fun removeAllAddedSites(){
        campsiteList.deleteCampsite(testSite)

    }



}