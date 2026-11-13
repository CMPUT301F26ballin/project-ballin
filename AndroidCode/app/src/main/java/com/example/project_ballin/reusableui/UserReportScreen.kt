package com.example.project_ballin.reusableui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.project_ballin.Report
import com.example.project_ballin.entrant.entantui.ConfirmActionDialog

// NEED TO FIND WAY TO REPLACE THE ID
// PROB JUST GENERATE A RANDOM ID OR SOMETHING

class UserReportScreen {

    /**
     * The user report screen where user fills out
     * reason they are reporting something
     *
     * @param navController the navController Object
     * @param subjectReport the subject of the report
     * @param clickedID the ID of the clicked item that is being reported
     * @param onAdd the function that adds the user report to the database
     */
    @Composable
    fun UserReportReason(
        navController: NavController,
        subjectReport: String,
        clickedID: String,
        onAdd: (Report) -> Unit
    ) {
        var reason by remember { mutableStateOf("") }
        var confirmReport  by remember { mutableStateOf(false) }

        TopAppBar(
            navController = navController,
            screenTitle = "Report"
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Must be a valid report",
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.height(34.dp))

                OutlinedTextField(
                    value = reason,
                    onValueChange = { reason = it },
                    label = { Text("Reason for Report") }
                )
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { confirmReport = true }
                ) {
                    Text("Confirm")
                }
                Spacer(modifier = Modifier.height(34.dp))

                if (reason.isBlank() && confirmReport) {
                    Text(
                        text = "Reason for report must not be empty",
                        color = Color.Red
                    )
                }

                if (reason.isNotBlank() && confirmReport) {
                    ConfirmActionDialog().ConfirmAction(
                        message = "Are you sure you want to report this?",
                        confirm = { confirmed ->
                            if (confirmed) {
                                val newUserReport = Report(
                                    reason,
                                    subjectReport,
                                    clickedID,
                                    "TODO"
                                )
                                onAdd(newUserReport)
                                navController.popBackStack()
                            }
                            confirmReport = false
                            reason = ""
                        }
                    )
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun UserReportScreenPreview() {
        val navController = rememberNavController()
        UserReportScreen().UserReportReason(
            navController,
            "subjectReport",
            "clickedID",
            onAdd = {}
        )
    }
}