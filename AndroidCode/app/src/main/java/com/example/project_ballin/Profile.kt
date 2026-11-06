package com.example.project_ballin

import com.example.project_ballin.admin.Admin
import com.example.project_ballin.owner.Owner

/**
 * A user's profile as saved in the database.
 *
 * @property deviceId the user's device ID (also the database ID)
 * @property name the user's name
 * @property email the user's email
 * @property phoneNum the user's phone number (can be blank)
 * @property profilePic link to the user's profile picture (can be blank)
 * @property role "Entrant", "Owner", or "Admin"
 */
data class Profile(
    val deviceId: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val phoneNum: String = "",
    val profilePic: String = "",
    val role: String = "Entrant"
) {
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
                role = role
            )
        }
    }
}
