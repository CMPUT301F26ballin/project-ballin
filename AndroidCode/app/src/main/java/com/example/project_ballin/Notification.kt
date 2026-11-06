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
 * @property content the content of the notification
 * @property dateSent the date the notification was sent
 * @property ID the unique ID of the notification
 * @property recipientID a list of Users Uuids, which are the recipients of the notification
 */
class Notification (
    var content: String = "",
    var dateSent: Date = Date(),
    var ID: String = "",
    var recipientID: String = ""
) {

    /**
     * Returns the date sent in a readable format.
     * Date() in java gives unnecessary data.
     */
    fun getFormattedDate(): String {
        return SimpleDateFormat("dd/MM/yyyy").format(dateSent) // MM = month (mm would be minutes)
    }
}