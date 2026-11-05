package com.example.project_ballin.entrant.entantui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class ConfirmActionDialog {
    /**
     * The confirm action dialog
     * Allows the user to confirm an action
     *
     * @param message the message to display
     * @param confirm the function to run when the user confirms
     */
    @Composable
    fun ConfirmAction(message: String, confirm: (Boolean) -> Unit){

        AlertDialog(
            onDismissRequest = { confirm(false) },
            title = { Text("Confirm") },
            text = { Text(message) },
            confirmButton = {
                TextButton(
                    onClick = { confirm(true) },
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {confirm(false)},
                    modifier = Modifier
                ) {
                    Text("Cancel")
                }
            },
            modifier = Modifier.testTag("confirmDeleteDialog")
        )
    }

    @Composable
    @Preview
    fun ConfirmActionPreview(){
        var action = false
        ConfirmAction("Are you sure you want to delete this campsite?", {action})
    }
}