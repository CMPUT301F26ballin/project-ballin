package com.example.project_ballin.entrant.entantui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.util.Calendar
import java.util.TimeZone

fun utcMillis(year: Int, month: Int, day: Int): Long =
    Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
        clear()
        set(year, month - 1, day)
    }.timeInMillis

@Composable
fun DateRangePicker(
    availableDates: List<Long>,
    bookingYear: Int,
    chosenDates: (startMillis: Long, endMillis: Long) -> Unit
) {
    val available = remember(availableDates) {
        availableDates.toSet() }
    val firstAvailable = remember(availableDates) {
        availableDates.minOrNull() }

    val state = rememberDateRangePickerState(
        initialDisplayedMonthMillis = firstAvailable,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean =
                utcTimeMillis in available

            override fun isSelectableYear(year: Int): Boolean =
                year == bookingYear
        }
    )

    val start = state.selectedStartDateMillis
    val end = state.selectedEndDateMillis

    val rangeIsAvailable = start != null && end != null &&
            generateSequence(start) { it + 24 * 60 * 60 * 1000 }
                .takeWhile { it <= end }//put all dates in range into a list
                .all { it in available }//Check if all dates are available

    Column(modifier = Modifier.fillMaxSize()) {
        DateRangePicker(
            state = state,
            modifier = Modifier.weight(1f)
        )

        if (rangeIsAvailable) {
            Button(
                onClick = {
                    chosenDates(start, end)
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Confirm Dates")
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun CalendarPreview() {
    DateRangePicker(
        availableDates = listOf(
            utcMillis(2027, 3, 19),
            utcMillis(2027, 3, 20),
            utcMillis(2027, 3, 21),
            utcMillis(2027, 3, 31)
        ),
        bookingYear = 2027,
        chosenDates = { _, _ -> }
    )
}