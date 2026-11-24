package com.example.project_ballin

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.PropertyName
import java.util.Date
import java.text.SimpleDateFormat
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
   // fun getFormattedDate() {}
}