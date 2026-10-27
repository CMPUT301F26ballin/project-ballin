package com.example.project_ballin

class Owner(
    override var name: String,
    override var phoneNum: String,
    override var email: String,
    override val deviceId: String,
    override var profilePic: String,
    override val appPolicy: String,
    val campsitesOwned: ArrayList<Int> = ArrayList()
) : User {

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

    fun addCampsite(campsiteID: Int) {
        campsitesOwned.add(campsiteID)
    }

    fun getCampsitesOwned(): ArrayList<Int> {
        return campsitesOwned
    }

}