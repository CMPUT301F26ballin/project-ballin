package com.example.project_ballin

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.project_ballin.reusableui.TopAppBar
import com.google.firebase.firestore.Blob

/**
 * One uploaded image, and the campsite it belongs to.
 *
 * @property site the campsite the image belongs to
 * @property index the image's position in the campsite's image list
 * @property image the image's bytes
 */
data class AdminImage(
    val site: Campsite,
    val index: Int,
    val image: Blob
) {
    /** A unique name for this image, used for list keys and test tags, e.g. "site1_0". */
    val key: String
        get() = "${site.ID}_$index"
}

/**
 * The admin's screens for browsing every uploaded image (US 03.03.02)
 * and removing them (US 03.03.01).
 */
class AdminImageScreens {

    val confirmDeleteDialog = ConfirmDeleteDialog()

    /**
     * Collects every image from every campsite into one list.
     *
     * @param campsites the campsites to collect images from
     * @return all the images, in campsite order
     */
    fun allCampsiteImages(campsites: List<Campsite>): List<AdminImage> =
        campsites.flatMap { site ->
            site.images.mapIndexed { index, image -> AdminImage(site, index, image) }
        }

    /**
     * Removes an image from its campsite, then saves the campsite.
     *
     * @param image the image to remove
     * @param updateSite saves the campsite, usually CampsiteList.updateCampsite
     */
    fun removeImage(image: AdminImage, updateSite: (oldSite: Campsite, newSite: Campsite) -> Unit) {
        val site = image.site
        if (image.index !in site.images.indices) return // already removed

        site.images = site.images.toMutableList().also { it.removeAt(image.index) }
        updateSite(site, site)
    }

    /**
     * Admin screen showing every uploaded image, each with a Remove button.
     *
     * @param navController the app's navController
     * @param campsites every campsite, to collect the images from
     * @param onRemove removes an image
     */
    @Composable
    fun AdminImageListScreen(
        navController: NavController,
        campsites: List<Campsite>,
        onRemove: (AdminImage) -> Unit
    ) {
        TopAppBar(navController, "Images") {
            AdminImageListView(allCampsiteImages(campsites), onRemove)
        }
    }

    /**
     * The image grid itself (no top bar), so it can be tested on its own.
     *
     * @param images the images to show
     * @param onRemove runs after a remove is confirmed
     */
    @Composable
    fun AdminImageListView(images: List<AdminImage>, onRemove: (AdminImage) -> Unit) {
        // Image waiting for delete confirmation (null = no pop-up)
        var imageToRemove by remember { mutableStateOf<AdminImage?>(null) }

        if (images.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("No images have been uploaded yet.", modifier = Modifier.testTag("noImages"))
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 160.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize().padding(16.dp).testTag("adminImageGrid")
            ) {
                items(images, key = { it.key }) { image ->
                    AdminImageItem(image, onRemoveClick = { imageToRemove = image })
                }
            }
        }

        imageToRemove?.let { image ->
            confirmDeleteDialog.ConfirmDeleteDialog(
                itemDescription = "this image from \"${image.site.name}\"",
                onConfirm = {
                    onRemove(image)
                    imageToRemove = null
                },
                onCancel = { imageToRemove = null }
            )
        }
    }

    /**
     * One image in the grid: the picture, its campsite's name, and a Remove button.
     *
     * @param image the image to show
     * @param onRemoveClick runs when Remove is pressed
     */
    @Composable
    fun AdminImageItem(image: AdminImage, onRemoveClick: () -> Unit) {
        Card(modifier = Modifier.fillMaxWidth().testTag("adminImageItem_${image.key}")) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AdminImageThumbnail(image.image, testTag = "adminImage_${image.key}")
                Text(
                    image.site.name,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Button(
                    onClick = onRemoveClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    ),
                    modifier = Modifier.width(150.dp).testTag("removeImage_${image.key}")
                ) {
                    Text("Remove")
                }
            }
        }
    }

    /**
     * Shows one image. Shows a message instead if the image can't be read.
     *
     * @param image the image's bytes
     * @param testTag the image's tag for UI testing
     */
    @Composable
    fun AdminImageThumbnail(image: Blob, testTag: String) {
        // Only decode the image again if it changes
        val bitmap = remember(image) {
            val bytes = image.toBytes()
            BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        }

        if (bitmap == null) {
            Box(
                modifier = Modifier.size(150.dp).testTag(testTag),
                contentAlignment = Alignment.Center
            ) {
                Text("Can't show image", fontSize = 12.sp)
            }
        } else {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Campsite image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(150.dp).testTag(testTag)
            )
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun AdminImageListScreenPreview() {
        // Fake image bytes, so the preview shows the "Can't show image" boxes
        val sites = listOf(
            Campsite(
                name = "Waterton Campground", ID = "site1",
                images = mutableListOf(Blob.fromBytes(byteArrayOf(1)), Blob.fromBytes(byteArrayOf(2)))
            ),
            Campsite(name = "Banff Hilltop", ID = "site2", images = mutableListOf(Blob.fromBytes(byteArrayOf(3))))
        )
        AdminImageListScreen(rememberNavController(), sites, onRemove = {})
    }
}
