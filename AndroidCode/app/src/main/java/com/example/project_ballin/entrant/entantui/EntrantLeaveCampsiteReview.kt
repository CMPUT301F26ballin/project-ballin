package com.example.project_ballin.entrant.entantui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
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
import com.example.project_ballin.Review
import com.example.project_ballin.ReviewList
import com.example.project_ballin.entrant.Entrant
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
    ){
        var reviewContents by remember { mutableStateOf("") }
        var confirmReview  by remember { mutableStateOf(false) }
        var noPreviousReviewGiven by remember { mutableStateOf(true) }

        if(entrant.userID in reviewList.getReviewsFromCampsite(campsiteID).map { it.reviewer }){
            noPreviousReviewGiven = false
        }

        TopAppBar(
            navController,
            "Review Input",
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(128.dp))
                if (noPreviousReviewGiven) {
                    Text(
                        text = "Permission Denied",
                        fontSize = 24.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "You Have Already Given Review",
                        fontSize = 24.sp
                    )
                } else {
                    Text(
                        text = "Review Campsite",
                        fontSize = 24.sp
                    )
                    Spacer(modifier = Modifier.height(34.dp))

                    OutlinedTextField(
                        value = reviewContents,
                        onValueChange = { reviewContents = it },
                        label = { Text("Text Review Here") }
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { confirmReview = true }
                    ) {
                        Text("Confirm")
                    }
                    Spacer(modifier = Modifier.height(34.dp))
                }

                if (reviewContents.isBlank() && confirmReview && noPreviousReviewGiven) {
                    Text(
                        text = "Review must not be empty",
                        color = Color.Red
                    )
                }

                if (reviewContents.isNotBlank() && confirmReview && noPreviousReviewGiven) {
                    ConfirmActionDialog().ConfirmAction(
                        message = "Are you sure you want to leave this review?",
                        confirm = { confirmed ->
                            if (confirmed) {
                                val newReview = Review(entrant.userID, reviewContents, campsiteID)
                                reviewList.addReview(newReview)
                                navController.popBackStack()
                            }
                            confirmReview = false
                            reviewContents = ""
                        }
                    )
                }
            }
        }
    }
}