package com.example.project_ballin.lottery

import com.example.project_ballin.Campsite
import com.example.project_ballin.User
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
    private val waitList: MutableList<User> = mutableListOf(),
    private var occupancy: Int = 0,
    private var numWinners: Int = 0,
    private var registrationStart: Long? = null,
    private var registrationEnd: Long? = null,
    private var bookingStart: Long? = null,
    private var bookingEnd: Long? = null,
    private val associatedSites: MutableList<Campsite> = mutableListOf(),
    private val lotteryID: Uuid = Uuid.random(),
    private var lotteryOwnerID: String = "",
    private val winners: MutableList<Uuid> = mutableListOf(),
    private val losers: MutableList<Uuid> = mutableListOf()
) {

    @OptIn(ExperimentalUuidApi::class)
    fun getLotteryID(): Uuid {
        return lotteryID
    }

    fun setLotteryDrawNumber(newNumber: Int) {
        numWinners = newNumber
    }

    fun getLotteryDrawNumber(): Int {
        return numWinners
    }

    fun addToWaitlist(user: User) {
        waitList.add(user)
    }

    fun removeFromWaitlist(user: User) {
        waitList.remove(user)
    }

    fun getWaitlist(): List<User> {
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

    @OptIn(ExperimentalUuidApi::class)
    fun getWinners(): List<Uuid> {
        return winners
    }

    @OptIn(ExperimentalUuidApi::class)
    fun getLosers(): List<Uuid> {
        return losers
    }

}