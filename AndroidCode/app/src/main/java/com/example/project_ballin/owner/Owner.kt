package com.example.project_ballin.owner

import com.example.project_ballin.User

/**
 * A class that stores all the information of an owner
 * Owners have a name, phone number, email, device ID, profile picture, user ID, and a list of owned campsites.
 * @property firstName the name of the owner
 * @property lastName the last name of the owner
 * @property phoneNum the phone number of the owner
 * @property email the email of the owner
 * @property deviceId the device ID of the owner
 * @property profilePic the profile picture of the owner
 * @property userID the user ID of the owner
 * @property campsitesOwned the campsites owned by the owner
 */
data class Owner(
    override var firstName: String = "",
    override var lastName: String = "",
    override var phoneNum: String = "",
    override var email: String = "",
    override val deviceId: String = "",
    override var profilePic: String = "",
    override val userID: String = "",
) : User