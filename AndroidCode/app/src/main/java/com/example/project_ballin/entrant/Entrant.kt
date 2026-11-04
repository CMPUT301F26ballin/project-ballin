package com.example.project_ballin.entrant

import androidx.compose.ui.semantics.Role
import com.example.project_ballin.MainActivity
import com.example.project_ballin.User
import com.example.project_ballin.lottery.Lottery
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)


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
 *
 */
class Entrant(
    override var firstName: String = "",
    override var lastName: String = "",
    override var phoneNum: String = "",
    override var email: String = "",
    override val deviceId: String = "",
    override var profilePic: String = "",
    override val userID: Uuid = Uuid.random(),
) : User {
    private val activeLotteries = mutableListOf<Lottery>()
    private val wonLotteries = mutableListOf<Lottery>()
    private val lostLotteries = mutableListOf<Lottery>()
    //private val activeReservations = mutableListOf<Reservation>()
    //private val pastReservations = mutableListOf<Reservation>()

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

    fun getActiveLotteries(): List<Lottery> {
        return activeLotteries
    }

    fun addActiveLottery(lottery: Lottery){
        activeLotteries.add(lottery)
    }


    fun getWonLotteries(): List<Lottery> {
        return wonLotteries
    }

    fun getLostLotteries(): List<Lottery> {
        return lostLotteries
    }

    fun removeActiveLottery(lottery: Lottery){
        activeLotteries.remove(lottery)

        if (lottery in wonLotteries){
            wonLotteries.remove(lottery)
        }
        else if (lottery in lostLotteries){
            lostLotteries.remove(lottery)
        }
    }

    fun getUuid(): Uuid {
        return userID
    }
}