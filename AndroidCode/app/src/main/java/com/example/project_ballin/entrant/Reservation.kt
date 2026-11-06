package com.example.project_ballin.entrant
/**
 * A singular reservation indicating a User's booking of a campsite in a
 * specific set of dates
 *
 * @param campsiteID the Uuid of the campsite being reserved
 * @param dates a list of the dates that have been reserved for the camp
 *
 * */
data class Reservation(
    val campsiteID: String?,
    val dates: List<Long>?,
)