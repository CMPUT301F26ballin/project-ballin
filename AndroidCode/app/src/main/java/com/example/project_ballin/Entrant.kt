package com.example.project_ballin

import kotlin.uuid.Uuid

@OptIn(kotlin.uuid.ExperimentalUuidApi::class)


/**
 * A class that stores all the information of an entrant
 *
 * Entrants have a name, phone number, email, device ID, profile picture, and app policy.
 *
 * @property name the name of the entrant
 * @property phoneNum the phone number of the entrant
 * @property email the email of the entrant
 * @property deviceId the device ID of the entrant
 * @property profilePic the profile picture of the entrant
 * @property appPolicy the app policy for the app
 *
 */
class Entrant(
    override var name: String,
    override var phoneNum: String,
    override var email: String,
    override val deviceId: String,
    override var profilePic: String,
    override val appPolicy: String,
    override val userID: Uuid = Uuid.random()
) : User {
    private val activeLotteries = mutableListOf<Lottery>()
    //private val activeReservations = mutableListOf<Reservation>()
    //private val pastReservations = mutableListOf<Reservation>()

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



}