package com.example.project_ballin.admin.adminui

import android.util.Log
import androidx.compose.foundation.layout.Box
import com.example.project_ballin.Report
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.project_ballin.AdminSearch
import com.example.project_ballin.ConfirmDeleteDialog
import com.example.project_ballin.reusableui.TopAppBar

class AdminViolationListScreen {

    val adminSearch = AdminSearch()
    val confirmDeleteDialog = ConfirmDeleteDialog()

    /**
     * Searches a report by ID being reported and reason
     *
     * @param query the search text
     * @return true if the report matches the search
     */
    fun Report.matchesSearch(query: String): Boolean =
        adminSearch.matchesQuery(query, idBeingReported, reason)

    /**
     * A generalized screen that shows a list of a reported
     * violations that shows either topic of a campsite owner, review or rating
     *
     * @param navController the navController of the app
     * @param reports the list of reports to iterate through, should be a certain subject passed though
     * such as review, campsite owner or rating
     * @param onDelete runs when the delete button is pressed
     */
    @Composable
    fun AdminViolationList (
        navController: NavController,
        reports: List<Report>,
        onDelete: (Report) -> Unit,
    ) {
        TopAppBar(
            navController = navController,
            screenTitle = "Reports List"
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(34.dp))

                if (reports.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No reports to browse through")
                    }
                } else {
                    // What the admin typed in the search box (kept when the screen rotates)
                    var query by rememberSaveable { mutableStateOf("") }
                    val results = reports.filter { it.matchesSearch(query) }
                    adminSearch.AdminSearchField(
                        query,
                        onQueryChange = { query = it },
                        placeholder = "Search reports"
                    )

                    if (results.isEmpty()) {
                        Text(
                            "No reports match \"${query.trim()}\".",
                            modifier = Modifier.padding(16.dp).testTag("noSearchResults")
                        )
                    } else {
                        ListReports(navController, results, onDelete = { onDelete(it) })
                    }
                }
            }
        }
    }

    /**
     * Shows all the reports of a certain subject in a list
     *
     * @param navController the navController of the app
     * @param reports the list of reports to iterate through
     * @param onDelete runs when the delete button is pressed
     */
    @Composable
    fun ListReports(
        navController: NavController,
        reports: List<Report>,
        onDelete: (Report) -> Unit,
    ) {
        // specify the reports by just doing the
        // reports.getReportsInCertainSubject() passed to it tbh
        var reportToDelete by remember { mutableStateOf<Report?>(null) }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            itemsIndexed(reports) { index, report ->
                ReportButtonRow(index, report, navController, onDelete = { reportToDelete = report })
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        reportToDelete?.let { report ->
            confirmDeleteDialog.ConfirmDeleteDialog(
                itemDescription = "the reported violation",
                onConfirm = {
                    onDelete(report)
                    reportToDelete = null
                },
                onCancel = { reportToDelete = null }
            )
        }
    }

    /**
     * Shows the general overview of a specific report,
     * it'll be a clickable button, showing only two of the most
     * important fields of reports.
     *
     * Details that should be shown on the button:
     * For campsite owner violations - firstName and lastName
     * For rating violations - campsiteID and rating
     * For review violations - campsiteID and dateSent
     *
     * @param index the index of the report in the list
     * @param report the report to show
     * @param navController the navController of the app
     * @param onDelete runs when the delete button is pressed
     */
    @Composable
    fun ReportButtonRow(
        index: Int,
        report: Report,
        navController: NavController,
        onDelete: (Report) -> Unit
    ) {
        Card(
            onClick = {
               navController.navigate("AdminViolationDetail/${report.ID}")
            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .testTag("checkReport${report.ID}") // for UI testing
                .fillMaxWidth(),
            colors = CardDefaults.cardColors()
        ) {

            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Report ${index + 1}",
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text("Report Subject: ${report.reportSubject}")
                    Text("ID Being Reported: ${report.idBeingReported}")
                }

                Button(
                    onClick = { onDelete(report) },
                    modifier = Modifier
                        .testTag("removeReport${report.ID}"), // for UI testing
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Remove")
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun AdminViolationListScreenPreview() {
        val navController = rememberNavController()

        val sampleReports = listOf(
            Report("Duplicate Campsite Listing", "Campsite Owner", "123456789", "100100100"),
            Report("Inappropiate Language Used", "Campsite Owner", "987654321", "111111111"),
        )
        AdminViolationList(navController, sampleReports, onDelete = {})
    }
}