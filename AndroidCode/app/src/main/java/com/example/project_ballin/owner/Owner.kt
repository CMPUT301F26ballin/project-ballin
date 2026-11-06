package com.example.project_ballin.owner

import com.example.project_ballin.EntrantList
import com.example.project_ballin.OwnerList
import com.example.project_ballin.User
import com.example.project_ballin.entrant.Entrant
import kotlin.uuid.Uuid


data class Owner(
    override var firstName: String = "",
    override var lastName: String = "",
    override var phoneNum: String = "",
    override var email: String = "",
    override val deviceId: String = "",
    override var profilePic: String = "",
    override val userID: String = Uuid.random().toString(),
    val campsitesOwned: MutableList<String> = mutableListOf(),
) : User{
    override fun updateDetails(
        firstName: String,
        lastName: String,
        phoneNum: String,
        email: String,
        profilePic: String
    ): Owner = copy(
        firstName = firstName,
        lastName = lastName,
        phoneNum = phoneNum,
        email = email,
        profilePic = profilePic
    )
    override fun deleteProfile() {
        OwnerList().deleteOwner(this)
    }
    override fun editProfile() {
        OwnerList().updateOwner(this)
    }
    override fun createProfile() {
        OwnerList().addOwner(this)
    }
}