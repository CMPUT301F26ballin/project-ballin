package com.example.project_ballin.entrant.entantui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

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
            onDismissRequest = {confirm(false)},
            title = { Text("Confirm?") },
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
                    onClick = { confirm(false) },
                ) {
                    Text("Cancel")
                }
            },
        )
    }

    @Composable
    @Preview
    fun ConfirmActionPreview(){
        var action = false
        ConfirmAction("Are you sure you want to delete this campsite?", {action})
    }
}