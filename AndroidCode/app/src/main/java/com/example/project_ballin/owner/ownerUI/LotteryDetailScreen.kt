package com.example.project_ballin.owner.ownerUI

import android.R
import com.example.project_ballin.lottery.Lottery
import com.example.project_ballin.reusableui.TopAppBar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.project_ballin.Campsite
import com.example.project_ballin.entrant.entantui.DateRangePicker
import kotlin.uuid.Uuid

/**
 * the screen to create or update a lotterys details.
 * Edits a lottery if passed one,
 * otherwise allows entering details of a new lottery to create
 *
 * @param navController the navController of the app
 * @param lottery the Lottery to edit
 * @param addLottery the function to add a new lottery
 * @param updateLottery the function to update an existing lottery
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LotteryDetailScreen(
    navController: NavController,
    lottery: Lottery?, // null if creating a new lottery
    ownerID: String,
    ownerSites: List<Campsite>,
    addLottery: (Lottery) -> Unit,
    updateLottery: (oldLottery: Lottery, newLottery: Lottery) -> Unit
){
    val registrationDateState = rememberDateRangePickerState(
        initialSelectedStartDateMillis = lottery?.getRegistrationStart(),
        initialSelectedEndDateMillis = lottery?.getRegistrationEnd()
    )
    val bookingDateState = rememberDateRangePickerState(
        initialSelectedStartDateMillis = lottery?.getBookingStart(),
        initialSelectedEndDateMillis = lottery?.getBookingEnd()
    )
    var occupancy by remember { mutableStateOf(lottery?.getOccupancy()?.toString() ?: "") }
    var numWinners by remember { mutableStateOf(lottery?.getLotteryDrawNumber()?.toString() ?: "") }

    TopAppBar(navController, if (lottery == null) "New Lottery Details" else "Edit Lottery Details"){
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                Button(onClick = {
                    //navController.navigate("LotteryDetailScreen")
                } ) {
                    Text("Select Campsites")
                }
                Button(onClick = {
                    // confirms the lottery details are valid
                    // and either creates a new lottery or updates an existing one

                    val regStart = registrationDateState.selectedStartDateMillis
                    val regEnd = registrationDateState.selectedEndDateMillis
                    val bookStart = bookingDateState.selectedStartDateMillis
                    val bookEnd = bookingDateState.selectedEndDateMillis
                    val occ = occupancy.toIntOrNull()
                    val winners = numWinners.toIntOrNull()

                    if (
                        regStart == null ||
                        regEnd == null ||
                        bookStart == null ||
                        bookEnd == null ||
                        occ == null ||  //waitlist capacity is allowed to be null
                        winners == null ||
                        regEnd < regStart ||
                        bookEnd < bookStart ||
                        bookStart < regEnd // registration still active while booking started
                        ) {
                        return@Button
                        // invalid lottery details
                        // TODO alert user of the error
                    }
                    val newLottery = Lottery(
                        lotteryOwnerID = ownerID,
                        registrationStart = regStart,
                        registrationEnd = regEnd,
                        bookingStart = bookStart,
                        bookingEnd = bookEnd,
                        occupancy = occ,
                        numWinners = winners,
                        lotteryID = lottery?.getLotteryID() ?: Uuid.random()
                    )
                    if (lottery == null) {
                        addLottery(newLottery)
                    } else {
                        updateLottery(lottery, newLottery)
                    }
                    navController.navigate("LotteryListScreen/$ownerID")
                } ) {
                    Text("Confirm Lottery")
                }

            }
            Spacer(modifier = Modifier.height(16.dp))
            DateRangeSelectionButton("Registration Dates", registrationDateState)
            Spacer(modifier = Modifier.height(16.dp))
            DateRangeSelectionButton("Booking Dates", bookingDateState)
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = occupancy,
                onValueChange = { occupancy = it },
                label = { Text("Waitlist limit") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = numWinners,
                onValueChange = { numWinners = it },
                label = { Text("Lottery winners") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            //Text("${registrationDateState.selectedStartDateMillis} to ${registrationDateState.selectedEndDateMillis}")
        }
    }
}


/**
 * A date range selection button
 *
 * @param label the label of the button
 * @param state the state of the date range picker
 *
 * Sources used:
 * https://developer.android.com/reference/kotlin/androidx/compose/material3/DatePickerDialog.composable
 */
@Composable
fun DateRangeSelectionButton(label: String, state: DateRangePickerState){
    var showPicker by remember { mutableStateOf(false) }

    val start = state.selectedStartDateMillis
    val end = state.selectedEndDateMillis
    val text = if(start != null && end != null) "${formatDateMillisToDMY(start)} to ${formatDateMillisToDMY(end)}"
        else "Dates not set"

    Text(label)

    Button(onClick = { showPicker = true }, modifier = Modifier.fillMaxWidth()) {
        Text(text)
    }

    if (showPicker) {
        DatePickerDialog(
            onDismissRequest = { showPicker = false },
            confirmButton = {
                Button(onClick = { showPicker = false }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                Button(onClick = {
                    showPicker = false
                    state.setSelection(null, null)
                }) {
                    Text("Cancel")
                }
            }
        ) {
            DateRangePicker(
                state = state,
                showModeToggle = false,
                modifier = Modifier.height(500.dp)
            )
        }
    }
}

/**
 * Convert the date picker Long millis times to standard date format
 *
 * @param millis the millis to convert
 * @return the formatted date (dd/mm/yyyy)
 */
fun formatDateMillisToDMY(millis: Long) : String {
    val date = java.util.Date(millis)
    val format = java.text.SimpleDateFormat("dd/MM/yyyy")
    return format.format(date)
}