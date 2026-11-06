package com.example.project_ballin

import androidx.compose.runtime.mutableStateListOf
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
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

            snapshots?.documents?.forEach { document->
                val notification = document.toObject(Notification::class.java)
                if(notification != null){
                    _notifications.add(notification)
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

}