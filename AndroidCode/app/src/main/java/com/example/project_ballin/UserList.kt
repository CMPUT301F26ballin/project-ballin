package com.example.project_ballin

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import kotlin.uuid.Uuid

/**
 * The list that stores all of the users added to the app.
 *
 *
 *
 * */

class UserList() {

    private val _users = mutableStateListOf<User>()

    private val db = Firebase.firestore

    /**
     * The path in the database for the campsites and the
     * snapshot listener that updates the database everytime
     * a new site is added.
     * */

    private val usersRef = db.collection("Users")
    init{
        usersRef.addSnapshotListener { snapshots, error ->
            if(error != null){
                return@addSnapshotListener
            }

            _users.clear()

            snapshots?.documents?.forEach { document->
                val user = document.toObject(User::class.java)
                if(user != null){
                    _users.add(user)
                }
            }
        }
    }

    val users: List<User>
        get() = _users

    /**
     * Adds a campsite to the database
     *
     * @param user The user object being added
     */
    fun addUser(user: User){
        //THIS MAY CHANGE BASED ON IF WE IMPLEMENT CAMPSITE IDs
        usersRef.document(user.userID.toString()).set(user)
    }

    /**
     * updates a given user with new information
     *
     * @param oldUser The user object being updated
     * @param updatedUser The object storing the new user data
     * */

    fun updateUser(oldUser: User, updatedUser: User){
        //THIS MAY CHANGE BASED ON IF WE IMPLEMENT CAMPSITE IDs
        usersRef.document(oldUser.userID.toString()).set(updatedUser)
    }

    /**
     * deletes a given user from the database
     *
     * @param user The user being deleted
     * */


    // Would need to further handle how this affects owners/entrants
    fun deleteUser(user: User) {
        usersRef.document(user.userID.toString()).delete()
    }

}