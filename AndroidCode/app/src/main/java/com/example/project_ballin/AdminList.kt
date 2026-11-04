package com.example.project_ballin

import androidx.compose.runtime.mutableStateListOf
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlin.uuid.Uuid

/**
 * The data class for an admin.
 * Admins have a name, phone number, email, device ID, profile picture, and user ID.
 *
 * @property name the name of the admin
 * @property phoneNum the phone number of the admin
 * @property email the email of the admin
 * @property deviceId the device ID of the admin
 * @property profilePic the profile picture of the admin
 * @property userID the user ID of the admin
 * */
data class AdminData(
    val name: String = "",
    val lastname: String = "",
    val phoneNum: String = "",
    val email: String = "",
    val deviceId: String = "",
    val profilePic: String = "",
    val userID: String = "",
)

fun Admin.toData() = AdminData(
    firstName,
    lastName,
    phoneNum,
    email,
    deviceId,
    profilePic,
    userID.toString(),
)

fun AdminData.toAdmin() = Admin(
    name,
    lastName = "",
    phoneNum,
    email = email,
    deviceId= deviceId,
    profilePic = profilePic,
    userID = Uuid.parse(userID),
)

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
        adminsRef.document(admin.userID.toString()).set(admin.toData())
    }

    /**
     * updates a given admin with new information
     *
     * @param admin The admin object being updated
     * */
    fun updateAdmin(admin: Admin) {
        adminsRef.document(admin.userID.toString()).set(admin.toData())
    }

    /**
     * deletes a given admin from the database
     *
     * @param admin The admin being deleted
     * */
    fun deleteAdmin(admin: Admin) {
        adminsRef.document(admin.userID.toString()).delete()
    }

    /**
     * gets an admin from the database
     *
     * @param id the Uuid of the admin
     */
    fun getAdmin(id: Uuid): Admin? = _admins.find {
        it.userID == id
    }
}


