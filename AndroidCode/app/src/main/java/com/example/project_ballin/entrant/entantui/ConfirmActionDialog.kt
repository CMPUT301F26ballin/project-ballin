package com.example.project_ballin.entrant.entantui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),

        ){
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp, 32.dp, 16.dp, 0.dp),
                horizontalArrangement = Arrangement.Center
            ){
                Text(message, textAlign = TextAlign.Center, style = MaterialTheme.typography.titleMedium)
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.Center
            ){
                Button(
                    onClick = {
                        confirm(false)
                    },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Cancel")
                }

                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    ),
                    onClick = {
                        confirm(true)
                    },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Confirm")
                }
            }
        }
    }

    @Composable
    @Preview
    fun ConfirmActionPreview(){
        var action = false
        ConfirmAction("Are you sure you want to delete this campsite?", {action})
    }
}