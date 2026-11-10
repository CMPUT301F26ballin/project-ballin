package com.example.project_ballin.reusableui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.project_ballin.R
import com.example.project_ballin.ui.theme.BallinDarkGreen
import com.example.project_ballin.ui.theme.BallinWhite
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

class InitialScreen {

    /**
     * The first screen a user will see, when loading the app
     * It launches and waits for # of seconds
     * before navigating to the sign-up screen
     *
     * Sources used:
     * Title: Loading images
     * Link: https://developer.android.com/develop/ui/compose/graphics/images/loading
     */
    @Composable
    fun Initial(navController: NavController) {

        Column(
            modifier = Modifier.fillMaxSize()
                .background(BallinDarkGreen),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(450.dp)
                    .background(BallinWhite),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Image(
                    painter = painterResource(id = R.drawable.fire_ballin_logo),
                    contentDescription = "fire-ballin logo",
                    modifier = Modifier
                        .width(175.dp)
                        .height(175.dp)
                )
            }

            LaunchedEffect(Unit) {
                delay(2000.milliseconds)
                navController.navigate("signup")
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun InitialPreview() {
        val navController = rememberNavController()
        Initial(navController)
    }
}
