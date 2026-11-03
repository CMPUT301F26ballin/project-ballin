package com.example.project_ballin.owner.ownerUI

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Button
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.content.MediaType.Companion.Image
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.InternalComposeApi
import androidx.compose.runtime.currentCompositionContext
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.project_ballin.Campsite
import com.example.project_ballin.reusableui.TopAppBar
import java.nio.file.WatchEvent
import kotlin.let
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class, InternalComposeApi::class)


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
fun CampsiteInputScreen (navController: NavController, oldSite: Campsite?, ownerID: Uuid,
                         addSite: (Campsite) -> Unit,
                         updateSite: (oldSite: Campsite, newSite: Campsite) -> Unit){
    TopAppBar(
        navController,
        "Input Campsite Details"
    ) {
        //initializing input variables (there's a lot of them)
        var campName by remember {mutableStateOf("")}
        //var campLocation by remember {mutableStateOf<Location>("")}
        var campTags = remember { mutableStateListOf<String>() }
        var tagToAdd by remember { mutableStateOf("") }
        var campPrice by remember { mutableStateOf("") } //This will be changed to float.
        var campRules by remember {mutableStateOf("")}
        var campDesc by remember {mutableStateOf("")}
        var campOccupancy by remember {mutableStateOf("")} //This will be changed to an int
        var campImages = remember {mutableStateListOf<Uri>()}
        val campByteArray = remember { mutableStateListOf<ByteArray>() }

        val pickerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            uri?.let{
                campImages.add(uri)
            }

        }


        //the old campsite (if we are updating) null if this is a new site.
        if (oldSite != null){

            campName = oldSite.name
            campPrice = oldSite.price.toString()
            oldSite.tags.forEach {tag -> campTags.add(tag) }
            campRules = oldSite.rules
            campDesc = oldSite.description
            campOccupancy = oldSite.occupancy.toString()
            oldSite.images.forEach { byteArray -> campByteArray.add(byteArray) }

        }



        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)){
                //Camp Name Box
                OutlinedTextField(
                    value = campName,
                    onValueChange = { campName = it },
                    label = { Text("Site Name") },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("campName")
                )
                Spacer(modifier = Modifier.width(16.dp))
                //Price Box
                OutlinedTextField(
                    value = campPrice,
                    onValueChange = { campPrice = it },
                    label = { Text("Price Per Night") },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("campPrice")
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
                        .testTag("campRules")
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
                        .testTag("campDesc")
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            //Tags Addition

            campTags.forEach { tag-> TagRow(tag, onDelete = {campTags.remove(tag)}) }


            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)){
                OutlinedTextField(
                    value = tagToAdd,
                    onValueChange = { tagToAdd = it },
                    label = { Text("Tag") },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .testTag("tagInput")
                )
                Button(onClick = {
                    if(tagToAdd.isNotBlank()){
                        campTags.add(tagToAdd)
                        tagToAdd = ""
                    }
                },
                    modifier = Modifier
                        .padding(16.dp)
                        .testTag("addTag")) {
                    Text("Add Tag")
                }

            }

            Spacer(modifier = Modifier.height(16.dp))

            //OCCUPANCY
            OutlinedTextField(
                value = campOccupancy,
                onValueChange = {campOccupancy = it},
                label = { Text("Occupancy") },
                modifier = Modifier
                    .weight(1f)
                    .testTag("campOccupancy")
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            //LOCATION
            OutlinedTextField(
                value = campOccupancy,
                onValueChange = {campOccupancy = it},
                label = { Text("Location") },
                modifier = Modifier
                    .weight(1f)
                    .testTag("campOccupancy")
                    .fillMaxWidth()
            )

            //IMAGE DISPLAY

            Spacer(modifier = Modifier.height(16.dp))
            if(campImages.isNotEmpty()){
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                ) {
                    campImages.forEach { uri ->

                        val context: Context = LocalContext.current
                        val bytes: ByteArray = context.contentResolver.openInputStream(uri).use {it!!.buffered().readBytes()}
                        campByteArray.add(bytes)
                        ImageRow(
                            bytes,
                            OnDelete = { campByteArray.remove(it) })
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                Text("Click images to remove them.")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {pickerLauncher.launch("image/*")},
                modifier = Modifier.fillMaxWidth()){
                Text("Add Promo Image")
            }


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
                                    ID = Uuid.random(), //FIGURE OUT ID IMPLEMENTATION
                                    ownerID = ownerID,
                                    price = campPrice.toFloat(),
                                    rating = null,
                                    tags = campTags,
                                    rules = campRules,
                                    description = campDesc,
                                    occupancy = campOccupancy.toInt(),
                                    images = campByteArray
                                    //Campsite needs other elements based on what is commented out in campsite object
                                )
                            )
                            navController.popBackStack()
                        }

                    },
                        modifier = Modifier.testTag("addSiteButton")) {
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
                                    occupancy = campOccupancy.toInt(),
                                    images = campByteArray
                                )
                            )
                            navController.popBackStack()
                        }


                    },
                        modifier = Modifier.testTag("updateSiteButton")) {
                        Text("Update Campsite")
                    }
                }
            }


        }
    }
}

@OptIn(InternalComposeApi::class)
@Composable
fun ImageRow(image: ByteArray, OnDelete:(ByteArray) -> Unit){
    val bitmap: Bitmap = BitmapFactory.decodeByteArray(image,0,image.size)
    Column(modifier = Modifier
        .padding(6.dp)
        .clickable(onClick = { OnDelete(image) })){
        Image(bitmap = bitmap.asImageBitmap(), contentDescription = "Camp Promo Image")
    }

}


@Composable
fun TagRow(tag: String, onDelete:(String)-> Unit){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)){
        Text(tag)
        Spacer(modifier = Modifier.width(16.dp))
        Button(onClick = {onDelete(tag)},
            modifier = Modifier.testTag("removeTag")){
            Text("x")
        }
    }

}