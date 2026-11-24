package com.example.project_ballin

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.PropertyName
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
 * @property lotteryID the lotteryID of the lottery this notification was sent to
 */
class Notification (
    var content: String = "",
    var dateSent: Long = System.currentTimeMillis(),
    var ID: String = "",
    var recipientID: String = "",
    var lotteryID: String = ""
) {
    // getFormattedDate() {}
}