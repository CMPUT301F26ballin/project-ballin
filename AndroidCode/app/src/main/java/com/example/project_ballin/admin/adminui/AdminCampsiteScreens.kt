package com.example.project_ballin

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.project_ballin.reusableui.TopAppBar
import com.google.firebase.firestore.Blob

class AdminCampsiteScreens {

    val adminSearch = AdminSearch()
    val confirmDeleteDialog = ConfirmDeleteDialog()
    /**
     * Admin screen listing every campsite in the app, each with a Remove button.
     *
     * @param navController the app's navController
     * @param campsites the campsites to show
     * @param onDelete removes a campsite
     */
    @Composable
    fun AdminCampsiteListScreen(
        navController: NavController,
        campsites: List<Campsite>,
        onDelete: (Campsite) -> Unit
    ) {
        TopAppBar(navController, "Campsites") {
            AdminCampsiteListView(
                campsites = campsites,
                onCampsiteClick = { site ->
                    navController.navigate("AdminCampsiteDetail/${site.ID}")
                },
                onDelete = onDelete
            )
        }
    }
    /**
     * Searches a campsite by name, location, description, tags, and owner ID.
     *
     * @param query the search text
     * @return true if the campsite matches the search
     */
    fun Campsite.matchesSearch(query: String): Boolean =
        adminSearch.matchesQuery(query, name, locationName, description, tags.joinToString(" "), ownerID)
    /**
     * The campsite list itself (no top bar), so it can be tested on its own.
     *
     * @param campsites the campsites to show
     * @param onCampsiteClick runs when a campsite is tapped
     * @param onDelete runs after a remove is confirmed
     */
    @Composable
    fun AdminCampsiteListView(
        campsites: List<Campsite>,
        onCampsiteClick: (Campsite) -> Unit,
        onDelete: (Campsite) -> Unit
    ) {
        // Campsite waiting for delete confirmation (null = no pop-up)
        var siteToDelete by remember { mutableStateOf<Campsite?>(null) }

        // What the admin typed in the search box (kept when the screen rotates)
        var query by rememberSaveable { mutableStateOf("") }
        val results = campsites.filter { it.matchesSearch(query) }

        if (campsites.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("There are no campsites yet.", modifier = Modifier.testTag("noCampsites"))
            }
        } else {
            Column(modifier = Modifier.fillMaxSize()) {
                adminSearch.AdminSearchField(
                    query,
                    onQueryChange = { query = it },
                    placeholder = "Search campsites"
                )

                if (results.isEmpty()) {
                    Text(
                        "No campsites match \"${query.trim()}\".",
                        modifier = Modifier.padding(16.dp).testTag("noSearchResults")
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().padding(16.dp)
                            .testTag("adminCampsiteList"),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(results, key = { it.ID }) { site ->
                            AdminCampsiteItem(
                                site = site,
                                onClick = { onCampsiteClick(site) },
                                onRemoveClick = { siteToDelete = site }
                            )
                        }
                    }
                }
            }
        }

        siteToDelete?.let { site ->
            confirmDeleteDialog.ConfirmDeleteDialog(
                itemDescription = "the campsite \"${site.name}\"",
                onConfirm = {
                    onDelete(site)
                    siteToDelete = null
                },
                onCancel = { siteToDelete = null }
            )
        }
    }

    /**
     * One row in the campsite list: name, location, price, and a Remove button.
     *
     * @param site the campsite to show
     * @param onClick runs when the row is tapped
     * @param onRemoveClick runs when Remove is pressed
     */
    @Composable
    fun AdminCampsiteItem(site: Campsite, onClick: () -> Unit, onRemoveClick: () -> Unit) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .testTag("adminCampsiteItem_${site.ID}")
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(site.name, fontSize = 18.sp)
                    Text(
                        site.locationName,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(formatPrice(site.price) + " / night", fontSize = 12.sp)
                }
                Button(
                    onClick = onRemoveClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    ),
                    modifier = Modifier.testTag("removeCampsite_${site.ID}")
                ) {
                    Text("Remove")
                }
            }
        }
    }

    /**
     * Admin screen showing one campsite's full details, with a Remove button.
     *
     * @param navController the app's navController
     * @param site the campsite to show (null if it was deleted)
     * @param onDelete removes the campsite
     */
    @Composable
    fun AdminCampsiteDetailScreen(
        navController: NavController,
        site: Campsite?,
        onDelete: (Campsite) -> Unit
    ) {
        TopAppBar(navController, "Campsite Details") {
            AdminCampsiteDetailView(
                site = site,
                onDelete = { deleted ->
                    onDelete(deleted)
                    navController.popBackStack() // back to the list
                }
            )
        }
    }

    /**
     * The campsite details itself (no top bar), so it can be tested on its own.
     *
     * @param site the campsite to show (null if it was deleted)
     * @param onDelete runs after a remove is confirmed
     */
    @Composable
    fun AdminCampsiteDetailView(site: Campsite?, onDelete: (Campsite) -> Unit) {
        if (site == null) {
            Box(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("This campsite no longer exists.")
            }
            return
        }

        var showDialog by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            CampsiteDetailRow("Name", site.name)
            CampsiteDetailRow("Location", site.locationName)
            CampsiteDetailRow("Description", site.description)
            CampsiteDetailRow("Price per night", formatPrice(site.price))
            CampsiteDetailRow("Occupancy", site.occupancy.toString())
            CampsiteDetailRow("Rating", site.rating?.toString() ?: "No ratings yet")
            CampsiteDetailRow("Tags", site.tags.joinToString(", ").ifBlank { "None" })
            CampsiteDetailRow("Rules & cancellation policy", site.rules.ifBlank { "None" })
            CampsiteDetailRow("Owner ID", site.ownerID)
            CampsiteImageGallery(site.images)
            // TODO: show the QR code once campsites have one

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { showDialog = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                ),
                modifier = Modifier.fillMaxWidth().testTag("removeCampsiteDetail")
            ) {
                Text("Remove Campsite")
            }
        }

        if (showDialog) {
            confirmDeleteDialog.ConfirmDeleteDialog(
                itemDescription = "the campsite \"${site.name}\"",
                onConfirm = {
                    showDialog = false
                    onDelete(site)
                },
                onCancel = { showDialog = false }
            )
        }
    }

    /**
     * A row of the campsite's images that scrolls sideways.
     *
     * @param images the campsite's images
     */
    @Composable
    fun CampsiteImageGallery(images: List<Blob>) {
        Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
            Text("Images", fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)

            if (images.isEmpty()) {
                Text("No images", fontSize = 18.sp, modifier = Modifier.testTag("noCampsiteImages"))
            } else {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(top = 4.dp).testTag("campsiteImageGallery")
                ) {
                    itemsIndexed(images) { index, image ->
                        CampsiteImage(image, index)
                    }
                }
            }
        }
    }

    /**
     * One campsite image. Shows a message instead if the image can't be read.
     *
     * @param image the image's bytes
     * @param index the image's position in the gallery, used for its test tag
     */
    @Composable
    fun CampsiteImage(image: Blob, index: Int) {
        // Only decode the image again if it changes
        val bitmap = remember(image) {
            val bytes = image.toBytes()
            BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        }

        if (bitmap == null) {
            Box(
                modifier = Modifier.size(150.dp).testTag("campsiteImage_$index"),
                contentAlignment = Alignment.Center
            ) {
                Text("Can't show image", fontSize = 12.sp)
            }
        } else {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Campsite image ${index + 1}",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(150.dp).testTag("campsiteImage_$index")
            )
        }
    }

    /**
     * One labelled line of campsite info, e.g. "Location" and the place.
     *
     * @param label the field name
     * @param value the field value
     */
    @Composable
    fun CampsiteDetailRow(label: String, value: String) {
        Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
            Text(label, fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
            Text(value, fontSize = 18.sp)
        }
    }

    /**
     * Turns a price into text like "$25.00".
     *
     * @param price the price
     * @return the price as text
     */
    fun formatPrice(price: Float): String = "$" + String.format("%.2f", price)

    private val sampleCampsites = listOf(
        Campsite(
            name = "Waterton Campground 1",
            ID = "site1",
            locationName = "Waterton Lakes",
            tags = mutableListOf("Lakeside", "Tents"),
            price = 25f,
            rating = 4.5f,
            rules = "No fires after 11pm. Free cancellation up to 2 days before.",
            description = "A quiet lakeside site, perfect for tents.",
            occupancy = 6,
            ownerID = "owner1"
        ),
        Campsite(
            name = "Waterton Campground 2",
            ID = "site2",
            locationName = "Waterton Lakes",
            price = 30f,
            description = "Close to the trails.",
            occupancy = 4,
            ownerID = "owner1"
        )
    )

    @Preview(showBackground = true)
    @Composable
    fun AdminCampsiteListScreenPreview() {
        val navController = rememberNavController()
        AdminCampsiteListScreen(navController, sampleCampsites, onDelete = {})
    }

    @Preview(showBackground = true)
    @Composable
    fun AdminCampsiteDetailScreenPreview() {
        val navController = rememberNavController()
        AdminCampsiteDetailScreen(navController, sampleCampsites[0], onDelete = {})
    }
}