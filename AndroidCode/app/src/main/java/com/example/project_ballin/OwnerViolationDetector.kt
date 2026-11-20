package com.example.project_ballin

/**
 * This should generate or look through list of owners
 * and see if they have any violations.
 * Can't completely check if they violate all policies
 * as it'll have to rely on user reports for the rest of it
 */

// NOTE TO SELF: I SHOULD PROBABLY MAKE ONE SINGLE OBJECT FOR THIS
// BUT EACH TIME A NEW CAMPSITE IS MADE BY OWNERS
// THIS GETS TRIGGERED AND CHECKS IF DUPLICATE

class OwnerViolationDetector(
    var reportList: ReportList
) {

    /**
     * This function gets a list of all the campsites owned by the owner
     * and checks if there are any duplicates.
     *
     * @param ownerID the ID of the owner
     * @param ownerCampsites the list of campsites
     * @return boolean, true if there are duplicates, false if no duplicates found
     */
    fun duplicateCampsiteListings(ownerID: String, ownerCampsites: MutableList<Campsite>): Boolean{

        // Campsites with exact lat, lon, locationName are most likely a duplicate listing
        // because they have the same physical location, unlike a name or other property which
        // one can end up putting false information in.
        // Source Used: https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-triple/
        val nonDuplicateCampsites = mutableSetOf<Triple<Double,Double,String>>()

        for (campsite in ownerCampsites) {
            nonDuplicateCampsites.add(Triple(
                campsite.location.latitude,
                campsite.location.longitude,
                campsite.locationName
            ))
        }

        // if no duplicates, the size of the lists should be the same
        return ownerCampsites.size != nonDuplicateCampsites.size
    }

    /**
     * Checks if the owner has committed any violations.
     * If they have one, a report is generated.
     *
     * @param ownerID the ID of the owner
     * @param ownerCampsites the campsites repository
     */
    fun generateNewReport(ownerID: String, ownerCampsites: MutableList<Campsite>) {
        val violationCommitted = (duplicateCampsiteListings(ownerID, ownerCampsites))

        // Checking if owner already has at least one report/violation
        // for duplicate camp listings already
        val existingReport = (
                reportList.getReport(ownerID) != null)
                &&
                reportList.getReport(ownerID)!!.reportSubject == "Owner Has Posted Duplicate Listings"

        if (violationCommitted && !existingReport) {
            val newReport = Report(
                "Owner Has Posted Duplicate Listings",
                "Campsite Owner",
                ownerID,
                "TODO"
            )
            reportList.addReport(newReport)
        }
    }

}