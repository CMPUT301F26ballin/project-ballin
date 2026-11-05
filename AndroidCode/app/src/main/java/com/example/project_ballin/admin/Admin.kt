package com.example.project_ballin.admin
import com.example.project_ballin.User

/**
 * Stores information of an admin, and allows
 * special functionalities related to being an admin
 *
 * Admin has a name, phone number, email, deviceId, profile picture, and user ID.
 * The admin can remove and browse things like campsites, profiles,
 * notifications, messages, and the owners who violate the app policy.
 * They can also act like an entrant/owner.
 *
 * @property firstName the name of the admin
 * @property lastName the last name of the admin
 * @property phoneNum the phone number of the admin
 * @property email the email of the admin
 * @property deviceId the device ID of the admin
 * @property profilePic the profile picture of the admin*
 * @property userID the user ID of the admin
 */
data class Admin(
    override var firstName: String = "",
    override var lastName: String = "",
    override var phoneNum: String = "",
    override var email: String = "",
    override val deviceId: String = "",
    override var profilePic: String = "",
    override val userID: String = ""
): User