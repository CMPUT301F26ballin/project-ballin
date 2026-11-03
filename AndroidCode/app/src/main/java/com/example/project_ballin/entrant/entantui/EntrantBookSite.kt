package com.example.project_ballin.entrant.entantui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.project_ballin.TopAppBar

@Composable
fun EntrantBookSite (navController: NavController) {
    var chosenDates by remember { mutableStateOf<List<Long>?>(null) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        TopAppBar(
            navController,
            "Book Site"
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                BookingDateRangePicker(
                    availableDates = listOf(
                        utcMillis(2027, 3, 19),
                        utcMillis(2027, 3, 20),
                        utcMillis(2027, 3, 21),
                        utcMillis(2027, 3, 31)
                    ),
                    bookingYear = 2027,
                    modifier = Modifier.weight(1f),
                    chosenDates = { chosenDates = it }
                )

                DeclineBookButtons(chosenDates)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EntrantBookSitePreview() {
    val navController = rememberNavController()
    EntrantBookSite(navController)
}