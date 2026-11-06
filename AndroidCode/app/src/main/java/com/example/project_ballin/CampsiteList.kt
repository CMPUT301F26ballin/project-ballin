package com.example.project_ballin

import androidx.compose.runtime.mutableStateListOf
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

@OptIn(kotlin.uuid.ExperimentalUuidApi::class)


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

    val campsites: List<Campsite>
        get() = _campsites

    /**
     * Adds a campsite to the database
     *
     * @param site The campsite object being added
     */
    fun addCampsite(site: Campsite){
        //THIS MAY CHANGE BASED ON IF WE IMPLEMENT CAMPSITE IDs
        campsitesRef.document(site.ID).set(site)
    }

    /**
     * A function that takes the campsite to be updated and a campsite with new
     * values and sets the database entry of the first campsite to the second one.
     * NOT TO BE USED BY TWO SITES WITH DIFFERENT UUIDS!
     *
     * @param oldSite the campsite entry that will be updated.
     * @param updatedSite the campsite values that will be entered instead.
     * */
    fun updateCampsite(oldSite: Campsite, updatedSite: Campsite){
        //THIS MAY CHANGE BASED ON IF WE IMPLEMENT CAMPSITE IDs
        campsitesRef.document(oldSite.ID).set(updatedSite)
    }

    /**
     * A function that takes a campsite and deletes it from the firestore.
     *
     * @param site the campsite to be deleted.
     * */

    // Would need to further handle how this affects owners/entrants
    fun deleteCampsite(site: Campsite) {
        campsitesRef.document(site.ID).delete()
    }

    /**
     * A function that takes a Uuid string and returns a list of all the sites owned
     * by the user associated with that ID.
     *
     * @param ID The string Uuid of the owner.
     * */

    fun getOwnerSites(ID: String): List<Campsite>{
        var returnList = mutableStateListOf<Campsite>()
        campsites.forEach { campsite -> if (campsite.ownerID == ID){
        returnList.add(campsite)}
        }
        return returnList
    }
    /**
     * A function that takes a campsite Uuid and returns the associated
     * site from firestore.
     *
     * @param ID the string Uuid of the campsite being accessed.
     * */
    fun getCampsite(ID: String?): Campsite? =
        if (ID == null) null else _campsites.find { it.ID == ID }

}