package com.example.project_ballin.entrant.entantui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.project_ballin.Campsite
import com.example.project_ballin.CampsiteList
import com.example.project_ballin.entrant.Entrant
import com.example.project_ballin.lottery.Lottery
import com.example.project_ballin.reusableui.TopAppBar

class EntrantSiteSelector {

    @Composable
    fun SiteSelector(
        navController: NavController,
        campsiteList: CampsiteList,
        entrant: Entrant,
        lottery: Lottery?
    ) {

        val availableSites = campsiteList.campsites.filter { campsite ->
            lottery?.associatedSites?.contains(campsite.ID) ?: false
        }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            TopAppBar(
                navController,
                "Select Site"
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LazyColumn {
                        items(availableSites) { campsite ->
                            SiteCard(navController, entrant, campsite, lottery)
                        }
                    }
                }
            }
        }
    }


    @Composable
    fun SiteCard(
        navController: NavController,
        entrant: Entrant,
        campsite: Campsite,
        lottery: Lottery?
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = campsite.name,
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                HorizontalDivider(modifier = Modifier.padding(0.dp, 16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {

                        Text(
                            text = "Details:",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Row(
                            modifier = Modifier.padding(16.dp, 0.dp)
                        ) {
                            Text(
                                text = " - Location: ${campsite.locationName}\n" +
                                        " - Occupancy: ${campsite.occupancy}",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(0.dp, 16.dp))

                Button(
                    onClick = {
                        navController.navigate("CampsiteDetailScreen/${campsite.ID}/${entrant.userID}")

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("More Details Button")

                ) {
                    Text("More Details")
                }

                Button(
                    onClick = {
                        navController.navigate("EntrantSiteBooker/${campsite.ID}/${lottery?.lotteryID}")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("BookSiteButton")

                ) {
                    Text("Select Dates")
                }
            }
        }
    }
}
