package com.example.project_ballin.owner

import kotlin.uuid.Uuid

@OptIn(kotlin.uuid.ExperimentalUuidApi::class)


class Owner(
    override var firstName: String = "",
    override var lastName: String = "",
    override var phoneNum: String = "",
    override var email: String = "",
    override val deviceId: String = "",
    override var profilePic: String = "",
    override val userID: String = Uuid.random().toString(),
    val campsitesOwned: ArrayList<String> = ArrayList(),
) : com.example.project_ballin.User {

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
        TODO()
    }

    fun addCampsite(campsiteID: String) {
        campsitesOwned.add(campsiteID)
    }

}