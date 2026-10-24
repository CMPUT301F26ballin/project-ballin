package com.example.project_ballin

import android.location.Location
import android.media.Image

/** A class that stores all the information of a campsite
*
* Campsites have a name, location, list of tags,
* price per night, rating, Rules & Cancellation Policy,
* description, occupancy limit, promotional images, and the
* unique user ID of their owner. Campsites get stored in
 * one large list and are accessed by taking subsets of them
 * depending on who is accessing the list.
*
 * @property name the name of the campsite
 * @property location the location of the campsite
 * @property tags the list of descriptive tags of the campsite
 * @property price the price per night of the campsite
 * @property rating the average rating of the campsite, ratings provided by users
 * @property Rules the Rules & Cancellation Policy of the site defined by the owner
 * @property Description a brief description of the site provided by the site owner
 * @property Occupancy the occupancy limit of the campsite
 * @property images A list of promotional images for the campsite
 * @property OwnerID the unique userID of the owner of the campsite
 *
 *
*
* */

class Campsite(
    var name: String,
    var location: Location,
    var tags: MutableList<String>,
    var price: Float,
    var rating: Float,
    var Rules: String,
    var Description: String,
    var Occupancy: Int,
    var images: MutableList<Image>,
    var OwnerID: Int) {
}