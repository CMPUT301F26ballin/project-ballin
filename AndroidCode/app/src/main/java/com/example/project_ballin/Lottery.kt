package com.example.project_ballin

class Lottery {
    private val waitList: MutableList<User> = mutableListOf()
    private var occupancy: Int = 0
    //private val lotteryOwner: Owner = TODO()
    //private val dateRange: DateRange = TODO()

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

    //fun setLotteryOwner(newOwner: Owner) {
        //lotteryOwner = newOwner

    //}

    //fun getLotteryOwner(): Owner {
        //return TODO()

    //}

    //fun setDateRange(newDateRange: DateRange) {
        //dateRange = newDateRange
    //}

    //fun getDateRange(): DateRange {
        //return TODO()
    //}

    //fun drawWinner(): User {
        //return TODO()
    //}
}