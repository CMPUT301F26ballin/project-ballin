package com.example.project_ballin

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore


/**
 * The screen to view all of the owners lotteries
 * and the add lottery button.
 *
 * @param navController the navController of the app
 * @param ownerID the ID of the owner user accessing the list
 *
 * */
@Composable
fun LotteryListScreen(
    navController: NavController,
    ownerID: Int
){
    val lotteries = remember { mutableStateListOf<Lottery>() }

    TopAppBar(navController, "Your Campsites"){
        LotteryListView(navController, lotteries)
    }
}


/**
 * Holds the list of all of the lotteries
 * and the new lottery button
 * @param navController the navController of the app
 * @param lotteries the list of lotteries to display for this owner
 */
@Composable
fun LotteryListView(
    navController: NavController,
    lotteries: List<Lottery>){
    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        item {
            Button(onClick = { navController.navigate("new lottery") } )
            {
                Text("Create new lottery")
            }
            Spacer(modifier = Modifier.height(16.dp))
        }


        itemsIndexed(lotteries) { index, lottery ->
            LotteryItem(
                lottery,
                onClick = { navController.navigate("lotteryDetailScreen/${lottery.id}") })

        }
    }
}

/**
 * A single row of the lottery list
 *
 * @param lottery the lottery of the entry,
 * @param onClick what to do when clicked
 * */

@Composable
fun LotteryItem(
    lottery: Lottery,
    onClick: () -> Unit)
{
    Row(
        modifier = Modifier.
            fillMaxWidth().
            clickable{ onClick() }.
            padding(16.dp)
    ) {
        // information about the lottery
        // site pic, reservation window, etc
        Text(lottery.getLotteryDrawNumber().toString())
    }
}