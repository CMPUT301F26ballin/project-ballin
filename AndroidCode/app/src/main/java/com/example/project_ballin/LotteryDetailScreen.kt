package com.example.project_ballin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController

@Composable
fun LotteryDetailScreen(
    navController: NavController,
    ownerID: Int
){
    val lotteries = remember { mutableStateListOf<Lottery>() }

    TopAppBar(navController, "Your Campsites"){

    }
}