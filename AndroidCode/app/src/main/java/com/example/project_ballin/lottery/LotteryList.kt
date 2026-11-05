package com.example.project_ballin.lottery

import androidx.compose.runtime.mutableStateListOf
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore


/**
 * A List connected to the Firestore database that contains
 * every campsite added to the app. Users will access this list
 * or subsets of it through the database.
 *
 * */
class LotteryList {

    private val _lotteries = mutableStateListOf<Lottery>()

    private val db = Firebase.firestore

    /**
     * The path in the database for the lotteries and the
     * snapshot listener that updates the database everytime
     * a new site is added.
     * */
    private val lotteriesRef = db.collection("Lotteries")

    init{
        lotteriesRef.addSnapshotListener { snapshots, error ->
            if(error != null){
                return@addSnapshotListener
            }

            _lotteries.clear()

            snapshots?.documents?.forEach { document->
                val lottery = document.toObject(Lottery::class.java)
                if (lottery != null){
                    _lotteries.add(lottery)
                }
            }
        }
    }

    val lottery: List<Lottery>
        get() = _lotteries

    /**
     * Adds a lottery to the database
     *
     * @param lottery The lottery object being added
     */
    fun addLottery(lottery: Lottery) {
        lotteriesRef.document(lottery.lotteryID).set(lottery)
    }

    fun updateLottery(oldLottery: Lottery, updatedLottery: Lottery) {
        lotteriesRef.document(oldLottery.lotteryID).set(updatedLottery)
    }

    fun deleteLottery(lottery: Lottery) {
        lotteriesRef.document(lottery.lotteryID).delete()
    }

    fun getLottery(ID: String): Lottery? =
        _lotteries.find { it.lotteryID == ID }

    fun getOwnerLotteries(ID: String): List<Lottery> =
        _lotteries.filter { it.lotteryOwnerID == ID }

}