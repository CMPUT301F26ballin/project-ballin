package com.example.project_ballin.entrant.entantui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.project_ballin.Rating
import com.example.project_ballin.entrant.Entrant
import com.example.project_ballin.reusableui.TopAppBar

class EntrantRateCampsite {

    /**
     * The screen where the entrant can give a rating to a campsite
     *
     * @param navController the navController Object
     * @param entrant the entrant Object
     * @param campsiteID the ID of the campsite being rated
     * @param onAdd the function that adds the rating to the database
     * @param noPreviousRatingGiven the boolean that checks if the entrant has already rated the campsite
     */
    @Composable
    fun EntrantGiveCampsiteRatingScreen(
        navController: NavController,
        entrant: Entrant,
        campsiteID: String,
        onAdd: (Rating) -> Unit,
        noPreviousRatingGiven: Boolean
    ) {
        var rating by remember { mutableStateOf(0) }
        var confirmRating by remember { mutableStateOf(false) }

        TopAppBar(
            navController,
            "Rating Input",
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(128.dp))

                if (!noPreviousRatingGiven) {
                    Text(
                        text = "Permission Denied",
                        fontSize = 24.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "You Have Already Given Rating",
                        fontSize = 24.sp
                    )
                } else {
                    Text(text = "Rate Campsite",fontSize = 24.sp)
                    Spacer(modifier = Modifier.height(34.dp))


                    Spacer(modifier = Modifier.height(34.dp))

                    Button(
                        onClick = { confirmRating = true }
                    ) {
                        Text("Confirm")
                    }
                }

            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun EntrantGiveCampsiteRatingnoPreviousRatingGivenPreview() {
        val navController = rememberNavController()

        EntrantRateCampsite().EntrantGiveCampsiteRatingScreen(
            navController,
            Entrant("1", "John", "Doe"),
            "123456789",
            onAdd = {},
            noPreviousRatingGiven = true
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun EntrantGiveCampsiteRatingPermissionDeniedPreview() {
        val navController = rememberNavController()

        EntrantRateCampsite().EntrantGiveCampsiteRatingScreen(
            navController,
            Entrant("1", "John", "Doe"),
            "123456789",
            onAdd = {},
            noPreviousRatingGiven = false
        )
    }


}