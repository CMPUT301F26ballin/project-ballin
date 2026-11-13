package com.example.project_ballin.reusableui

import androidx.compose.foundation.background
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.navigation.NavController

class GeneralReportButton {

    /**
     * A button that allows the user to report
     * something, that will go to admin users to review.
     *
     * @param subjectReport the subject of the report
     * @param clickedID the ID of the clicked item that is being reported
     * @param navController the navController Object
     */
    @Composable
    fun ReportButton(
        subjectReport: String,
        clickedID: String,
        navController: NavController
    ) {
        Button(
            onClick = {
                navController.navigate(
                    "UserReportScreen/${subjectReport}/${clickedID}"
                )
            },
            modifier = Modifier
                .testTag("reportButton")
                .background(Color.Red)
        ){
            Text("Report")
        }
    }
}