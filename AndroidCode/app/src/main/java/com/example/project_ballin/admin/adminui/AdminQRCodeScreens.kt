package com.example.project_ballin

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.project_ballin.reusableui.TopAppBar
import com.google.firebase.firestore.Blob

/**
 * The admin's screen for browsing every campsite's QR code (US 03.04.04).
 * A campsite gets its QR code when the owner creates it (US 02.02.01).
 * Tapping a QR code opens that campsite's details.
 */
class AdminQRCodeScreens {

    val adminSearch = AdminSearch()

    /**
     * Searches a campsite by name, location, campsite ID, and owner ID.
     *
     * @param query the search text
     * @return true if the campsite matches the search
     */
    fun Campsite.matchesSearch(query: String): Boolean =
        adminSearch.matchesQuery(query, name, locationName, ID, ownerID)

    /**
     * Admin screen showing the QR code of every campsite.
     *
     * @param navController the app's navController
     * @param campsites every campsite, to show the QR codes of
     */
    @Composable
    fun AdminQRCodeListScreen(navController: NavController, campsites: List<Campsite>) {
        TopAppBar(navController, "QR Codes") {
            AdminQRCodeListView(
                campsites = campsites,
                onCampsiteClick = { site ->
                    navController.navigate("AdminCampsiteDetail/${site.ID}")
                }
            )
        }
    }

    /**
     * The QR code grid itself (no top bar), so it can be tested on its own.
     *
     * @param campsites the campsites to show the QR codes of
     * @param onCampsiteClick runs when a QR code is tapped
     */
    @Composable
    fun AdminQRCodeListView(campsites: List<Campsite>, onCampsiteClick: (Campsite) -> Unit) {
        // What the admin typed in the search box (kept when the screen rotates)
        var query by rememberSaveable { mutableStateOf("") }
        val results = campsites.filter { it.matchesSearch(query) }

        if (campsites.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("There are no campsites yet.", modifier = Modifier.testTag("noQRCodes"))
            }
        } else {
            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                adminSearch.AdminSearchField(
                    query,
                    onQueryChange = { query = it },
                    placeholder = "Search QR codes"
                )

                if (results.isEmpty()) {
                    Text(
                        "No campsites match \"${query.trim()}\".",
                        modifier = Modifier.padding(16.dp).testTag("noSearchResults")
                    )
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 160.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxSize().padding(top = 8.dp).testTag("adminQRCodeGrid")
                    ) {
                        items(results, key = { it.ID }) { site ->
                            AdminQRCodeItem(site, onClick = { onCampsiteClick(site) })
                        }
                    }
                }
            }
        }
    }

    /**
     * One QR code in the grid, with its campsite's name and location.
     *
     * @param site the campsite the QR code belongs to
     * @param onClick runs when the QR code is tapped
     */
    @Composable
    fun AdminQRCodeItem(site: Campsite, onClick: () -> Unit) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .testTag("adminQRCodeItem_${site.ID}"),
            colors = CardDefaults.cardColors()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                QRCodeImage(site.qrCode, testTag = "adminQRCode_${site.ID}")
                Text(
                    site.name,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 4.dp),
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    site.locationName,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }

    /**
     * Shows a campsite's QR code. Shows a message instead if the campsite
     * has no QR code (e.g. it was made before QR codes were added) or it can't be read.
     *
     * @param qrCode the QR code's image, or null if there isn't one
     * @param testTag the QR code's tag for UI testing
     * @param size how big to draw the QR code
     */
    @Composable
    fun QRCodeImage(qrCode: Blob?, testTag: String, size: Dp = 150.dp) {
        // Only decode the image again if it changes
        val bitmap = remember(qrCode) {
            qrCode?.toBytes()?.let { bytes -> BitmapFactory.decodeByteArray(bytes, 0, bytes.size) }
        }

        if (bitmap == null) {
            Box(
                modifier = Modifier.size(size).testTag(testTag),
                contentAlignment = Alignment.Center
            ) {
                Text(if (qrCode == null) "No QR code" else "Can't show QR code", fontSize = 12.sp)
            }
        } else {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "QR code",
                modifier = Modifier.size(size).testTag(testTag)
            )
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun AdminQRCodeListScreenPreview() {
        val sites = listOf(
            Campsite(
                name = "Waterton Campground", ID = "site1", locationName = "Waterton Lakes",
                qrCode = QRCodeGenerator().encodeQRBlobFromString("site1")
            ),
            Campsite(name = "Banff Hilltop", ID = "site2", locationName = "Banff") // no QR code
        )
        AdminQRCodeListScreen(rememberNavController(), sites)
    }
}
