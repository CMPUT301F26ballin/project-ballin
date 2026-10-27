package com.example.project_ballin

import androidx.compose.runtime.mutableStateListOf
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

/**
 * All user profiles, kept in sync with the "Users" collection in the database.
 */
class ProfileList {

    private val _profiles = mutableStateListOf<Profile>()

    private val db = Firebase.firestore

    private val profilesRef = db.collection("Users")

    // Reload the list whenever the database changes
    init {
        profilesRef.addSnapshotListener { snapshots, error ->
            if (error != null) {
                return@addSnapshotListener
            }

            _profiles.clear()

            snapshots?.documents?.forEach { document ->
                val profile = document.toObject(Profile::class.java)
                if (profile != null) {
                    _profiles.add(profile)
                }
            }
        }
    }

    val profiles: List<Profile>
        get() = _profiles

    /**
     * Adds a profile to the database (or replaces it if it already exists).
     *
     * @param profile the profile to add
     */
    fun addProfile(profile: Profile) {
        profilesRef.document(profile.deviceId).set(profile)
    }

    /**
     * Removes a profile from the database.
     *
     * @param profile the profile to remove
     */
    // TODO: also remove an owner's campsites and an entrant's lotteries
    fun deleteProfile(profile: Profile) {
        profilesRef.document(profile.deviceId).delete()
    }

    /**
     * Finds a profile by device ID.
     *
     * @param deviceId the device ID to look for
     * @return the profile, or null if not found
     */
    fun getProfile(deviceId: String): Profile? {
        return _profiles.find { it.deviceId == deviceId }
    }
}
