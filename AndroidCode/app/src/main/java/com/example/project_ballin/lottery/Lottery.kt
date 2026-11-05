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
 * @property lotteryOwner the owner of the lottery
 * @property dateRange the date range of the lottery *** Replaced ***
 * property registrationStart the start time of sign up period
 * @property registrationEnd the end time of sign up period
 * @property bookingStart the start time of booking period
 * @property bookingEnd the end time of booking period
 * @property associatedSites the associated campsites of the lottery
 *
 */
class Lottery @OptIn(ExperimentalUuidApi::class) constructor(
    private val waitList: MutableList<Entrant> = mutableListOf(),
    private var occupancy: Int = 0,
    private var numWinners: Int = 0,
    private var registrationStart: Long? = null,
    private var registrationEnd: Long? = null,
    private var bookingStart: Long? = null,
    private var bookingEnd: Long? = null,
    private val associatedSites: MutableList<Campsite> = mutableListOf(),
    private val lotteryID: String = Uuid.random().toString(),
    private var lotteryOwnerID: String = "",
    private val winners: MutableList<String> = mutableListOf(),
    private val losers: MutableList<String> = mutableListOf()
) {

    fun getLotteryID(): String {
        return lotteryID
    }

    fun setLotteryDrawNumber(newNumber: Int) {
        numWinners = newNumber
    }

    fun getLotteryDrawNumber(): Int {
        return numWinners
    }

    fun addToWaitlist(entrant: Entrant) {
        waitList.add(entrant)
    }

    fun removeFromWaitlist(entrant: Entrant) {
        waitList.remove(entrant)
    }

    fun getWaitlist(): List<Entrant> {
        return waitList
    }


    fun getOccupancy(): Int {
        return occupancy

    }

    fun setOccupancy(newOccupancy: Int) {
        occupancy = newOccupancy

    }

    fun setLotteryOwner(ownerID: String) {
        lotteryOwnerID = ownerID
    }

    fun getLotteryOwner(): String {
        return lotteryOwnerID
    }

    fun setRegistrationDates(newStart: Long, newEnd: Long) {
        registrationStart = newStart
        registrationEnd = newEnd
    }

    fun getRegistrationStart(): Long? {
        return registrationStart
    }

    fun getRegistrationEnd(): Long? {
        return registrationEnd
    }

    fun getBookingStart(): Long? {
        return bookingStart
    }

    fun getBookingEnd(): Long? {
        return bookingEnd
    }

    fun setBookingDates(newStart: Long, newEnd: Long) {
        bookingStart = newStart
        bookingEnd = newEnd
    }

    //fun setDateRange(newDateRange: DateRange) {
        //dateRange = newDateRange
    //}

    //fun getDateRange(): DateRange {
        //return TODO()
    //}

    fun getAssociatedSites(): MutableList<Campsite> {
        return associatedSites
    }

    fun addAssociatedSite(newSites: ArrayList<Campsite>) {
        associatedSites.addAll(newSites)
    }


    fun getWinners(): List<String> {
        return winners
    }

    fun getLosers(): List<String> {
        return losers
    }

}