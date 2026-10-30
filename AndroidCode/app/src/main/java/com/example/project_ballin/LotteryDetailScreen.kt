package com.example.project_ballin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LotteryDetailScreen(
    navController: NavController,
    lotteryID: String
){
    val dateState = rememberDateRangePickerState()

    TopAppBar(navController, "Lottery Details"){
        Column {
            DateRangePicker(state = dateState, modifier = Modifier.weight(1f))
            Text("${dateState.selectedStartDateMillis} to ${dateState.selectedEndDateMillis}")
        }
    }
}