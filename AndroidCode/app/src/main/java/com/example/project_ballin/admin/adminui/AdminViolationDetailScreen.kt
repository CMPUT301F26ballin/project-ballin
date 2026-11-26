package com.example.project_ballin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.project_ballin.reusableui.TopAppBar

class AdminViolationDetailScreen {

    val confirmDeleteDialog = ConfirmDeleteDialog()

    /**
     * A generalized screen that shows details of a reported
     * violation of either a campsite owner, review or rating
     *
     * @param navController the navController of the app
     */
    @Composable
    fun AdminViolationDetail (
        navController: NavController,
        report: Report,
        onDelete: (Report) -> Unit
    ) {
        var reportToDelete by remember { mutableStateOf<Report?>(null) }

        TopAppBar(
            navController = navController,
            screenTitle = "Report Details"
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                ReportDetailRows("Reason", report.reason, "reason${report.reason}")
                ReportDetailRows("Report Subject", report.reportSubject, "reportSubject${report.reportSubject}")
                ReportDetailRows("ID", report.ID, "ID${report.ID}")
                ReportDetailRows("ID Being Reported", report.idBeingReported, "idBeingReported${report.idBeingReported}")

                Spacer(modifier = Modifier.height(68.dp))

                Button(
                    onClick = {
                        reportToDelete = report
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("removeReport") // for UI testing
                ) {
                    Text("Remove")
                }
            }

            reportToDelete?.let { report ->
                confirmDeleteDialog.ConfirmDeleteDialog(
                    itemDescription = "the report",
                    onConfirm = {
                        onDelete(report)
                        reportToDelete = null
                        navController.popBackStack()
                    },
                    onCancel = { reportToDelete = null }
                )
            }
        }
    }


    @Composable
    fun ReportDetailRows (descriptionText: String, content: String, testTag: String) {
        Spacer(modifier = Modifier.height(34.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .testTag(testTag)
        ) {
            Text(
                text = "${descriptionText}:",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(content)
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun AdminViolationDetailScreenPreview() {
        val navController = rememberNavController()
        AdminViolationDetailScreen().AdminViolationDetail(navController,
            Report(
                "Duplicate Campsite Listing",
                "Campsite Owner",
                "123456789",
                "100100100"),
            onDelete = {}
        )
    }

}