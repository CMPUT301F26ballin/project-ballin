package com.example.project_ballin

import kotlin.uuid.Uuid

/**
 * Stores information of an admin, and allows
 * special functionalities related to being an admin
 *
 * Admin has a name, phone number, email, deviceId, profile picture.
 * The admin can remove and browse things like campsites, profiles,
 * notifications, messages, and the owners who violate the app policy.
 * They can also act like an entrant/owner.
 *
 * @property name the name of the admin
 * @property phoneNum the phone number of the admin
 * @property email the email of the admin
 * @property deviceId the device ID of the admin
 * @property profilePic the profile picture of the admin
 *
 */
@OptIn(kotlin.uuid.ExperimentalUuidApi::class)

class Admin(
    override var firstName: String = "",
    override var lastName: String = "",
    override var phoneNum: String = "",
    override var email: String = "",
    override val deviceId: String = "",
    override var profilePic: String = "",
    override val userID: String = Uuid.random().toString(),
): User {

    override fun updateFirstName(newFirstName: String) {
        firstName = newFirstName
    }

    override fun updateLastName(newLastName: String) {
        lastName = newLastName
    }

    override fun updatePhoneNum(newPhoneNum: String) {
        phoneNum = newPhoneNum
    }

    override fun updateEmail(newEmail: String) {
        email = newEmail
    }

    override fun updateProfilePic(newProfilePic: String) {
        profilePic = newProfilePic
    }

    override fun deleteAccount() {
        TODO("Not yet implemented")
    }
}