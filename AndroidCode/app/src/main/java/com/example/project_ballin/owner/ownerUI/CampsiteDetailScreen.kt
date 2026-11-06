package com.example.project_ballin

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.project_ballin.owner.ownerUI.TagRow
import com.example.project_ballin.reusableui.TopAppBar
import com.google.firebase.firestore.Blob

/**
 * The screen that shows the details of a selected campsite. Used by both owners and entrants.
 * Owners get extra button options related to the campsite (allowing them to update the site's details or look at reservations.)
 *
 * @param site The campsite being viewed.
 * @param userID the id of the user accessing the view (useful when determining whether it deserves access to the site.)
 *
 * */
@Composable
fun CampsiteDetailScreen(navController: NavController, site: Campsite, userID: String){
    TopAppBar(navController, "Campsite Details") {
        var showAvailability by remember { mutableStateOf(false) }

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())) {

            //image Display
            Row(modifier = Modifier
                .padding(12.dp)
                .horizontalScroll(rememberScrollState())){
                site.images.forEach { blob -> ImageDisplayRow(blob)}
            }
            //NAME
            Row(modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.Center
            ){ Text("Campsite Name: ${site.name}") }
            //DESC
            Row(modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.Center
            ){ Text("Campsite Description: ${site.description}") }
            //Price and occupancy
            Row (modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)){
                Text("Price Per Night: ${site.price}")
                Spacer(Modifier.width(16.dp))
                Text("Occupancy: ${site.occupancy}")
            }
            //Location
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)){
                Text("Location Name: ${site.locationName}")
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)){
                Text("Lat: ${site.lat} Lon ${site.lon}")
            }

            if(showAvailability){
                DatePick(site.availability,
                    onDismiss = {showAvailability = false},
                    onSelect = {showAvailability = false},
                    adding = false)
            }

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)){
                Button(onClick = {showAvailability = true},
                    modifier = Modifier.fillMaxWidth().padding(16.dp)){
                    Text("Show Available Dates")
                }
            }
            //Rating
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)){
                if (site.rating != null) {
                    Text("Current Rating: ${site.rating}")
                } else {
                    Text("No Ratings Available")
                }
            }
            //RULES
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
                Text(site.rules)
            }

            Spacer(Modifier.height(16.dp))

            if(site.ownerID == userID){
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)){
                    Button(onClick = {navController.navigate("CampsiteInputScreen/${site.ID}/${site.ownerID}")},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .testTag("updateCampsite")) {
                        Text("Update Campsite")
                    }
                }
                /*Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)){
                    Button(onClick = {navController.navigate("CampsiteInputScreen/${site.ID}/${site.ownerID}")},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .testTag("viewReservationsButton")) {
                        Text("View Active Reservations")
                    }
                }*/
            }
        }
    }

}

@Composable
fun ImageDisplayRow(image: Blob){
    val imageBytes: ByteArray = image.toBytes()
    val bitmap: Bitmap = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.size)
    Column(modifier = Modifier
        .padding(6.dp)){
        Image(bitmap = bitmap.asImageBitmap(), contentDescription = "Camp Promo Image")
    }

}