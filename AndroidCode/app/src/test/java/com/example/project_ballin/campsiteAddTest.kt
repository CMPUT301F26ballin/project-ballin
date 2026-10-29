package com.example.project_ballin

import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.uuid.Uuid

class campsiteAddTest() {

    private lateinit var campsiteList: CampsiteList

    @Before
    fun setUpList(){
        campsiteList = CampsiteList()
    }

    @Test
    fun addSite_checkIfSiteExists_GetSite(){
        val campUid = Uuid.random()
        val ownerUid = Uuid.random()
        campsiteList.addCampsite(Campsite("HelloSite",
            campUid,
            mutableListOf("tenting"),
            65f,
            null,
            "Have Fun!",
            "best",
            12,
            ownerUid))

        campsiteList.campsites

        val findSite = campsiteList.getCampsite(campUid)





    }

    @Test
    fun addSite_checkIfSiteExists_RemoveSite_ConfirmRemoval(){
        val campUid = Uuid.random()
        val ownerUid = Uuid.random()

        campsiteList.addCampsite(Campsite("HelloSite",
            campUid,
            mutableListOf("tenting"),
            65f,
            null,
            "Have Fun!",
            "best",
            12,
            ownerUid))

        campsiteList.deleteCampsite(Campsite("HelloSite",
            campUid,
            mutableListOf("tenting"),
            65f,
            null,
            "Have Fun!",
            "best",
            12,
            ownerUid))


    }

    @Test
    fun addSite_checkIfSiteExists_updateSite_ConfirmUpdates(){
        val campUid = Uuid.random()
        val ownerUid = Uuid.random()

        val oldsite = Campsite("HelloSite",
            campUid,
            mutableListOf("tenting"),
            65f,
            null,
            "Have Fun!",
            "best",
            12,
            ownerUid)

        val newsite = Campsite("HelloSite",
            campUid,
            mutableListOf("tenting"),
            65f,
            null,
            "Have Fun!",
            "best",
            20,
            ownerUid)

        campsiteList.addCampsite(oldsite)


    }


    @After
    fun removeAllAddedSites(){
        //campsiteList.deleteCampsite()

    }



}