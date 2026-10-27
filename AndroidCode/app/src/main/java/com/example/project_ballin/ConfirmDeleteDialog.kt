package com.example.project_ballin

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview

/**
 * Pop-up asking the admin to confirm a delete. Works for anything (profiles, campsites, images).
 *
 * @param itemDescription what will be deleted, e.g. "the profile of John"
 * @param onConfirm runs when Delete is pressed
 * @param onCancel runs when Cancel is pressed
 */
@Composable
fun ConfirmDeleteDialog(
    itemDescription: String,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text("Delete?") },
        text = { Text("Are you sure you want to delete $itemDescription? This can't be undone.") },
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                modifier = Modifier.testTag("confirmDelete") // for UI testing
            ) {
                Text("Delete")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onCancel,
                modifier = Modifier.testTag("cancelDelete") // for UI testing
            ) {
                Text("Cancel")
            }
        },
        modifier = Modifier.testTag("confirmDeleteDialog")
    )
}

@Preview(showBackground = true)
@Composable
fun ConfirmDeleteDialogPreview() {
    ConfirmDeleteDialog("the profile of John Ballin", onConfirm = {}, onCancel = {})
}
