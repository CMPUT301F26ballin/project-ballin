package com.example.project_ballin

import androidx.compose.runtime.mutableStateListOf
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

/**
 * A list that that stores all the reviews left by users
 */
open class ReviewList {
    private val _reviews = mutableStateListOf<Review>()

    private val db = Firebase.firestore

    /**
     * The path in the database for the user reviews and the
     * snapshot listener that updates the database everytime
     * a new review is added.
     *
     */
    private val reviewsRef = db.collection("Reviews")

    init {
        reviewsRef.addSnapshotListener { snapshots, error ->
            if (error != null) {
                return@addSnapshotListener
            }

            _reviews.clear()

            snapshots?.documents?.forEach { document ->
                val review = document.toObject(Review::class.java)
                if (review != null) {
                    _reviews.add(review)
                }
            }
        }
    }

    val reviews: List<Review>
        get() = _reviews

    /**
     * Adds a review to the database
     *
     * @param review The review being added
     */
    fun addReview(review: Review) {
        reviewsRef.document(review.ID).set(review)
    }

    fun getAllReviews(): List<Review> {
        return reviews
    }

    /**
     * Gets a review from the database with specific ID
     *
     * @param ID the ID of the review to get
     */
    fun getReview(ID: String): Review? {
        return _reviews.find { it.ID == ID }
    }

    /**
     * Gets a review from the database from specific campsites
     *
     * @param campsiteID the ID of the campsite the review is for
     */
    fun getReviewsFromCampsite(campsiteID: String): List<Review> {
        return _reviews.filter { it.campsiteID == campsiteID }
    }

    /**
     * Deletes a review from the database
     *
     * @param ID the ID of the review being deleted
     */
    fun deleteReview(ID: String) {
        reviewsRef.document(ID).delete()
    }

    fun getCampsiteReviewFromEntrant(entrantID: String, campsiteID: String): Review? {
        return _reviews.find { it.reviewer == entrantID && it.campsiteID == campsiteID }
    }

}