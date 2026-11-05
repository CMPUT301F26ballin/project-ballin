package com.example.project_ballin.entrant

import androidx.compose.runtime.mutableStateListOf
import com.example.project_ballin.User
import com.example.project_ballin.lottery.Lottery


/**
 * A class that stores all the information of an entrant
 *
 * Entrants have a name, phone number, email, device ID, profile picture, and app policy.
 *
 * @property firstName the name of the entrant
 * @property lastName the last name of the entrant
 * @property phoneNum the phone number of the entrant
 * @property email the email of the entrant
 * @property deviceId the device ID of the entrant
 * @property profilePic the profile picture of the entrant
 *
 */
data class Entrant(
    override var firstName: String = "",
    override var lastName: String = "",
    override var phoneNum: String = "",
    override var email: String = "",
    override val deviceId: String = "",
    override var profilePic: String = "",
    override val userID: String = "",
    val activeLotteries: MutableList<Lottery> = mutableStateListOf(),
    val wonLotteries: MutableList<Lottery> = mutableStateListOf(),
    val lostLotteries: MutableList<Lottery> = mutableStateListOf(),
    val activeReservations: MutableList<Reservation> = mutableStateListOf(),
    val pastReservations: MutableList<Reservation> = mutableStateListOf(),
) : User











