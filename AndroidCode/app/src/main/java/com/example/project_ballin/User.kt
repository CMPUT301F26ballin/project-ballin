package com.example.project_ballin

import java.util.UUID
import kotlin.uuid.Uuid

interface User {
    var name: String
    var phoneNum: String
    var email: String
    val deviceId: String
    val profilePic: String
    val appPolicy: String

    val userID: Uuid

    fun updateName(newName: String)
    fun updatePhoneNum(newPhoneNum: String)
    fun updateEmail(newEmail: String)
    fun updateProfilePic(newProfilePic: String)

    fun deleteAccount()

}