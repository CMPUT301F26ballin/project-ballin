package com.example.project_ballin

import java.text.SimpleDateFormat
import java.util.Date
import kotlin.uuid.Uuid

/**
 * A class that stores information about a notification,
 *
 * Notifications have content, dateSent, and ID and a list
 * of recipients.
 *
 * @property _content the content of the notification
 * @property _dateSent the date the notification was sent
 * @property _ID the unique ID of the notification
 * @property _recipientIDs a list of Users Uuids, which are the recipients of the notification
 */
class Notification (
    private val _content: String,
    private val _dateSent: Date,
    private val _ID: Uuid,
    // private val _recipientIDs: List<Uuid>
) {

    val content: String get() = _content
    val ID: Uuid get() = _ID
    // val recipientIDs: List<Uuid> get() = _recipientIDs

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