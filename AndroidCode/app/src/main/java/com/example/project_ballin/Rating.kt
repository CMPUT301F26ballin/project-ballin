package com.example.project_ballin

import kotlin.uuid.Uuid

/**
 * A class that holds information of a rating that user/entrant
 * did for a certain campsite
 *
 * @property ratingNumber the rating given by the user/entrant
 * @property campsiteID the ID of the campsite
 * @property reviewer the ID of the user/entrant who gave the rating
 * @property ID the unique ID of the rating
 */
class Rating (
    var reviewer: String = "",
    var ratingNumber: Double = 0.0,
    var campsiteID: String = "",
    var ID: String = Uuid.random().toString()
)
