package com.example.project_ballin.owner

import com.example.project_ballin.User


data class Owner(
    override var firstName: String = "",
    override var lastName: String = "",
    override var phoneNum: String = "",
    override var email: String = "",
    override val deviceId: String = "",
    override var profilePic: String = "",
    override val userID: String = "",
    val campsitesOwned: MutableList<String> = mutableListOf(),
) : User