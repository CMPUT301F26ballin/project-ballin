package com.example.project_ballin.entrant

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.project_ballin.reusableui.TopAppBar

/**
 *The view class that allows users to scan campsite QR codes.
 * */
class QRCodeScanner(){
    /**
     * The QR code scanner page
     * Will allow entrants to scan QR codes to view campsite details
     *
     * @param navController the navigation controller for the app
     */
    @SuppressLint("NotConstructor")
    @Composable
    fun QRCodeScanner(navController: NavController) {
        TopAppBar(
            navController = navController,
            screenTitle = "Scan QR Code"
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "QR Code Scanner",
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Scan a campsite QR code to view details.",
                    fontSize = 16.sp
                )
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun QRCodeScannerPreview() {
        val navController = rememberNavController()
        QRCodeScanner(navController)
    }
}
