package com.example.project_ballin

import android.graphics.Paint
import android.location.Location
import android.media.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.w3c.dom.Text
import java.nio.file.WatchEvent

/**
 * The screen where the owner can input the details of a campsite.
 * This screen is used when a new campsite is being entered or when an existing
 * campsite is being updated.
 *
 * @param navController the navcontroller of the app
 * @param siteID a nullable integer that is not null if the campsite is being updated.
 * @param ownerID the UserID of the owner
 * @param getSite the function to return the site details depending on the site ID. Only gets called
 * if site is not null.
 * @param addSite the function that adds a campsite to the
 *
 *
 * */

@Composable
fun CampsiteInputScreen (navController: NavController, siteID: Int?, ownerID: String, getSite: (Int) -> Campsite,
                         addSite: (Campsite) -> Unit,
                         updateSite: (oldSite: Campsite, newSite: Campsite) -> Unit){
    TopAppBar(navController, "Input Campsite Details"){
        CampsiteInputView(navController, siteID, ownerID, getSite = {getSite(it)},
            addSite = {addSite(it)},
            updateSite = {oldSite, newSite -> updateSite(oldSite, newSite)})
    }
}

@Composable
fun CampsiteInputView (navController: NavController, siteID: Int?, ownerID: String, getSite: (Int) -> Campsite,
                       addSite: (Campsite) -> Unit,
                       updateSite: (oldSite: Campsite, newSite: Campsite) -> Unit){
    //initializing input variables (there's a lot of them)
    var campName by remember {mutableStateOf("")}
    //var campLocation by remember {mutableStateOf<Location>("")}
    var campTags = mutableListOf<String>()
    var tagToAdd by remember { mutableStateOf("") }
    var campPrice by remember { mutableStateOf("") } //This will be changed to float.
    var campRules by remember {mutableStateOf("")}
    var campDesc by remember {mutableStateOf("")}
    var campOccupancy by remember {mutableStateOf("")}
    //var campImages by remember {mutableStateListOf<Image>()}


    //the old campsite (if we are updating) null if this is a new site.
    var oldSite: Campsite? = null
    if (siteID != null){
        oldSite = getSite(siteID)
        campName = oldSite.name
        campPrice = oldSite.price.toString()
        campTags = oldSite.tags
        campRules = oldSite.rules
        campDesc = oldSite.description
        campOccupancy = oldSite.occupancy.toString()

    }



    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)){
            //Camp Name Box
            OutlinedTextField(
                value = campName,
                onValueChange = { campName = it },
                label = { Text("Site Name") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            //Price Box
            OutlinedTextField(
                value = campPrice,
                onValueChange = { campPrice = it },
                label = { Text("Price Per Night") },
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        //Rules Box
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)){
            OutlinedTextField(
                value = campRules,
                onValueChange = { campRules = it },
                label = { Text("Rules & Cancellation Policy") },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        //Description box
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)){
            OutlinedTextField(
                value = campDesc,
                onValueChange = { campDesc = it },
                label = { Text("Camp Description") },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        //Tags Addition
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)){
            LazyColumn() {
                itemsIndexed(campTags){index, tag ->
                    TagRow(tag, onDelete = {campTags.remove(tag)})
                }
            }

            OutlinedTextField(
                value = tagToAdd,
                onValueChange = { tagToAdd = it },
                label = { Text("Tag") },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )
            Button(onClick = {
                if(tagToAdd.isNotBlank()){
                    campTags.add(tagToAdd)
                    tagToAdd = ""
                }
            }) {
                Text("Add Tag")
            }

        }

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = campOccupancy,
            onValueChange = {campOccupancy = it},
            label = { Text("Occupancy") },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)){
            if (oldSite == null) {
                Button(onClick = {
                    if (campName.isNotBlank() &&
                        campDesc.isNotBlank() &&
                        campOccupancy.isNotBlank() &&
                        campPrice.isNotBlank() &&
                        campRules.isNotBlank()
                    ) {
                        //must assert numbers in price and occupancy
                        addSite(
                            Campsite(
                                name = campName,
                                ID = "INSERT ID", //FIGURE OUT ID IMPLEMENTATION
                                ownerID = ownerID,
                                price = campPrice.toFloat(),
                                rating = null,
                                tags = campTags,
                                rules = campRules,
                                description = campDesc,
                                occupancy = campOccupancy.toInt()
                                //Campsite needs other elements based on what is commented out in campsite object
                            )
                        )
                        navController.popBackStack()
                    }

                }) {
                    Text("Add Campsite")
                }
            } else if (oldSite != null) {
                Button(onClick = {
                    if (campName.isNotBlank() &&
                        campDesc.isNotBlank() &&
                        campOccupancy.isNotBlank() &&
                        campPrice.isNotBlank() &&
                        campRules.isNotBlank()
                    ) {
                        updateSite(
                            oldSite, Campsite(
                                name = campName,
                                ID = oldSite.ID,
                                ownerID = oldSite.ownerID,
                                price = campPrice.toFloat(),
                                rating = oldSite.rating,
                                tags = campTags,
                                rules = campRules,
                                description = campDesc,
                                occupancy = campOccupancy.toInt()
                            )
                        )
                        navController.popBackStack()
                    }


                }) {
                    Text("Update Campsite")
                }
            }
        }


    }





}
@Composable
fun TagRow(tag: String, onDelete:(String)-> Unit){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)){
        Text(tag)
        Spacer(modifier = Modifier.width(16.dp))
        Button(onClick = {onDelete(tag)}){
            Text("x")
        }
    }

}