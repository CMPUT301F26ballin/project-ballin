package com.example.project_ballin

import androidx.compose.runtime.mutableStateListOf
import com.example.project_ballin.admin.Admin
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

/**
 * The Admin list class
 * Stores admin profiles in firebase
 *
 * @property _admins the list of admins
 * @property db the database
 * @property adminsRef the reference to the admins collection
 * @property admins the list of admins
 *
 */
class AdminList{
    private val _admins = mutableStateListOf<Admin>()

    private val db = Firebase.firestore

    /**
     * The path in the database for the admins and the
     * snapshot listener that updates the database everytime
     * a new admin is added.
     * */
    private val adminsRef = db.collection("Admins")
    init{
        adminsRef.addSnapshotListener { snapshots, error ->
            if(error != null){
                return@addSnapshotListener
            }

            _admins.clear()

            snapshots?.documents?.forEach { document->
                val admin = document.toObject(Admin::class.java)
                if(admin != null){
                    _admins.add(admin)
                }
            }
        }
    }

    val admins: List<Admin>
        get() = _admins

    /**
     * Adds an admin to the database
     *
     * @param admin The admin object being added
     */
    fun addAdmin(admin: Admin) {
        adminsRef.document(admin.userID).set(admin)
    }

    /**
     * updates a given admin with new information
     *
     * @param admin The admin object being updated
     * */
    fun updateAdmin(admin: Admin) {
        adminsRef.document(admin.userID).set(admin)
    }

    /**
     * deletes a given admin from the database
     *
     * @param admin The admin being deleted
     * */
    fun deleteAdmin(admin: Admin) {
        adminsRef.document(admin.userID).delete()
    }

    /**
     * gets an admin from the database
     *
     * @param id the Uuid of the admin
     */
    fun getAdmin(id: String): Admin? = _admins.find {
        it.userID == id
    }
}


