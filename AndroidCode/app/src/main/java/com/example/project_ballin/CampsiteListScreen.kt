package com.example.project_ballin

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

/**
 * The screen that shows a list of all the cities of the owner accessing
 *
 *
 * @property navController the navController of the app
 * @property ownerID the ID of the owner user accessing the list.
 *
 * */


@Composable
fun CampsiteListScreen(navController: NavController, ownerID: Int){

    val campsiteRef = Firebase.firestore.collection("Campsites")

    val campsitesQuery = campsiteRef.whereEqualTo("ownerID", ownerID)

    var campsitesByOwner = campsitesQuery.get()

    campsitesQuery.addSnapshotListener { snapshots, error ->
        if(error != null){
            return@addSnapshotListener
        }
        campsitesByOwner = campsitesQuery.get()

    }
    //Paste the skeleton pieces here once they are made.

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        itemsIndexed(campsitesByOwner){ index, campsite ->

            val ID = campsite.ID
            CampsiteItem(campsite, OnClick = {navController.navigate("CampsiteDetailScreen/$ID")})

        }
    }

}

/**
 * A single row of the campsite list, it details the basic identifying information
 * (name, location, and images) of the campsite. The item is also clickable
 *
 * @property site the Campsite of the entry,
 * @property OnClick the function defining what to do when the row is clicked
 * It will navigate to the CampsiteDetailScreen
 * */


@Composable
fun CampsiteItem(site: Campsite, OnClick: () -> Unit)
{
    Row( modifier = Modifier.fillMaxWidth().clickable{OnClick}.padding(16.dp)){

        //Row needs the image
        Text(site.name)

        Text(site.location.toString())

    }
}