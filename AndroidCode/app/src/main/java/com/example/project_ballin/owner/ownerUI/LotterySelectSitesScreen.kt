package com.example.project_ballin.owner.ownerUI

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.project_ballin.Campsite
import com.example.project_ballin.lottery.Lottery
import com.example.project_ballin.reusableui.TopAppBar
/*
@Composable
fun LotterySelectCampsitesScreen(
    navController: NavController,
    ownerID: String,
    getOwnerLotteries: (String) -> List<Lottery>
)
{
    val campsite = getOwnerCampsites(ownerID)

    TopAppBar(navController, "Select Campsites"){

    }
}

 */