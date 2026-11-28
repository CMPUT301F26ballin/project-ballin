package com.example.project_ballin

import java.text.DateFormat
import java.util.Date
import java.util.Locale
import kotlin.uuid.Uuid


/**
 * A class that stores information shows information
 * about a message sent by an owner to an entrant.
 *
 * Messages have an ownerID, entrantID, content, and dateSent.
 *
 * @property senderID the ID of the user who sent the message.
 * @property recipientID the ID of the user which sender sent message to.
 * @property content the body/content of the message
 * @property dateSent the date the message was sent
 * @property ID the unique ID of the message
 */
class Message (
    var senderID: String = "",
    var recipientID: String = "",
    var content: String = "",
    var dateSent: Long = System.currentTimeMillis(),
    var ID: String = Uuid.random().toString()
) {
    /**
     * Gets the date of the message in a readable format.
     *
     * Got code from EntrantConfirmBooking.kt
     *
     * @param dateFormat the format of the date
     * @return the date of the message in a readable format
     */
    fun getFormattedDate(dateFormat: Int =  DateFormat.MEDIUM): String {
        val df = DateFormat.getDateInstance(dateFormat, Locale.getDefault())
        return df.format(Date(this.dateSent))
    }
}