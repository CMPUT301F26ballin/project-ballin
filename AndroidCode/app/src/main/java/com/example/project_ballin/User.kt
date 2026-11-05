package com.example.project_ballin

import java.util.UUID
import kotlin.uuid.Uuid

@OptIn(kotlin.uuid.ExperimentalUuidApi::class)


interface User {
    var firstName: String
    var lastName: String
    var phoneNum: String
    var email: String
    val deviceId: String
    val profilePic: String
    val userID: String

    fun updateFirstName(newFirstName: String)
    fun updateLastName(newLastName: String)
    fun updatePhoneNum(newPhoneNum: String)
    fun updateEmail(newEmail: String)
    fun updateProfilePic(newProfilePic: String)

    fun deleteAccount()

}