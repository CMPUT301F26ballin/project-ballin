package com.example.project_ballin

import androidx.compose.runtime.mutableStateListOf
import com.example.project_ballin.entrant.Entrant
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

/**
 * The Entrant list class
 * Stores entrant profiles in firebase
 *
 * @property _entrants the list of entrants
 * @property db the database
 * @property entrantsRef the reference to the entrants collection
 * @property entrants the list of entrants
 *
 */
class EntrantList{
    private val _entrants = mutableStateListOf<Entrant>()

    private val db = Firebase.firestore

    /**
     * The path in the database for the entrants and the
     * snapshot listener that updates the database everytime
     * a new entrant is added.
     * */
    private val entrantsRef = db.collection("Entrants")
    init{
        entrantsRef.addSnapshotListener { snapshots, error ->
            if(error != null){
                return@addSnapshotListener
            }

            _entrants.clear()

            snapshots?.documents?.forEach { document->
                val entrant = document.toObject(Entrant::class.java)
                if(entrant != null){
                    _entrants.add(entrant)
                }
            }
        }
    }

    val entrants: List<Entrant>
        get() = _entrants

    /**
     * Adds an entrant to the database
     *
     * @param entrant The entrant object being added
     */
    fun addEntrant(entrant: Entrant) {
        entrantsRef.document(entrant.userID).set(entrant)
    }

    /**
     * updates a given entrant with new information
     *
     * @param entrant The entrant object being updated
     * */
    fun updateEntrant(entrant: Entrant) {
        entrantsRef.document(entrant.userID).set(entrant)
    }

    /**
     * deletes a given entrant from the database
     *
     * @param entrant The entrant being deleted
     * */
    fun deleteEntrant(entrant: Entrant) {
        entrantsRef.document(entrant.userID).delete()
    }

    /**
     * gets an entrant from the database
     *
     * @param id the Uuid of the entrant
     */
    fun getEntrant(id: String): Entrant? = _entrants.find {
        it.userID == id
    }
}


