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

    fun updateDetails(
        firstName: String = this.firstName,
        lastName: String = this.lastName,
        phoneNum: String = this.phoneNum,
        email: String = this.email,
        profilePic: String = this.profilePic,
        deviceId: String = this.deviceId
    ): User

    fun deleteProfile()
    fun editProfile()
    fun createProfile()
}