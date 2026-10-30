package com.example.project_ballin

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
    private var lotteryOwner: Owner,
    //private val dateRange: DateRange = TODO(),
    private var registrationStart: Long? = null,
    private var registrationEnd: Long? = null,
    private var bookingStart: Long? = null,
    private var bookingEnd: Long? = null,
    private val associatedSites: MutableList<Campsite> = mutableListOf(),
    private val lotteryID: Uuid = Uuid.random()
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

    fun setLotteryOwner(newOwner: Owner) {
        lotteryOwner = newOwner
    }

    fun getLotteryOwner(): Owner {
        return lotteryOwner
    }

    fun setRegistrationDates(newStart: Long, newEnd: Long) {
        registrationStart = newStart
        registrationEnd = newEnd
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

}