package com.example.project_ballin

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.google.firebase.Firebase
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects

@OptIn(kotlin.uuid.ExperimentalUuidApi::class)


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
                if(lottery != null){
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
    fun addLottery(lottery: Lottery){
        lotteriesRef.document(lottery.getLotteryID().toString()).set(lottery)
    }

    fun updateLottery(oldLottery: Lottery, updatedLottery: Lottery){
        lotteriesRef.document(oldLottery.getLotteryID().toString()).set(updatedLottery)
    }

    fun deleteCampsite(lottery: Lottery) {
        lotteriesRef.document(lottery.getLotteryID().toString()).delete()
    }

    fun getOwnerLotteries(ID: Int): List<Lottery>{
        var returnList = mutableStateListOf<Lottery>()
        lotteriesRef.whereEqualTo("ownerID", ID).get()
            .addOnSuccessListener { result ->
                returnList = result.toObjects<Lottery>() as SnapshotStateList<Lottery>

            }
        return returnList
    }

    fun getLottery(ID: Int): Lottery{
        var lotteryToReturn: Lottery? = null
        lotteriesRef.document(ID.toString()).get().addOnSuccessListener {
                result ->
            lotteryToReturn = result.toObject<Lottery>()!!
        }
        return lotteryToReturn!!
    }

}