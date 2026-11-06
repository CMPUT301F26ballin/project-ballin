package com.example.project_ballin.lottery

/**
 * A class that draws the winners for a lottery, and
 * removes the winners from the pool after being drawn
 *
 */
class LotteryDrawer {

    fun draw(lottery: Lottery): List<String> {
        val winners = lottery.waitList.shuffled().take(lottery.numWinners)
        winners.forEach {
            lottery.waitList.remove(it)
            lottery.addWinner(it)
        }
        // Everyone still on the waitlist lost
        lottery.waitList.toList().forEach {
            lottery.waitList.remove(it)
            lottery.addLoser(it)
        }
        return winners
    }
}