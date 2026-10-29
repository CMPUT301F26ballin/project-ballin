package com.example.project_ballin

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlin.uuid.Uuid

/**
 * The screen that shows a list of all the cities of the owner accessing
 *
 *
 * @param navController the navController of the app
 * @param ownerID the ID of the owner user accessing the list.
 * @param getOwnerSites calls getOwnerSites on the main campsite list defined in the BallinApp
 *
 * */


@Composable
fun CampsiteListView(navController: NavController, ownerID: Uuid, getOwnerSites: (Uuid) -> List<Campsite>){

    val campsitesByOwner = getOwnerSites(ownerID)

    //Paste the skeleton pieces here once they are made.
    Button(onClick ={ val ID = null
        navController.navigate("CampsiteInputScreen/$ID")} )
    {
        Text("Create Campsite")
    }

    Spacer(modifier = Modifier.height(16.dp))

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        itemsIndexed(campsitesByOwner){ index, campsite ->
            val ID = campsite.ID
            CampsiteItem(campsite, OnClick = {navController.navigate("CampsiteDetailScreen/$ID")})
        }
    }

}



@Composable
fun CampsiteListScreen(navController: NavController, ownerID: Uuid, getOwnerSites: (Uuid) -> List<Campsite>){
    TopAppBar(navController, "Your Campsites"){
        CampsiteListView(navController, ownerID, getOwnerSites = {getOwnerSites(it)})
    }


}



/**
 * A single row of the campsite list, it details the basic identifying information
 * (name, location, and images) of the campsite. The item is also clickable
 *
 * @param site the Campsite of the entry,
 * @param OnClick the function defining what to do when the row is clicked
 * It will navigate to the CampsiteDetailScreen
 * */


@Composable
fun CampsiteItem(site: Campsite, OnClick: () -> Unit)
{
    Row( modifier = Modifier.fillMaxWidth().clickable{OnClick()}.padding(16.dp)){

        //Row needs the image
        Text(site.name)

        //Text(site.location.toString())

    }
}

@Preview (showBackground = true)
@Composable
fun CampsiteListScreenPreview(){
    val navController = rememberNavController()

}