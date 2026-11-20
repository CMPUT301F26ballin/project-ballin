package com.example.project_ballin

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

/**
 * A general composable screen that can be inserted into any place that needs a Google Maps view.
 * The screen can take in a campsite object and display its location on the screen.
 *
 *
 * */

class GoogleMapScreen {
    /**
     * The screen composable function that displays the map. Shows the current location of a given campsite
     * and allows for editing that location (in scenarios where that is needed.)
     *
     *
     * */
    @SuppressLint("NotConstructor")
    @Composable
    fun GoogleMapScreen(location: LatLng, editing: Boolean, onNewLocation:(LatLng) -> Unit){

        var isMapLoaded by remember { mutableStateOf(false) }

        var curLoc by remember { mutableStateOf(location) }

        val cameraPositionState = rememberCameraPositionState{
            position = CameraPosition(curLoc, 5f, 0f, 0f)
        }

        Column(modifier = Modifier.fillMaxSize()){
            Box(modifier = Modifier.padding(16.dp)) {
                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    onMapLoaded = { isMapLoaded = true },
                    onMapClick = {
                        if (editing) {
                            curLoc = it
                        }
                    },
                    cameraPositionState = cameraPositionState
                ) {
                    Marker(
                        state = rememberMarkerState(position = curLoc),
                        tag = "campsite"
                    )
                }
            }

            Button(onClick = {onNewLocation(curLoc)},
                modifier = Modifier.fillMaxWidth().padding(16.dp)){
                Text("Close Map")

            }
        }

    }

}