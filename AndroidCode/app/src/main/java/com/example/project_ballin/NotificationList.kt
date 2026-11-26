package com.example.project_ballin

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import java.util.Date
import kotlin.uuid.Uuid

/**
 * A list that that stores all the notifications.
 * This connects to a Firestore database.
 */
class NotificationList {
    private val _notifications = mutableStateListOf<Notification>()

    private val db = Firebase.firestore

    /**
     * The path in the database for the notifications and the
     * snapshot listener that updates the database everytime
     * a new notification is added.
     *
     */

    private val notificationsRef = db.collection("Notifications")

    init{
        notificationsRef.addSnapshotListener { snapshots, error ->
            if(error != null){
                return@addSnapshotListener
            }

            _notifications.clear()

            snapshots?.documents?.forEach { document ->
                try {
                    val notification = document.toObject(Notification::class.java)
                    if(notification != null){
                        _notifications.add(notification)
                    }
                } catch (e: Exception) {
                    // just skip notifications that can't be parsed
                }
            }
        }
    }

    val notifications: List<Notification>
        get() = _notifications


    /**
     * Adds a notification to the database
     *
     * @param notification The notification being added
     */
    fun addNotification(notification: Notification){
        notificationsRef.document(notification.ID).set(notification)
    }

    /**
     * Deletes a notification from the database
     *
     * @param notification The notification being deleted
     */
    fun deleteNotification(notification: Notification) {
        notificationsRef.document(notification.ID).delete()
    }

    fun getAllNotifications(): List<Notification>{
        return notifications
    }

    /**
     * Gets a notification from the database with specific ID
     *
     * @param ID the ID of the notification to get
     * @return a notification object with the given ID
     */
    fun getNotification(ID: String): Notification? =
        _notifications.find { it.ID == ID }

    /**
     * Sends a notification to a list of entrants
     *
     * @param content the message to send
     * @param lotteryID
     * @param entrantIDs the list of entrant IDs to send to
     * @return successfully sent or not
     */
    fun sendToEntrants(content: String, lotteryID: String, entrantIDs: List<String>): Boolean {
        if (content.isBlank()) return false
        val now = System.currentTimeMillis()
        if (entrantIDs.isEmpty()) {
            // if theres no entrants, still send a message to no one
            // so the sent message still in the owners records
            addNotification(
                Notification(
                    content = content.trim(),
                    dateSent = now,
                    ID = Uuid.random().toString(),
                    lotteryID = lotteryID,
                    recipientID = ""
                )
            )
        } else {
            entrantIDs.forEach { entrantID ->
                addNotification(
                    Notification(
                        content = content.trim(),
                        dateSent = now,
                        ID = Uuid.random().toString(),
                        lotteryID = lotteryID,
                        recipientID = entrantID
                    )
                )
            }
        }
        return true
    }

    fun getNotificationsByLottery(lotteryID: String) : List<Notification> {
        return _notifications.filter { it.lotteryID == lotteryID }
    }
}