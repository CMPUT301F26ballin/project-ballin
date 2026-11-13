package com.example.project_ballin

import androidx.compose.runtime.mutableStateListOf
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

/**
 * A list that that stores all the reports of users
 */
class ReportList {
    private val _reports = mutableStateListOf<Report>()

    private val db = Firebase.firestore

    /**
     * The path in the database for the user reports and the
     * snapshot listener that updates the database everytime
     * a new report is added.
     *
     */
    private val reportsRef = db.collection("Messages")

    init {
        reportsRef.addSnapshotListener { snapshots, error ->
            if (error != null) {
                return@addSnapshotListener
            }

            _reports.clear()

            snapshots?.documents?.forEach { document ->
                val report = document.toObject(Report::class.java)
                if (report != null) {
                    _reports.add(report)
                }
            }
        }
    }

    val reports: List<Report>
        get() = _reports

    /**
     * Adds a report to the database
     *
     * @param report The report being added
     */
    fun addReport(report: Report) {
        reportsRef.document(report.ID).set(report)
    }


    fun getAllReports(): List<Report> {
        return reports
    }

    /**
     * Gets a report from the database related to the subject given
     *
     * @param reportSubject the subject of the report to get
     * @return a list of reports related to subject
     */
    fun getReportsInSubject(reportSubject: String): List<Report> {
        var returnList = mutableStateListOf<Report>()
        reports.forEach { report ->
            if (report.reportSubject == reportSubject) {
                returnList.add(report)
            }
        }
        return returnList
    }
}