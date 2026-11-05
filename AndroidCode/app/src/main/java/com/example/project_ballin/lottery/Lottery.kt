package com.example.project_ballin.lottery

import com.example.project_ballin.Campsite
import com.example.project_ballin.User
import com.example.project_ballin.entrant.Entrant
import com.example.project_ballin.owner.Owner
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


/**
 * A class that stores all the information of a lottery
 *
 * Lotteries have a list of waitlist users, an occupancy limit,
 * a number of winners, an owner, an active date Range, and associated campsites
 *
 * @property waitList the list of waitlist users
 * @property occupancy the occupancy limit of the lottery
 * @property numWinners the number of winners of the lottery
 * @property registrationStart the start time of sign up period
 * @property registrationEnd the end time of sign up period
 * @property bookingStart the start time of booking period
 * @property bookingEnd the end time of booking period
 * @property associatedSites the associated campsites of the lottery
 * @property lotteryID the ID of the lottery
 * @property lotteryOwnerID the ID of the owner of the lottery
 * @property winners the list of winners of the lottery
 * @property losers the list of losers of the lottery
 *
 */
data class Lottery(
    var waitList: MutableList<String> = mutableListOf(),
    var occupancy: Int = 0,
    var numWinners: Int = 0,
    var registrationStart: Long? = null,
    var registrationEnd: Long? = null,
    var bookingStart: Long? = null,
    var bookingEnd: Long? = null,
    var associatedSites: MutableList<String> = mutableListOf(),
    var lotteryID: String = "",
    var lotteryOwnerID: String = "",
    var winners: MutableList<String> = mutableListOf(),
    var losers: MutableList<String> = mutableListOf()
){
    fun addWinner(id: String) { winners.add(id) }
    fun addLoser(id: String) { losers.add(id) }
}