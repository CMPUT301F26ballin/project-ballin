package com.example.project_ballin

import androidx.compose.runtime.mutableStateListOf
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.firestore

/**
 * Every user's profile, for the admin to browse and remove.
 *
 * Users are saved in three collections ("Entrants", "Owners", and "Admins"),
 * so this listens to all three and combines them into one list.
 */
class ProfileList {

    private val _profiles = mutableStateListOf<Profile>()

    private val db = Firebase.firestore

    // Which collection each role is saved in
    private val collections = mapOf(
        "Entrant" to "Entrants",
        "Owner" to "Owners",
        "Admin" to "Admins"
    )

    // The latest profiles from each collection, so an update to one doesn't wipe the others
    private val profilesByRole = mutableMapOf<String, List<Profile>>()

    // Reload the list whenever any of the three collections changes
    init {
        collections.forEach { (role, collectionName) ->
            db.collection(collectionName).addSnapshotListener { snapshots, error ->
                if (error != null) {
                    return@addSnapshotListener
                }

                profilesByRole[role] = snapshots?.documents?.map { toProfile(it, role) } ?: emptyList()

                _profiles.clear()
                profilesByRole.values.forEach { _profiles.addAll(it) }
            }
        }
    }

    val profiles: List<Profile>
        get() = _profiles

    /**
     * Removes a user from the database.
     *
     * @param profile the profile to remove
     */
    // TODO: also remove an owner's campsites and an entrant's lotteries
    fun deleteProfile(profile: Profile) {
        val collectionName = collections[profile.role] ?: return
        db.collection(collectionName).document(profile.userID).delete()
    }

    /**
     * Finds a profile by user ID.
     *
     * @param userID the user ID to look for
     * @return the profile, or null if not found
     */
    fun getProfile(userID: String): Profile? {
        return _profiles.find { it.userID == userID }
    }

    /**
     * Reads the profile fields from a user saved in the database.
     *
     * @param document the user's document
     * @param role the user's role
     * @return the user's profile
     */
    private fun toProfile(document: DocumentSnapshot, role: String): Profile {
        return Profile(
            deviceId = document.getString("deviceId") ?: "",
            firstName = document.getString("firstName") ?: "",
            lastName = document.getString("lastName") ?: "",
            email = document.getString("email") ?: "",
            phoneNum = document.getString("phoneNum") ?: "",
            profilePic = document.getString("profilePic") ?: "",
            role = role,
            userID = document.id // users are saved under their user ID
        )
    }
}
