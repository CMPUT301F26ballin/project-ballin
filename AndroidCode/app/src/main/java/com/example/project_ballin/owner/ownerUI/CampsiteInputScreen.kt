package com.example.project_ballin.owner.ownerUI

import android.R
import android.app.DatePickerDialog
import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.net.Uri
import android.util.Log
import android.widget.Button
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.content.MediaType.Companion.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.InternalComposeApi
import androidx.compose.runtime.currentCompositionContext
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.project_ballin.Campsite
import com.example.project_ballin.DatePick
import com.example.project_ballin.reusableui.TopAppBar
import com.google.firebase.firestore.Blob
import java.nio.file.WatchEvent
import java.sql.Date
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
 * @param addSite the function that adds a campsite to the firestore
 * @param updateSite the function that updates a campsite in the firestore.
 *
 *
 * Sources Used (Organized for ease of reading):
 *
 * Images:
 *
 * Title: Blob
 * Last Updated: 2026-10-07 UTC
 * https://firebase.google.com/docs/reference/android/com/google/firebase/firestore/Blob#fromBytes%28byte[]%29
 *
 * Title: How to set bytes type to field in firestore
 * Inquirer: user13296045 (April 15, 2020)
 * Responder: Alex Mamo (April 16, 2020)
 * https://stackoverflow.com/questions/61230610/how-to-set-bytes-type-to-field-in-firestore
 *
 *
 *
 *
 * */


@Composable
fun CampsiteInputScreen (navController: NavController, oldSite: Campsite?, ownerID: String,
                         addSite: (Campsite) -> Unit,
                         updateSite: (oldSite: Campsite, newSite: Campsite) -> Unit){
    TopAppBar(
        navController,
        "Input Campsite Details"
    ) {
        //initializing input variables (there's a lot of them)
        var campName by remember {mutableStateOf("")}
        var campLat by remember {mutableStateOf("")}
        var campLon by remember {mutableStateOf("")}
        var campLocation by remember { mutableStateOf("") }
        var campTags = remember { mutableStateListOf<String>() }
        var tagToAdd by remember { mutableStateOf("") }
        var campPrice by remember { mutableStateOf("") } //This will be changed to float.
        var campRules by remember {mutableStateOf("")}
        var campDesc by remember {mutableStateOf("")}
        var campOccupancy by remember {mutableStateOf("")} //This will be changed to an int
        var campImages = remember {mutableStateListOf<Uri>()}
        val campByteArray = remember { mutableStateListOf<Blob>() }
        val campAvailableDates = remember { mutableStateListOf<Long>() }
        var copied by remember { mutableStateOf(0) }

        var showDatePicker by remember { mutableStateOf(false) }
        var addDate by remember { mutableStateOf(true) }




        val pickerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            uri?.let{
                campImages.add(uri)
            }

        }


        //the old campsite (if we are updating) null if this is a new site.
        if (oldSite != null && copied == 0){
            Log.d("campsite", "There is an old site")
            campName = oldSite.name
            campPrice = oldSite.price.toString()
            oldSite.tags.forEach {tag -> campTags.add(tag) }
            campRules = oldSite.rules
            campDesc = oldSite.description
            campOccupancy = oldSite.occupancy.toString()
            oldSite.images.forEach { byteArray -> campByteArray.add(byteArray) }
            oldSite.availability.forEach { date -> campAvailableDates.add(date) }
            campLat = oldSite.lat.toString()
            campLon = oldSite.lon.toString()
            campLocation = oldSite.locationName
            copied = 1
        }



        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally) {
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

            //OCCUPANCY
            Row(modifier = Modifier.padding(16.dp)){
                OutlinedTextField(
                    value = campOccupancy,
                    onValueChange = { campOccupancy = it },
                    label = { Text("Occupancy") },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("campOccupancy")
                        .fillMaxWidth()
                )
            }

            //AVAILABLE DATES

            if(showDatePicker){
                DatePick(campAvailableDates,
                    onDismiss = {showDatePicker = false},
                    onSelect = {

                        if(addDate){

                            it.forEach{ date->
                                if (date !in campAvailableDates) {
                                    campAvailableDates.add(date)
                                }
                            }
                         }
                        else{
                            it.forEach{ date->
                                if (date in campAvailableDates) {
                                    campAvailableDates.remove(date)
                                }
                            }
                        }
                },
                    adding = addDate)

            }


            Row(modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()){
                Button(
                    onClick = {addDate = true
                    showDatePicker = true},
                    modifier = Modifier.testTag("addDateButton")){
                    Text("Add Availability")
                }
                Button(
                    onClick = {addDate = false
                    showDatePicker = true},
                    modifier = Modifier.testTag("removeDateButton")
                ){
                    Text("Remove Availability")
                }
            }




            //LOCATION
            Row(modifier = Modifier.padding(16.dp)){
                OutlinedTextField(
                    value = campLat,
                    onValueChange = { campLat = it },
                    label = { Text("Latitude") },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("campLat")
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.width(16.dp))
                OutlinedTextField(
                    value = campLon,
                    onValueChange = { campLon = it },
                    label = { Text("Longitude") },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("campLon")
                        .fillMaxWidth()
                )
            }

            Row(modifier = Modifier.padding(16.dp)) {
                OutlinedTextField(
                    value = campLocation,
                    onValueChange = { campLocation = it },
                    label = { Text("Location Name") },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("campLocName")
                        .fillMaxWidth()
                )
            }

            //IMAGE DISPLAY

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
                        campByteArray.add(Blob.fromBytes(bytes))
                        ImageRow(
                            Blob.fromBytes(bytes),
                            onDelete = { campByteArray.remove(it)
                            campImages.remove(uri)})
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
                                    lon = campLon.toDouble(),
                                    lat = campLat.toDouble(),
                                    ID = Uuid.random().toString(), //FIGURE OUT ID IMPLEMENTATION
                                    ownerID = ownerID,
                                    price = campPrice.toFloat(),
                                    rating = null,
                                    tags = campTags,
                                    rules = campRules,
                                    description = campDesc,
                                    occupancy = campOccupancy.toInt(),
                                    images = campByteArray,
                                    //Campsite needs other elements based on what is commented out in campsite object
                                    locationName = campLocation,
                                    availability = campAvailableDates,
                                    lotteries = mutableListOf() //TODO
                                )
                            )
                            Log.d("Navigation", "Adding campsite and returning to list")
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
                            val location = Location(campLocation)
                            location.latitude = campLat.toDouble()
                            location.longitude = campLon.toDouble()
                            updateSite(
                                oldSite, Campsite(
                                    //location = location,
                                    name = campName,
                                    ID = oldSite.ID,
                                    lon = campLon.toDouble(),
                                    lat = campLat.toDouble(),
                                    ownerID = oldSite.ownerID,
                                    price = campPrice.toFloat(),
                                    rating = oldSite.rating,
                                    tags = campTags,
                                    rules = campRules,
                                    description = campDesc,
                                    occupancy = campOccupancy.toInt(),
                                    images = campByteArray,
                                    locationName = "",
                                    availability = campAvailableDates,
                                    lotteries = mutableListOf()//TODO
                                )
                            )
                            Log.d("Navigation", "Updating campsite and returning to campsite details")
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


/**
 * The composable collumns that show the uploaded campsite images.
 *
 * @param image a blob dataclass of the image being shown
 * @param onDelete the function that tells the composable what to delete
 *
 *
 * */

@Composable
fun ImageRow(image: Blob, onDelete:(Blob) -> Unit){
    val imageBytes: ByteArray = image.toBytes()
    val bitmap: Bitmap = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.size)
    Column(modifier = Modifier
        .padding(6.dp)
        .clickable(onClick = { onDelete(image) })){
        Image(bitmap = bitmap.asImageBitmap(), contentDescription = "Camp Promo Image")
    }

}



/**
 * A composable row that shows which tags the Campsite currently has.
 * Each tag can be individually deleted by a button next to it.
 *
 * @param tag the String holding the tag name. Tags are mostly created and defined by the owners
 * @param onDelete the function that tells the composable what tag to delete.
 * */
@Composable
fun TagRow(tag: String, onDelete:(String)-> Unit){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
        horizontalArrangement = Arrangement.Center){
        Text(tag)
        Spacer(modifier = Modifier.width(16.dp))
        Button(onClick = {onDelete(tag)},
            modifier = Modifier.testTag("removeTag")){
            Text("x")
        }
    }

}