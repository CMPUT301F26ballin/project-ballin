package com.example.project_ballin

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
 * @property dateRange the date range of the lottery
 * @property associatedSites the associated campsites of the lottery
 *
 */
class Lottery @OptIn(ExperimentalUuidApi::class) constructor(
    private val waitList: MutableList<User> = mutableListOf(),
    private var occupancy: Int = 0,
    private var numWinners: Int = 0,
    private var lotteryOwner: Owner,
    //private val dateRange: DateRange = TODO(),
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