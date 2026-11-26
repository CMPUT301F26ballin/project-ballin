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

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        for (i in 1..5) {
                            RatingButton(
                                i,
                                onSelectedRating = {rating = it},
                                modifier = Modifier.weight(1f),
                                change = (i <= rating)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(34.dp))

                    Button(
                        onClick = { confirmRating = true }
                    ) {
                        Text("Confirm")
                    }
                }

                if (rating > 0 && confirmRating && noPreviousRatingGiven) {
                    ConfirmActionDialog().ConfirmAction(
                        message = "Are you sure you want to rate campsite ${rating}?",
                        confirm = { confirmed ->
                            if (confirmed) {
                                val newRating = Rating(entrant.userID, rating, campsiteID)
                                onAdd(newRating)
                                navController.popBackStack()
                            }
                            rating = 0
                            confirmRating = false
                        }
                    )
                }

            }
        }
    }

    /**
     * A button that allows the entrant to give a rating to a campsite
     * Number of stars indicate the rating, 1-star being the worst and 5-star being the best
     *
     * @param number the number of the rating
     * @param onSelectedRating the function that is called when the button is clicked
     * @param modifier the modifier for the button
     * @param change the boolean that determines if the icon button should be big or small, and diff color,
     * it depends on whatever rating the entrant has given
     */
    @Composable
    fun RatingButton(
        number: Int,
        onSelectedRating: (Int) -> Unit,
        modifier: Modifier = Modifier,
        change: Boolean
    ) {
        val iconSize = if (change) 68.dp else 24.dp

        /**
         * Source Used: https://brahimoubbad.com/posts/how-to-use-material-icons-in-jetpack-compose/
         * Title: How to use Material Icons in Jetpack Compose - (A Developer's Complete Guide 2026)
         * Author: Brahim Oubbad
         * Published: May 22, 2025
         */
        IconButton(
            onClick = {
                onSelectedRating(number)
            },
            modifier = modifier
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Star Icon",
                modifier = Modifier.size(iconSize),
                tint = if (change) MaterialTheme.colorScheme.primary else Color.Gray
            )
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

    @Preview(showBackground = true)
    @Composable
    fun Entrant3StarRatingPreview() {
        Row{
            for (i in 1..5) {
                RatingButton(
                    i,
                    onSelectedRating = {},
                    change = (i <= 3)
                )
            }
        }
    }
}