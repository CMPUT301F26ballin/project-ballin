package com.example.project_ballin

import kotlin.uuid.Uuid

/**
 * A class that stores information on a review of a campsite
 *
 * The review class should have a reviewer, the contents of review,
 * and the associated campsite they wish to leave a review on
 *
 * @property reviewer the ID of the user who left the review
 * @property content the contents of the review
 * @property campsiteID the ID of the campsite the review is for
 */
class Review (
    var reviewer: String = "",
    var content: String = "",
    var campsiteID: String = "",
    var ID: String = Uuid.random().toString()
)
