package com.example.project_ballin.entrant.entantui

import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import java.util.Calendar
import java.util.TimeZone

fun utcMillis(year: Int, month: Int, day: Int): Long =
    Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
        clear()
        set(year, month - 1, day)
    }.timeInMillis

/**
 * Shows a date range picker that only allows the given dates.
 *
 * @param availableDates UTC-midnight millis of the dates that can be booked
 * @param bookingYear the only year that can be selected
 * @param onValidRangeChange called whenever the selection changes. Receives (start, end) in
 * UTC millis when a range is selected and every day is available, otherwise null.
 *
 * Sources used:
 * https://developer.android.com/develop/ui/compose/components/datepickers
 *
 * Title: Date Picker in Material 3 Jetpack Compose (with Examples)
 * Author: Kumar Komati
 * Published: Dec 1, 2024
 * https://towardsdev.com/date-picker-in-material-3-jetpack-compose-with-examples-ed0a939c8b80?sk=5002ed239ae7846ec511540bba625a6b
 */
@Composable
fun BookingDateRangePicker(
    availableDates: List<Long>,
    bookingYear: Int,
    modifier: Modifier = Modifier,
    chosenDates: (List<Long>?) -> Unit
) {
    val available = remember(availableDates) { availableDates.toSet() }
    val firstAvailable = remember(availableDates) { availableDates.minOrNull() }

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
                .takeWhile { it <= end }
                .all { it in available }

    chosenDates(
        if (rangeIsAvailable) listOf(start, end)
        else null
    )


    DateRangePicker(
        state = state,
        showModeToggle = false,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun CalendarPreview() {
    BookingDateRangePicker(
        availableDates = listOf(
            utcMillis(2027, 3, 19),
            utcMillis(2027, 3, 20),
            utcMillis(2027, 3, 21),
            utcMillis(2027, 3, 31)
        ),
        bookingYear = 2027,
        chosenDates = { }
    )
}