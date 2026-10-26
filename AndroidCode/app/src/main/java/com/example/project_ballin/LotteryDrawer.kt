package com.example.project_ballin

/**
 * A class that draws the winners for a lottery, and
 * removes the winners from the pool after being drawn
 *
 */
class LotteryDrawer {

    fun draw(lottery: Lottery): List<User> {
        val waitlist = lottery.getWaitlist()
        val numWinners = lottery.getLotteryDrawNumber()
        val winners = mutableListOf<User>()

        for (i in 0 until numWinners) {
            val winner = waitlist.random()
            winners.add(winner)
            lottery.removeFromWaitlist(winner)
        }

        return winners
    }
}