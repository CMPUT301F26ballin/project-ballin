package com.example.project_ballin

import androidx.compose.runtime.mutableStateListOf
import com.example.project_ballin.owner.Owner
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlin.uuid.Uuid

/**
 * The data class for an owner.
 * Owners have a name, phone number, email, device ID, profile picture, and user ID.
 *
 * @property name the name of the owner
 * @property phoneNum the phone number of the owner
 * @property email the email of the owner
 * @property deviceId the device ID of the owner
 * @property profilePic the profile picture of the owner
 * @property userID the user ID of the owner
 * @property campsitesOwned the campsites owned by the owner
 */
data class OwnerData(
    val name: String = "",
    val lastName: String = "",
    val phoneNum: String = "",
    val email: String = "",
    val deviceId: String = "",
    val profilePic: String = "",
    val userID: String = "",
    val campsitesOwned: ArrayList<Uuid> = ArrayList(),
)

fun Owner.toData() = OwnerData(
    firstName,
    lastName,
    phoneNum,
    email,
    deviceId,
    profilePic,
    userID.toString(),
    campsitesOwned,
)

fun OwnerData.toOwner() = Owner(
    name,
    lastName,
    phoneNum,
    email,
    deviceId,
    profilePic,
    Uuid.parse(userID),
    campsitesOwned,
)

class OwnerList{
    private val _owners = mutableStateListOf<Owner>()

    private val db = Firebase.firestore

    /**
     * The path in the database for the owners and the
     * snapshot listener that updates the database everytime
     * a new owner is added.
     * */
    private val ownersRef = db.collection("Owners")
    init{
        ownersRef.addSnapshotListener { snapshots, error ->
            if(error != null){
                return@addSnapshotListener
            }

            _owners.clear()

            snapshots?.documents?.forEach { document->
                val owner = document.toObject(Owner::class.java)
                if(owner != null){
                    _owners.add(owner)
                }
            }
        }
    }

    val owners: List<Owner>
        get() = _owners

    /**
     * Adds an owner to the database
     *
     * @param owner The owner object being added
     */
    fun addOwner(owner: Owner) {
        ownersRef.document(owner.userID.toString()).set(owner.toData())
    }

    /**
     * updates a given owner with new information
     *
     * @param owner The owner object being updated
     * */
    fun updateOwner(owner: Owner) {
        ownersRef.document(owner.userID.toString()).set(owner.toData())
    }

    /**
     * deletes a given owner from the database
     *
     * @param owner The owner being deleted
     * */
    fun deleteOwner(owner: Owner) {
        ownersRef.document(owner.userID.toString()).delete()
        //TODO: delete associated campsites and lotteries
    }

    /**
     * gets an owner from the database
     *
     * @param id the Uuid of the owner
     */
    fun getOwner(id: Uuid): Owner? = _owners.find {
        it.userID == id
    }
}


