package com.example.project_ballin

import org.junit.After
import org.junit.Before
import org.junit.Test

class campsiteAddTest() {

    private lateinit var campsiteList: CampsiteList

    @Before
    fun setUpList(){
        campsiteList = CampsiteList()
    }

    @Test
    fun addSite_checkIfSiteExists_GetSite(){
        //campsiteList.addCampsite(Campsite("HelloSite"))
    }

    @Test
    fun addSite_checkIfSiteExists_RemoveSite_ConfirmRemoval(){

    }

    @Test
    fun addSite_checkIfSiteExists_updateSite_ConfirmUpdates(){

    }


    @After
    fun removeAllAddedSites(){

    }



}