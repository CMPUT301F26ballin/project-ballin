package com.example.project_ballin.owner

import kotlin.uuid.Uuid

class Owner(
    override var name: String,
    override var phoneNum: String,
    override var email: String,
    override val deviceId: String,
    override var profilePic: String,
    override val appPolicy: String,
    override val userID: Uuid = Uuid.random(),
    private val campsitesOwned: ArrayList<Uuid> = ArrayList()
) : com.example.project_ballin.User {

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
        TODO()
    }

    fun addCampsite(campsiteID: Uuid) {
        campsitesOwned.add(campsiteID)
    }

    fun getCampsitesOwned(): ArrayList<Uuid> {
        return campsitesOwned
    }

}