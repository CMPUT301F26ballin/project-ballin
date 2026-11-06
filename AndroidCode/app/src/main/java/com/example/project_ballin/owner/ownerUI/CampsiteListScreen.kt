package com.example.project_ballin.owner.ownerUI

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.key.Key.Companion.D
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.project_ballin.Campsite
import com.example.project_ballin.reusableui.TopAppBar
import com.google.apps.card.v1.Widget
import java.nio.file.WatchEvent
import kotlin.compareTo
import kotlin.uuid.Uuid

/**
 * The view class where owners can view a list of all the classes they own
 * */


class CampsiteListScreen()
{

    /**
     * The screen that shows a list of all the cities of the owner accessing
     *
     *
     * @param navController the navController of the app
     * @param ownerID the ID of the owner user accessing the list.
     * @param getOwnerSites calls getOwnerSites on the main campsite list defined in the BallinApp
     *
     * */


    @SuppressLint("NotConstructor")
    @Composable
    fun CampsiteListScreen(
        navController: NavController,
        ownerID: String,
        getOwnerSites: (String) -> List<Campsite>
    ) {
        TopAppBar(navController, "Your Campsites") {
            val campsitesByOwner = getOwnerSites(ownerID)
            Column(modifier = Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            navController.navigate("CampsiteInputScreen/none/$ownerID")
                            Log.d("Navigation", "I should go to input a site.")
                        },
                        modifier = Modifier
                            .padding(16.dp)
                            .testTag("newCampsiteButton")
                    ) {
                        Text("Create Campsite")
                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {

                    itemsIndexed(campsitesByOwner) { index, campsite ->
                        val ID = campsite.ID
                        CampsiteItem(
                            campsite,
                            onClick = { navController.navigate("CampsiteDetailScreen/$ID/$ownerID") })
                        if (index < campsitesByOwner.lastIndex) {
                            HorizontalDivider()
                        }
                    }
                }
            }
        }


    }


    /**
     * A single row of the campsite list, it details the basic identifying information
     * (name, location, and images) of the campsite. The item is also clickable
     *
     * @param site the Campsite of the entry,
     * @param onClick the function defining what to do when the row is clicked
     * It will navigate to the CampsiteDetailScreen
     * */


    @Composable
    fun CampsiteItem(site: Campsite, onClick: () -> Unit) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(16.dp)
                .testTag("Campsite!")) {


            Row(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                if (site.images.isNotEmpty()) {
                    Column(modifier = Modifier.padding(6.dp)) {
                        val imageBytes: ByteArray = site.images[0].toBytes()
                        val bitmap: Bitmap =
                            BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = "First Camp Promo Image"
                        )
                    }
                }

                Column(modifier = Modifier.padding(6.dp)) {
                    Row(modifier = Modifier.padding(10.dp)) { Text(site.name) }

                    Row(modifier = Modifier.padding(10.dp)) { Text(site.locationName) }
                }
            }


        }
    }
}
