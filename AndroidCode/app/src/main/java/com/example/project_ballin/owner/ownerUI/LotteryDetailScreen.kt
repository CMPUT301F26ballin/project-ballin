package com.example.project_ballin.owner.ownerUI

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import com.example.project_ballin.lottery.Lottery
import com.example.project_ballin.reusableui.TopAppBar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.project_ballin.Campsite
import com.example.project_ballin.entrant.entantui.DateRangePicker
import kotlin.uuid.Uuid

class LotteryDetailScreen() {
    /**
     * the screen to create or update a lotterys details.
     * Edits a lottery if passed one,
     * otherwise allows entering details of a new lottery to create
     *
     * @param navController the navController of the app
     * @param lottery the Lottery to edit, or null if creating a new one
     * @param ownerID the ID of the owner of the lottery
     * @param ownerSites the list of campsites owned by the owner
     * @param getCampsite the function to get a campsite by ID
     * @param addLottery the function to add a new lottery
     * @param updateLottery the function to update an existing lottery
     */
    @SuppressLint("NotConstructor")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun LotteryDetailScreen(
        navController: NavController,
        lottery: Lottery?, // null if creating a new lottery
        ownerID: String,
        ownerSites: List<Campsite>,
        getCampsite: (String) -> Campsite?,
        addLottery: (Lottery) -> Unit,
        updateLottery: (oldLottery: Lottery, newLottery: Lottery) -> Unit
    ) {
        val registrationDateState = rememberDateRangePickerState(
            initialSelectedStartDateMillis = lottery?.registrationStart,
            initialSelectedEndDateMillis = lottery?.registrationEnd
        )
        val bookingDateState = rememberDateRangePickerState(
            initialSelectedStartDateMillis = lottery?.bookingStart,
            initialSelectedEndDateMillis = lottery?.bookingEnd
        )
        var occupancy by remember { mutableStateOf(lottery?.occupancy?.toString() ?: "") }
        var numWinners by remember { mutableStateOf(lottery?.numWinners?.toString() ?: "") }
        var selectedSiteIDs by remember { mutableStateOf(lottery?.associatedSites ?: listOf()) }

        TopAppBar(
            navController,
            if (lottery == null) "New Lottery Details" else "Edit Lottery Details"
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(onClick = {
                        // confirms the lottery details are valid
                        // and either creates a new lottery or updates an existing one

                        val regStart = registrationDateState.selectedStartDateMillis
                        val regEnd = registrationDateState.selectedEndDateMillis
                        val bookStart = bookingDateState.selectedStartDateMillis
                        val bookEnd = bookingDateState.selectedEndDateMillis
                        val occ = occupancy.toIntOrNull()
                        val winners = numWinners.toIntOrNull()
                        val sites = selectedSiteIDs

                        if (
                            regStart == null ||
                            regEnd == null ||
                            bookStart == null ||
                            bookEnd == null ||
                            occ == null ||  //waitlist capacity is allowed to be null
                            winners == null ||
                            sites.isEmpty() ||
                            regEnd < regStart ||
                            bookEnd < bookStart ||
                            bookStart < regEnd // registration still active while booking started
                        ) {
                            return@Button
                            // invalid lottery details
                            // TODO alert user of the error
                        }

                        // TODO book the availability of the campsites selected

                        // start with the existing lottery or make a new one
                        val baseLottery = lottery ?: Lottery(
                            lotteryID = Uuid.random().toString(),
                            lotteryOwnerID = ownerID
                        )
                        val newLottery = baseLottery.copy(
                            registrationStart = regStart,
                            registrationEnd = regEnd,
                            bookingStart = bookStart,
                            bookingEnd = bookEnd,
                            occupancy = occ,
                            numWinners = winners,
                            associatedSites = sites.toMutableList()
                        )
                        if (lottery == null) {
                            addLottery(newLottery)
                        } else {
                            updateLottery(lottery, newLottery)
                        }
                        navController.popBackStack()
                    }) {
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
                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .border(1.dp, Color.Gray)
                        .padding(8.dp)
                ) {
                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                        Text("Selected Campsites")
                        if (selectedSiteIDs.isEmpty()) Text("No sites selected")
                        selectedSiteIDs.forEach { id ->
                            val campsite = getCampsite(id)
                            if (campsite != null) Text("${campsite.name} - ${campsite.description}")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text("Campsites")

                if (ownerSites.isEmpty()) Text("You don't have any campsites")
                else {
                    ownerSites.forEach {
                        val isSelected = selectedSiteIDs.contains(it.ID)

                        CampsiteSelectable(
                            campsite = it, isSelected = isSelected,
                            onClick = {
                                selectedSiteIDs = if (isSelected) selectedSiteIDs - it.ID
                                else selectedSiteIDs + it.ID
                            }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }

    /**
     * A single row of the campsite list, click to select it
     *
     * @param campsite the campsite of the entry
     * @param isSelected whether the campsite is selected
     * @param onClick what to do when clicked
     */
    @Composable
    fun CampsiteSelectable(
        campsite: Campsite,
        isSelected: Boolean,
        onClick: () -> Unit
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .border(2.dp, if (isSelected) Color.Blue else Color.Black)
                .clickable { onClick() }
                .padding(16.dp)
        ) {
            Column {
                Text(campsite.name)
                Text(campsite.description)

                // TODO actually check if the campsite is available
                Text("\nCampsite available for this booking window✅")
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
    fun DateRangeSelectionButton(label: String, state: DateRangePickerState) {
        var showPicker by remember { mutableStateOf(false) }

        val start = state.selectedStartDateMillis
        val end = state.selectedEndDateMillis
        val text = if (start != null && end != null) "${formatDateMillisToDMY(start)} to ${
            formatDateMillisToDMY(end)
        }"
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
    fun formatDateMillisToDMY(millis: Long): String {
        val format = java.text.SimpleDateFormat("dd/MM/yyyy")
        format.timeZone = java.util.TimeZone.getTimeZone("UTC")
        return format.format(java.util.Date(millis))
    }
}