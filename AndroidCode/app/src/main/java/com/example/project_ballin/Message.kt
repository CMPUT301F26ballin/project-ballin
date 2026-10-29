package com.example.project_ballin

import java.util.Date
import java.text.SimpleDateFormat

/**
 * A class that stores information shows information
 * about a message sent by an owner to an entrant.
 *
 * Messages have an ownerID, entrantID, content, and dateSent.
 *
 * @property _ownerID the ID of the owner of the message.
 * @property _entrantID the ID of the entrant which owner sent message to.
 * @property _content the body/content of the message
 * @property _dateSent the date the message was sent
 * @property _ID the unique ID of the message
 */
class Message (
    private val _ownerID: Int,
    private val _entrantID: Int,
    private val _content: String,
    private val _dateSent: Date,
    private val _ID: Int
) {
    // I did this below because then it creates
    // a public-read only for private attributes
    // it's basically a getter() for above
    val ownerID: Int get() = _ownerID
    val entrantID: Int get() = _entrantID
    val content: String get() = _content
    val ID: Int get() = _ID

    val sdf = SimpleDateFormat("dd/mm/yyyy")
    val sDate = sdf.format(_dateSent)

    /**
     * Returns the date sent in a readable format.
     * Date() in java gives unnecessary data.
     */
    fun getFormattedDate(): String {
        return sDate
    }
}