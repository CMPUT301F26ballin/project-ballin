package com.example.project_ballin

import com.example.project_ballin.owner.Owner

/**
 * A user's profile, as shown to the admin.
 *
 * @property deviceId the user's device ID
 * @property firstName the user's first name
 * @property lastName the user's last name
 * @property email the user's email
 * @property phoneNum the user's phone number (can be blank)
 * @property profilePic link to the user's profile picture (can be blank)
 * @property role "Entrant", "Owner", or "Admin"
 * @property userID the user's ID (also their ID in the database)
 */
data class Profile(
    val deviceId: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val phoneNum: String = "",
    val profilePic: String = "",
    val role: String = "Entrant",
    val userID: String = ""
) {
    /** The user's first and last name together, e.g. "John Ballin". */
    val fullName: String
        get() = "$firstName $lastName".trim()

    companion object {
        /**
         * Makes a Profile from an Entrant, Owner, or Admin.
         *
         * @param user the user to copy
         * @return the user's profile
         */
        fun fromUser(user: User): Profile {
            val role = when (user) {
                is Admin -> "Admin"
                is Owner -> "Owner"
                else -> "Entrant"
            }
            return Profile(
                deviceId = user.deviceId,
                firstName = user.firstName,
                lastName = user.lastName,
                email = user.email,
                phoneNum = user.phoneNum,
                profilePic = user.profilePic,
                role = role,
                userID = user.userID
            )
        }
    }
}
