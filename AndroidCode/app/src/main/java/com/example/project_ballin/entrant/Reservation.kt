package com.example.project_ballin.entrant

import kotlin.uuid.Uuid

/**
 * A class that stores all the information of a reservation
 * Does not store much right now
 *
 * @property campsiteID the ID of the campsite that the reservation is for
 * @property dates the dates that the reservation is for
 */
data class Reservation(
    val campsiteID: String? = "",
    val dates: List<Long>? = listOf(),
    val UserID: String? = "",
    var cancelled: Boolean = false,
    val resID: String = ""
)