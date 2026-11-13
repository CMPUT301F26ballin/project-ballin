package com.example.project_ballin.reusableui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

class PolicyGuidelinesScreen {
    @Composable
    fun PolicyGuidelines(
        navController: NavController
    ) {
        TopAppBar(
            navController = navController,
            "Policy Guidelines"
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Fire-Ballin Policies",
                        fontSize = 24.sp
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                val policyText = """
                1. All information provided by users must be accurate and truthful
                2. Offensive language,actions, and content are prohibited
                3. Users must not impersonate another person
                4. Entrants can't manipulate,and abuse lotteries/lottery system
                5. Entrants must obey the campsite rules set by owners
                6. Owners can't make duplicate listings of campsites
                7. Owners must only post relevant promotional images for listings
                8. Owners must ensure accurate and up-to-date campsite details
                  and availabilities
                9. Violation of policies may result in account deletion by administration
            """.trimIndent()

                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(policyText)
                }
                Spacer(modifier = Modifier.height(34.dp))

                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Fire-Ballin Guidelines",
                        fontSize = 24.sp
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                val guidelinesText = """
                1. Respect other users (admin, owner, entrant)
                2. Cancel reservations promptly if you are unable to attend
                3. Keep profile information up-to-date
                4. Only join lotteries you are interested in
                5. Read the rules and cancellation policy of each campsite beforehand
            """.trimIndent()

                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(guidelinesText)
                }


            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun PolicyGuidelinesScreenPreview() {
        val navController = rememberNavController()
        PolicyGuidelines(navController)
    }
}