package com.example.project_ballin

import kotlin.uuid.Uuid

/**
 * Stores information of an admin, and allows
 * special functionalities related to being an admin
 *
 * Admin has a name, phone number, email, deviceId, profile picture,
 * app policy and the current mode. The admin can remove and browse
 * things like campsites, profiles, notifications, messages, and the
 * owners who violate the app policy. They can also act like an entrant/owner.
 *
 * @property name the name of the admin
 * @property phoneNum the phone number of the admin
 * @property email the email of the admin
 * @property deviceId the device ID of the admin
 * @property profilePic the profile picture of the admin
 * @property appPolicy the app policy for the app
 * @property currentMode the mode the admin user is in, by default they start off in admin mode
 *
 */
class Admin(
    override var name: String,
    override var phoneNum: String,
    override var email: String,
    override val deviceId: String,
    override var profilePic: String,
    override val appPolicy: String,
    override val userID: Uuid = Uuid.random(),
    var currentMode: String = "Admin"
): User {

    // Might have to make Owner(), and Entrant() objects
    // Because Admin can be owner and entrant if they want

    override fun updateName(newName: String) {
        name = newName
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

    /**
     * This function verifies that admin is currently
     * in admin mode and not other mode
     *
     * @return a boolean based on if admin is in admin mode
     */
    fun verifyAdminMode(): Boolean {
        return (this.currentMode == "Admin")
    }

    /**
     * Allows Admin to act like an owner
     */
    fun switchOwnerMode() {
        this.currentMode = "Owner"
    }

    /**
     * Allows Admin to act like an entrant
     */
    fun switchEntrantMode() {
        this.currentMode = "Entrant"
    }

    /**
     * Allows Admin to act like an admin
     */
    fun switchAdminMode() {
        this.currentMode = "Admin"
    }

    /**
     * Generic function that deletes objects from storage/database
     *
     * @param obj the object that admin wants to remove
     * @param deleteFunc function that takes in object to remove and removes it
     * @return doesn't return a value
     */
    fun <T> deleteObj(obj: T, deleteFunc: (T) -> Unit) {
        if (verifyAdminMode()) {
            deleteFunc(obj)
        }
    }
}