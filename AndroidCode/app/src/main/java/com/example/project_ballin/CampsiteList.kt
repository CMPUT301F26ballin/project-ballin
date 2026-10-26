package com.example.project_ballin

import androidx.compose.runtime.mutableStateListOf
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
/**
 * A List connected to the Firestore database that contains
 * every campsite added to the app. Users will access this list
 * or subsets of it through the database.
 *
 * */
class CampsiteList {

    private val _campsites = mutableStateListOf<Campsite>()

    private val db = Firebase.firestore

    /**
     * The path in the database for the campsites and the
     * snapshot listener that updates the database everytime
     * a new site is added.
     * */

    private val campsitesRef = db.collection("Campsites")
    init{
        campsitesRef.addSnapshotListener { snapshots, error ->
            if(error != null){
                return@addSnapshotListener
            }

            _campsites.clear()

            snapshots?.documents?.forEach { document->
                val campsite = document.toObject(Campsite::class.java)
                if(campsite != null){
                    _campsites.add(campsite)
                }
            }
        }
    }

    val campsite: List<Campsite>
        get() = _campsites

    /**
     * Adds a campsite to the database
     *
     * @param site The campsite object being added
     */
    fun addCampsite(site: Campsite){
        //THIS MAY CHANGE BASED ON IF WE IMPLEMENT CAMPSITE IDs
        campsitesRef.document(site.name).set(site)
    }

    fun updateCampsite(oldSite: Campsite, updatedSite: Campsite){
        //THIS MAY CHANGE BASED ON IF WE IMPLEMENT CAMPSITE IDs
        campsitesRef.document(oldSite.name).set(updatedSite)
    }

    // Would need to further handle how this affects owners/entrants
    fun deleteCampsite(site: Campsite) {
        campsitesRef.document(site.name).delete()
    }

}