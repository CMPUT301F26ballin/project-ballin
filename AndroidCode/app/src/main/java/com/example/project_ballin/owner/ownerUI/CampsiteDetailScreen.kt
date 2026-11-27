package com.example.project_ballin

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
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
import androidx.room.util.TableInfo
import com.example.project_ballin.reusableui.GeneralReportButton
import com.example.project_ballin.reusableui.TopAppBar
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.Blob
import com.google.zxing.qrcode.encoder.QRCode

/**
 * The view class where entrants and owners can view the details of a given campsite
 * Owners are given extra options for campsites they own.
 *
 * */

class CampsiteDetailScreen(){


    /**
     * The screen that shows the details of a selected campsite. Used by both owners and entrants.
     * Owners get extra button options related to the campsite (allowing them to update the site's details or look at reservations.)
     *
     * @param site The campsite being viewed.
     * @param userID the id of the user accessing the view (useful when determining whether it deserves access to the site.)
     *
     * */
    @SuppressLint("NotConstructor")
    @Composable
    fun CampsiteDetailScreen(navController: NavController, site: Campsite, userID: String) {

        val datePicker = GeneralDatePicker()
        val map = GoogleMapScreen()
        var mapMoving by remember { mutableStateOf(false) }

        TopAppBar(navController, "Campsite Details") {
            var showAvailability by remember { mutableStateOf(false) }
            var showMap by remember { mutableStateOf(false) }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp, 0.dp)
                    .verticalScroll(rememberScrollState())
            ) {

                Spacer(modifier = Modifier.padding(8.dp))

                //NAME
                Row{
                    Text(
                        text = site.name,
                        style = MaterialTheme.typography.headlineLarge
                    )
                }

                HorizontalDivider(modifier = Modifier.padding(0.dp, 0.dp,0.dp, 16.dp))

                //image Display
                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState(), enabled = !mapMoving)
                ) {
                    site.images.forEach { blob -> ImageDisplayRow(blob) }
                }

                Spacer(modifier = Modifier.padding(8.dp))

                Card{
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        //DESC
                        Row{
                            Text(
                            text = "Campsite description:",
                            style = MaterialTheme.typography.titleMedium
                            )
                        }
                        Row{
                            Text(
                                text = site.description,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }

                        Spacer(modifier = Modifier.padding(8.dp))

                        //Price and occupancy
                        Row{
                            Text("Price: ",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text("$${"%.2f".format(site.price)}/night")

                        }

                        Row{
                            Text("Occupancy: ",
                                style = MaterialTheme.typography.titleMedium
                            )

                            Text("${site.occupancy}")
                        }

                        //Location
                        Row{
                            Text("Located in: ",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(site.locationName)
                        }

                        Spacer(modifier = Modifier.padding(8.dp))

                        //Rating
                        Row{
                            Text("Rating: ",
                                style = MaterialTheme.typography.titleMedium
                            )

                            if (site.rating != null) {
                                Text("${"%.1f".format(site.rating)}/5")
                            } else {
                                Text("No ratings available")
                            }
                        }

                    }
                }

                HorizontalDivider(modifier = Modifier.padding(0.dp, 16.dp,0.dp, 16.dp))


                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .aspectRatio(1f)
                    ,
                    horizontalArrangement = Arrangement.Center
                ) {

                    map.GoogleMapScreen(LatLng(site.lat, site.lon),
                        false,
                        onNewLocation = {},
                        onMoving = {mapMoving = it})

                }

                HorizontalDivider(modifier = Modifier.padding(0.dp, 16.dp,0.dp, 16.dp))

                if (showAvailability) {
                    datePicker.DatePick(
                        site.availability,
                        onDismiss = { showAvailability = false },
                        onSelect = { showAvailability = false },
                        adding = false
                    )
                }


                Button(
                    onClick = { showAvailability = true },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text("Show available dates")
                }

                if (site.ownerID == userID) {
                    Row{
                        Button(
                            onClick = { navController.navigate("CampsiteInputScreen/${site.ID}/${site.ownerID}") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("updateCampsite")
                        ) {
                            Text("Update campsite")
                        }
                    }
                    Row{
                        Button(onClick = {navController.navigate("OwnerReservationScreen/${site.ID}/Active/${site.ownerID}")},
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("viewActiveReservationsButton")) {
                            Text("View active reservations")
                        }
                    }
                    Row{
                        Button(onClick = {navController.navigate("OwnerReservationScreen/${site.ID}/Past/${site.ownerID}")},
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("viewPastReservationsButton")) {
                            Text("View past reservations")
                        }
                    }
                    Row{
                        Button(onClick = {navController.navigate("OwnerReservationScreen/${site.ID}/Cancelled/${site.ownerID}")},
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("viewPastReservationsButton")) {
                            Text("View past reservations")
                        }
                    }
                } else {

                    Row{
                        Button(modifier = Modifier.fillMaxWidth().testTag("ViewLotteriesButton"),
                            onClick = { navController.navigate("CampsiteLotteryScreen/$userID/${site.ID}") }){
                            Text("View active lotteries")
                        }
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(0.dp, 16.dp))

                //RULES
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ){
                        Text("Rules & Cancellation:",
                            style = MaterialTheme.typography.titleMedium)

                        Text(site.rules,
                            style = MaterialTheme.typography.bodyMedium)
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(0.dp, 16.dp))

                Row(modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ){
                    Log.d("Qr", "Getting Bytes")
                    if(site.qrCode == null){
                        Log.d("Qr", "Null QR")
                    }
                    else{
                        val byteArray = site.qrCode.toBytes()
                        Log.d("Qr", "Bytes!")
                        Image(
                            bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                                .asImageBitmap(),
                            contentDescription = "CampQRCode"
                        )
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(0.dp, 16.dp))

                if(site.ownerID != userID){
                    // Anyone who doesn't own this campsite can report it, or report its owner
                    val reportButton = GeneralReportButton()
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        reportButton.ReportButton(
                            subjectReport = "Campsite",
                            clickedID = site.ID,
                            navController = navController,
                            label = "Report campsite",
                            testTag = "reportCampsiteButton",
                            modifier = Modifier.weight(1f)
                        )
                        reportButton.ReportButton(
                            subjectReport = "Owner",
                            clickedID = site.ownerID,
                            navController = navController,
                            label = "Report owner",
                            testTag = "reportOwnerButton",
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun ImageDisplayRow(image: Blob) {
        val imageBytes: ByteArray = image.toBytes()
        val bitmap: Bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        Card{
            Image(bitmap = bitmap.asImageBitmap(), contentDescription = "Camp Promo Image")
        }

    }
}