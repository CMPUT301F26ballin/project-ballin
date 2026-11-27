package com.example.project_ballin

import androidx.compose.runtime.mutableStateListOf
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlin.uuid.Uuid

/**
 * A list that that stores all the messages
 * sent by owners to entrants. This connects
 * to a Firestore database
 */
class MessageList {
    private val _messages = mutableStateListOf<Message>()

    private val db = Firebase.firestore

    /**
     * The path in the database for the messages and the
     * snapshot listener that updates the database everytime
     * a new message is added.
     *
     */

    private val messagesRef = db.collection("Messages")
    init{
        messagesRef.addSnapshotListener { snapshots, error ->
            if(error != null){
                return@addSnapshotListener
            }

            _messages.clear()

            snapshots?.documents?.forEach { document->
                try {
                    val message = document.toObject(Message::class.java)
                    if(message != null){
                        _messages.add(message)
                    }
                } catch (e: Exception) {
                    // just skip messages that can't be parsed
                }
            }
        }
    }

    val messages: List<Message>
        get() = _messages

    /**
     * Adds a message to the database
     *
     * @param message The message being added
     */
    fun addMessage(message: Message){
        messagesRef.document(message.ID).set(message)
    }

    /**
     * Deletes a message from the database
     *
     * @param message The message being deleted
     */
    fun deleteMessage(message: Message) {
        messagesRef.document(message.ID).delete()
    }

    fun getAllMessages(): List<Message>{
        return messages
    }

    /**
     * Gets a message from the database with specific ID
     *
     * @param ID the ID of the message to get
     * @return a message object with the given ID
     */
    fun getMessage(ID: String): Message? =
         _messages.find { it.ID == ID }

    /**
     * A Method that gets all messages between two users
     *
     * @param firstID the ID of the first user
     * @param secondID the ID of the second user
     * @return a list of message objects between the two individuals
     *
     * */

    fun getMessagesBetween(firstID: String, secondID: String): List<Message>{
        val returnList = mutableListOf<Message>()
        messages.forEach { message ->
            if(message.senderID == firstID && message.recipientID == secondID){
                returnList.add(message)
            }
            else if(message.senderID == secondID && message.recipientID == firstID){
                returnList.add(message)
            }

        }
        returnList.sortByDescending { message -> message.dateSent }
        returnList.reverse()
        return returnList
    }

}