package com.example.project_ballin

import androidx.compose.runtime.mutableStateListOf
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

/**
 * A list that that stores all the ratings made by users
 */
open class RatingList {
    private val _ratings = mutableStateListOf<Rating>()

    private val db = Firebase.firestore

    /**
     * The path in the database for the user ratings and the
     * snapshot listener that updates the database everytime
     * a new rating is added.
     *
     */
    private val ratingsRef = db.collection("Ratings")

    init {
        ratingsRef.addSnapshotListener { snapshots, error ->
            if (error != null) {
                return@addSnapshotListener
            }

            _ratings.clear()

            snapshots?.documents?.forEach { document ->
                val rating = document.toObject(Rating::class.java)
                if (rating != null) {
                    _ratings.add(rating)
                }
            }
        }
    }

    val ratings: List<Rating>
        get() = _ratings

    /**
     * Adds a rating to the database
     *
     * @param rating The rating being added
     */
    fun addRating(rating: Rating) {
        ratingsRef.document(rating.ID).set(rating)
    }

    fun getAllRatings(): List<Rating> {
        return ratings
    }

    /**
     * Gets a rating from the database with specific ID
     *
     * @param ID the ID of the rating to get
     */
    fun getRating(ID: String): Rating? {
        return _ratings.find { it.ID == ID }
    }

    /**
     * Gets all rating from the database from specific campsites
     *
     * @param campsiteID the ID of the campsite the rating is for
     */
    fun getRatingsFromCampsite(campsiteID: String): List<Rating> {
        return _ratings.filter { it.campsiteID == campsiteID }
    }

    /**
     * Deletes a rating from the database
     *
     * @param ID the ID of the rating being deleted
     */
    fun deleteRating(ID: String) {
        ratingsRef.document(ID).delete()
    }

    fun getCampsiteRatingFromEntrant(entrantID: String, campsiteID: String): Rating? {
        return _ratings.find { it.reviewer == entrantID && it.campsiteID == campsiteID }
    }

}

