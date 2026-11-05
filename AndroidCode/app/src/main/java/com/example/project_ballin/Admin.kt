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
 * @property currentMode the mode the admin user is in, by default they start off in admin mode
 *
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