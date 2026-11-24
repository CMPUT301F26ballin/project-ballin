package com.example.project_ballin

import androidx.compose.runtime.mutableStateListOf
import com.example.project_ballin.entrant.Reservation
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

/**
 * The list that holds all of the reservations. Has a variety of methods that allow
 * reservations of a site before or after a certain date to be selected.
 * */


class ReservationList {
    private val _reservations = mutableStateListOf<Reservation>()

    private val db = Firebase.firestore

    /**
     * The path in the database for the reservations and the
     * snapshot listener that updates the database everytime
     * a new site is added.
     * */

    private val reservationsRef = db.collection("Reservations")
    init{
        reservationsRef.addSnapshotListener { snapshots, error ->
            if(error != null){
                return@addSnapshotListener
            }

            _reservations.clear()

            snapshots?.documents?.forEach { document->
                val reservation = document.toObject(Reservation::class.java)
                if(reservation != null){
                    _reservations.add(reservation)
                }
            }
        }
    }

    val reservations: List<Reservation>
        get() = _reservations

    /**
     * Adds a reservation to the Database
     *
     * @param site The campsite object being added
     */
    fun addReservation(reservation: Reservation){

        reservationsRef.document(reservation.resID).set(reservation)
    }

    /**
     * A method that takes a campsite reservation and cancels it.
     * */
    fun cancelReservation(reservation: Reservation){
        reservation.cancelled = true

        reservationsRef.document(reservation.resID).set(reservation)
    }

    /**
     * A method that takes a reservation ID and returns the corresponding reservation
     * */

    fun getReservation(resID: String): Reservation?{
        return reservations.find{it.resID == resID}
    }

    /**
     * A method that takes a campsite's ID and a specific date and returns all non-cancelled reservations
     * that fall after that date (a reservation that is being used will be on that list)
     *
     * @param siteID a string of the Uuid of the campsite
     * @param date A long date of the millis that serves as the date
     * we are looking for reservations relative to.
     * */

    fun getReservationsAfter(siteID: String, date: Long): List<Reservation>{
        val returnList = mutableStateListOf<Reservation>()
        reservations.forEach { reservation ->
            if(reservation.campsiteID == siteID &&
                reservation.dates!!.last() >= date&&
                !reservation.cancelled){
                returnList.add(reservation)
            }
        }

        return returnList
    }

    /**
     * A method that takes a campsite's ID and a specific date and returns all non-cancelled reservations
     * that fall before that date.
     *
     * @param siteID a string of the Uuid of the campsite
     * @param date A long date of the millis that serves as the date
     * we are looking for reservations relative to.
     * */

    fun getReservationsBefore(siteID: String, date: Long): List<Reservation>{
        val returnList = mutableStateListOf<Reservation>()
        reservations.forEach { reservation ->
            if(reservation.campsiteID == siteID &&
                reservation.dates!!.last() < date&&
                !reservation.cancelled){
                returnList.add(reservation)
            }
        }
        return returnList
    }

    /**
     * A method that takes a campsite's ID and a specific date and returns all cancelled reservations on that site
     *
     * @param siteID a string of the Uuid of the campsite
     * */

    fun getCancelledReservations(siteID: String): List<Reservation>{
        val returnList = mutableStateListOf<Reservation>()
        reservations.forEach { reservation ->
            if(reservation.campsiteID == siteID &&
                reservation.cancelled){
                returnList.add(reservation)
            }
        }
        return returnList
    }

    /**
     * A method that takes a user's ID and a specific date and returns all non-cancelled reservations
     * that fall after that date (a reservation that is being used will be on that list)
     *
     * @param userID a string of the Uuid of the campsite
     * @param date A long date of the millis that serves as the date
     * we are looking for reservations relative to.
     * */

    fun getUserReservationsAfter(userID: String, date: Long): List<Reservation>{
        val returnList = mutableStateListOf<Reservation>()
        reservations.forEach { reservation ->
            if(reservation.userID == userID &&
                reservation.dates!!.last() >= date&&
                !reservation.cancelled){
                returnList.add(reservation)
            }
        }

        return returnList
    }

    /**
     * A method that takes a user's ID and a specific date and returns all non-cancelled reservations
     * that fall before that date.
     *
     * @param userID a string of the Uuid of the campsite
     * @param date A long date of the millis that serves as the date
     * we are looking for reservations relative to.
     * */

    fun getUserReservationsBefore(userID: String, date: Long): List<Reservation>{
        val returnList = mutableStateListOf<Reservation>()
        reservations.forEach { reservation ->
            if(reservation.userID == userID &&
                reservation.dates!!.last() < date&&
                !reservation.cancelled){
                returnList.add(reservation)
            }
        }
        return returnList
    }

    /**
     * A method that takes a campsite's ID and a specific date and returns all cancelled reservations on that site
     *
     * @param userID a string of the Uuid of the campsite
     * */

    fun getUserCancelledReservations(userID: String): List<Reservation>{
        val returnList = mutableStateListOf<Reservation>()
        reservations.forEach { reservation ->
            if(reservation.userID == userID &&
                reservation.cancelled){
                returnList.add(reservation)
            }
        }
        return returnList
    }

    fun getCurrentReservationsForEntrant(userID: String): List<Reservation> {
        val returnList = mutableStateListOf<Reservation>()
        reservations.forEach { reservation ->
            if(reservation.userID == userID && reservation.dates?.get(1)!! <= System.currentTimeMillis() && !reservation.cancelled){
                returnList.add(reservation)
            }
        }
        return returnList
    }

    fun getPastReservationsForEntrant(userID: String): List<Reservation> {
        val returnList = mutableStateListOf<Reservation>()

        reservations.forEach { reservation ->
            if(reservation.userID == userID && reservation.dates?.get(1)!! >= System.currentTimeMillis() && !reservation.cancelled){
                returnList.add(reservation)
            }
        }
        return returnList
    }

    fun getCancelledReservationsForEntrant(userID: String): List<Reservation> {
        val returnList = mutableStateListOf<Reservation>()
        reservations.forEach { reservation ->
            if(reservation.userID == userID && reservation.cancelled){
                returnList.add(reservation)
            }
        }
        return returnList
    }
}