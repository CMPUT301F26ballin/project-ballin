@file:OptIn(ExperimentalUuidApi::class)
package com.example.project_ballin

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import kotlin.uuid.ExperimentalUuidApi

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
        campsitesRef.document(site.ID.toString()).set(site)
    }

    fun updateCampsite(oldSite: Campsite, updatedSite: Campsite){
        //THIS MAY CHANGE BASED ON IF WE IMPLEMENT CAMPSITE IDs
        campsitesRef.document(oldSite.ID.toString()).set(updatedSite)
    }

    // Would need to further handle how this affects owners/entrants
    fun deleteCampsite(site: Campsite) {
        campsitesRef.document(site.ID.toString()).delete()
    }

    fun getOwnerSites(ID: String): List<Campsite>{
        var returnList = mutableStateListOf<Campsite>()
        campsitesRef.whereEqualTo("ownerID", ID).get()
            .addOnSuccessListener { result ->
                returnList = result.toObjects<Campsite>() as SnapshotStateList<Campsite>

            }
        return returnList
    }

    fun getCampsite(ID: String): Campsite{
        var siteToReturn: Campsite? = null
        campsitesRef.document(ID.toString()).get().addOnSuccessListener {
            result ->
            siteToReturn = result.toObject<Campsite>()!!
        }
        return siteToReturn!!
    }

}