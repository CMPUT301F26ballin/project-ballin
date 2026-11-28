package com.example.project_ballin

import java.text.DateFormat
import java.util.Date
import java.util.Locale
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
 * @property lotteryID the lotteryID of the lottery this notification was sent to
 */
class Notification (
    var content: String = "",
    var dateSent: Long = System.currentTimeMillis(),
    var ID: String = Uuid.random().toString(),
    var recipientID: String = "",
    var lotteryID: String = ""
) {
    /**
     * Gets the date of the message in a readable format.
     */
    fun getFormattedDate(dateFormat: Int =  DateFormat.MEDIUM): String {
        val df = DateFormat.getDateInstance(dateFormat, Locale.getDefault())
        return df.format(Date(this.dateSent))
    }
}