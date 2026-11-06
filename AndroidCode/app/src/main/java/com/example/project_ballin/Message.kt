package com.example.project_ballin

import java.util.Date
import java.text.SimpleDateFormat
import kotlin.uuid.Uuid

/**
 * A class that stores information shows information
 * about a message sent by an owner to an entrant.
 *
 * Messages have an ownerID, entrantID, content, and dateSent.
 *
 * @property ownerID the ID of the owner of the message.
 * @property entrantID the ID of the entrant which owner sent message to.
 * @property content the body/content of the message
 * @property dateSent the date the message was sent
 * @property ID the unique ID of the message
 */
class Message (
    var ownerID: String = "",
    var entrantID: String = "",
    var content: String = "",
    var dateSent: Date = Date(),
    var ID: String = Uuid.random().toString()
) {

    /**
     * Returns the date sent in a readable format.
     * Date() in java gives unnecessary data.
     */
    fun getFormattedDate(): String {
        return SimpleDateFormat("dd/mm/yyyy").format(dateSent)
    }
}