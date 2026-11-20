package com.example.project_ballin

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
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
    fun GoogleMapScreen(campsite: Campsite,editing: Boolean){

        var isMapLoaded by remember { mutableStateOf(false) }

        var cursite by remember { mutableStateOf(campsite) }

        val cameraPositionState = rememberCameraPositionState{
            position = CameraPosition(cursite.location, 5f, 0f, 0f)
        }

        Box(modifier = Modifier.padding(16.dp)){
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                onMapLoaded = {isMapLoaded = true},
                onMapClick = {if(editing){cursite.location = it}},
                cameraPositionState = cameraPositionState
                ){
                Marker(
                    state = rememberMarkerState(position = cursite.location),
                    tag = "campsite"
                )
            }
        }

    }

}