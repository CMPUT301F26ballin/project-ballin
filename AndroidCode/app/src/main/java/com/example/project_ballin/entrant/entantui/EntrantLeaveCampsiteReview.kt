package com.example.project_ballin.entrant.entantui

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.project_ballin.Rating
import com.example.project_ballin.RatingList
import com.example.project_ballin.Review
import com.example.project_ballin.ReviewList
import com.example.project_ballin.entrant.Entrant
import com.example.project_ballin.reusableui.GeneralReportButton
import com.example.project_ballin.reusableui.TopAppBar

class EntrantLeaveCampsiteReview {

    /**
     * The screen where the entrant can give a review to a campsite
     *
     * @param navController the navController Object
     * @param entrant the entrant Object
     * @param campsiteID the ID of the campsite being reviewed
     * @param onAdd the function that adds the review to the database
     * @param noPreviousReviewGiven the boolean that checks if the entrant has already reviewed the campsite
     */
    @SuppressLint("NotConstructor")
    @Composable
    fun EntrantGiveCampsiteReviewScreen(
        navController: NavController,
        entrant: Entrant,
        campsiteID: String,
        reviewList: ReviewList,
        ratingList: RatingList
    ){
        var reviewContents by remember { mutableStateOf("") }
        var confirmReview  by remember { mutableStateOf(false) }
        var noPreviousReviewGiven by remember { mutableStateOf(true) }

        var rating by remember { mutableStateOf(0) }
        var confirmRating by remember { mutableStateOf(false) }


        if(entrant.userID in reviewList.getReviewsFromCampsite(campsiteID).map { it.reviewer }){
            noPreviousReviewGiven = false
        }

        TopAppBar(
            navController,
            "Review Input",
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                    ){
                        if (!noPreviousReviewGiven) {
                            PastReviewCard(
                                ratingList.getCampsiteRatingFromEntrant(entrant.userID, campsiteID)!!,
                                reviewList.getCampsiteReviewFromEntrant(entrant.userID, campsiteID)!!,                        )
                        } else {

                            Text(
                                text = "Review campsite:",
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.padding(4.dp, 0.dp)
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                for (i in 1..5) {
                                    RatingButton(
                                        number = i,
                                        onSelectedRating = { rating = it },
                                        change = i <= rating,
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            OutlinedTextField(
                                value = reviewContents,
                                onValueChange = { newContents: String ->
                                    if (newContents.length <= 500) {
                                        reviewContents = newContents
                                    }
                                },
                                label = { Text("Input your review here...") },
                                minLines = 1,
                                modifier = Modifier
                                    .fillMaxWidth()

                            )

                            HorizontalDivider(modifier = Modifier.padding(0.dp, 16.dp))

                            Button(
                                onClick = {
                                    confirmReview = true
                                    confirmRating = true
                                },
                                enabled = reviewContents.isNotBlank() && rating > 0,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Confirm")
                            }
                        }

                        if (reviewContents.isBlank() && confirmReview && noPreviousReviewGiven) {
                            Text(
                                text = "Review must not be empty",
                                color = Color.Red
                            )
                        }
                    }

                    if (reviewContents.isNotBlank() && confirmReview && rating > 0 && confirmRating && noPreviousReviewGiven) {
                        ConfirmActionDialog().ConfirmAction(
                            message = "Are you sure you want to leave this review?\n",
                            confirm = { confirmed ->
                                if (confirmed) {
                                    val newRating = Rating(entrant.userID, rating, campsiteID)
                                    ratingList.addRating(newRating)
                                    navController.popBackStack()

                                    val newReview = Review(entrant.userID, reviewContents, campsiteID)
                                    reviewList.addReview(newReview)
                                    navController.popBackStack()
                                }
                                rating = 0
                                confirmRating = false

                                confirmReview = false
                                reviewContents = ""
                            }
                        )
                    }
                }

                // Lets the entrant report the campsite they are reviewing
                ReportCampsiteButton(navController, campsiteID)
            }
        }
    }

    /**
     * A "Report" button that lets the entrant report the campsite
     * they are reviewing to the admins.
     *
     * @param navController the navController Object
     * @param campsiteID the ID of the campsite being reported
     */
    @Composable
    fun ReportCampsiteButton(navController: NavController, campsiteID: String) {
        GeneralReportButton().ReportButton(
            subjectReport = "Campsite",
            clickedID = campsiteID,
            navController = navController,
            label = "Report",
            testTag = "reviewReportButton",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp)
        )
    }

    /**
     * A button that allows the entrant to give a rating to a campsite
     * Numbers range from 1-5, with 1 being the worst, to 5 being the best
     *
     * @param number the number of the rating
     * @param onSelectedRating the function that is called when the button is clicked
     * @param modifier the modifier for the button
     * @param change the boolean that determines if the icon button should be big or small, and diff color,
     * it depends on whatever rating the entrant has given
     *
     * Source Used: https://brahimoubbad.com/posts/how-to-use-material-icons-in-jetpack-compose/
     * Title: How to use Material Icons in Jetpack Compose - (A Developer's Complete Guide 2026)
     * Author: Brahim Oubbad
     * Published: May 22, 2025
     */
    @Composable
    fun RatingButton(
        number: Int,
        onSelectedRating: (Int) -> Unit,
        change: Boolean,
        modifier: Modifier = Modifier
    ) {
        val size = 48.dp

        Box(
            modifier = modifier
                .size(size)
                .clip(CircleShape)
                .clickable { onSelectedRating(number) },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "Star Icon",
                modifier = Modifier.size(if (change) size else size * 0.5f),
                tint = if (change) MaterialTheme.colorScheme.primary else Color.Gray,

            )
        }
    }

    @Composable
    fun PastReviewCard(rating: Rating, review: Review, modifier: Modifier = Modifier){
        val size = 32.dp

        Text(
            text = "You have already reviewed this campsite.",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(3.dp, 0.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            for (i in 1..(rating.ratingNumber)) {
                Box(
                    modifier = modifier
                        .size(size)
                        .clip(CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "Star Icon",
                        modifier = Modifier.size(size),
                        tint = Color.Gray,
                    )
                }
            }

            Text(
                text = "/5",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.Bottom)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Review contents: ${review.content}",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray,
            modifier = Modifier.padding(3.dp, 0.dp)
        )
    }
}