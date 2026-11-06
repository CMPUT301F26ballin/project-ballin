package com.example.project_ballin

import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * The calendar composable used to select dates to add or remove from camp's availability
 *
 * @param campAvailableDates the current dates the campsite is available. If dates are being added, these dates will be unavailable
 * if the dates are being deleted, these dates will be available.
 * @param onDismiss the function that dismisses the pop-up.
 * @param onSelect the function that determines what happens with the given dates.
 * @param adding A boolean that tells the composable whether dates are being added or removed.
 *
 * Sources Used:
 *
 * Title: Date Pickers
 * https://developer.android.com/develop/ui/compose/components/datepickers
 *
 * Title: Date Picker in Material 3 Jetpack Compose (with Examples)
 * Author: Kumar Komati
 * Published: Dec 1, 2024
 * https://towardsdev.com/date-picker-in-material-3-jetpack-compose-with-examples-ed0a939c8b80?sk=5002ed239ae7846ec511540bba625a6b
 * */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePick(campAvailableDates: List<Long>, onDismiss:() -> Unit, onSelect:(List<Long>)->Unit, adding: Boolean){

    var dateState: DateRangePickerState
    if(adding){
        dateState = rememberDateRangePickerState(
            initialDisplayedMonthMillis = System.currentTimeMillis(),
            selectableDates = object : SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long): Boolean =
                    utcTimeMillis !in campAvailableDates

            }
        )
    }
    else{
        dateState = rememberDateRangePickerState(
            initialDisplayedMonthMillis = System.currentTimeMillis(),
            selectableDates = object : SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long): Boolean =
                    utcTimeMillis in campAvailableDates

            }
        )
    }



    val startDate = dateState.selectedStartDateMillis
    val endDate = dateState.selectedEndDateMillis


    DatePickerDialog(
        onDismissRequest = {onDismiss()},
        confirmButton = {
            TextButton(onClick = {
                if(startDate != null && endDate != null){
                    val dateList = mutableListOf<Long>()
                    var listBuilder = startDate
                    while(listBuilder <= endDate){
                        dateList.add(listBuilder)
                        listBuilder += 24 * 60 * 60 * 1000
                    }

                    onSelect(dateList)
                    onDismiss()}}
            ) {
                Text("Confirm")
            }

        },
        dismissButton = { TextButton(onClick = {onDismiss()}){
            Text("Cancel")
        }
        }

    ){
        DateRangePicker(
            state = dateState,
            showModeToggle = false,
            modifier = Modifier
        )

    }
}