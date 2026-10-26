package com.example.project_ballin

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

/**
 * The screen where the owner can input the details of a campsite.
 * This screen is used when a new campsite is being entered or when an existing
 * campsite is being updated.
 *
 * @param navController the navcontroller of the app
 * @param siteID a nullable integer that is not null if the campsite is being updated.
 * @param getSite the function to return the site details depending on the site ID. Only gets called
 * if site is not null.
 *
 *
 * */

@Composable
fun CampsiteInputScreen (navController: NavController, siteID: Int?, getSite: (Int) -> Campsite ){
    TopAppBar(navController, "Input Campsite Details"){
        CampsiteInputView(navController, siteID, getSite = {getSite(it)})
    }
}

@Composable
fun CampsiteInputView (navController: NavController, siteID: Int?, getSite: (Int) -> Campsite){

    //the old campsite (if we are updating) null if this is a new site.
    var oldSite: Campsite? = null
    if (siteID != null){
        oldSite = getSite(siteID)
    }

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {

    }



}