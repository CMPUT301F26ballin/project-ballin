package com.example.project_ballin

/**
 * This should generate or look through list of owners
 * and see if they have any violations.
 * Can't completely check if they violate all policies
 * as it'll have to rely on user reports on the owner
 */

class OwnerViolationDetector(
    var reportList: ReportList
) {

    /**
     * This function gets a list of all the campsites owned by the owner
     * and checks if there are any duplicates.
     *
     * @param ownerID the ID of the owner
     * @param campsites the list of campsites
     * @return boolean, true if there are duplicates, false if no duplicates found
     */
    fun duplicateCampsiteListings(ownerID: String, campsites: CampsiteList): Boolean{
        val ownersCampsites = campsites.getOwnerSites(ownerID)

        // Campsites with exact lat, lon, locationName are most likely a duplicate listing
        // because they have the same physical location, unlike a name or other property which
        // one can end up putting false information in.
        // Source Used: https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-triple/
        val nonDuplicateCampsites = mutableSetOf<Triple<Double,Double,String>>()

        for (campsite in ownersCampsites) {
            nonDuplicateCampsites.add(Triple(
                campsite.lat,
                campsite.lon,
                campsite.locationName
            ))
        }

        // if no duplicates, the size of the lists should be the same
        // because a set (nonDuplicateCampsites) doesn't allow copies to be added,
        return ownersCampsites.size != nonDuplicateCampsites.size
    }

    /**
     * Checks if the owner has committed any violations.
     * If they have one, a report is generated.
     *
     * @param ownerID the ID of the owner
     * @param campsites the campsites repository
     */
    fun generateNewReport(ownerID: String, campsites: CampsiteList) {
        val violationCommitted = (duplicateCampsiteListings(ownerID, campsites))

        if (violationCommitted) {
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