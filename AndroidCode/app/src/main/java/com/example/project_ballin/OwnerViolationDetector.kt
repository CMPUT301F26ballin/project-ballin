package com.example.project_ballin

import kotlin.uuid.Uuid

/**
 * Generates and look through list of owners
 * and see if they have any violations. This class
 * Can't completely check if owners violate all policies
 * as it'll have to rely on user Reporting System for rest of it.
 *
 * @property reportList the list of reports
 */

class OwnerViolationDetector(
    var reportList: ReportList
) {

    /**
     * Checks if the campsite listing is a duplicate
     *
     * @param site the campsite to check
     * @param ownerCampsites the campsites list
     * @return boolean, true if duplicate, false if not
     */
    fun isDuplicateListing(site: Campsite, ownerCampsites: List<Campsite>): Boolean {
        // I chose lat,lon and locationName, and availability
        // because they are the most likely to be a duplicate listing
        // if same time and place
        val duplicateListing = ownerCampsites.find {
            it.lat == site.lat &&
            it.lon == site.lon &&
            it.locationName == site.locationName &&
            it.availability == site.availability &&
            it.ID != site.ID // If the ID is the same, it's the same listing
        }
        return (duplicateListing != null)
    }

    /**
     * Checks if existing report is already in the list of report,.
     * If they have one, a report is generated.
     *
     * @param ownerID the ID of the owner
     */
    fun generateNewReport(ownerID: String) {

        // Checking if owner already has at least one report/violation
        // for duplicate camp listings already
        val existingReport =
            (reportList.getReport(ownerID) != null) &&
                    (reportList.getReport(ownerID)!!.reason == "Has Posted Duplicate Listings")

        if (!existingReport) {
            val newReport = Report(
                "Has Posted Duplicate Listings",
                "Campsite Owner",
                ownerID,
                Uuid.random().toString()
            )
            reportList.addReport(newReport)
        }
    }

    /**
     * A quick check to see if the campsite listing is a duplicate,
     * and if it is, a report is generated if it doesn't already have one.
     *
     * This function is called upon or triggered each time
     * there is a new campsite listing made by an owner
     *
     * @param site the campsite to check
     * @param ownerCampsites the campsites list
     */
    fun quickSiteCheck(site: Campsite, ownerCampsites: List<Campsite>) {
        val violationCommitted = (isDuplicateListing(site, ownerCampsites))

        if (violationCommitted) {
            generateNewReport(site.ownerID)
        }
    }

}