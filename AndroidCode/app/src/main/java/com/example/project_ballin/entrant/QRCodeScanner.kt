package com.example.project_ballin.entrant

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.liveData
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.project_ballin.reusableui.TopAppBar
import com.google.firebase.firestore.util.Executors
import java.util.jar.Manifest

/**
 *The view class that allows users to scan campsite QR codes.
 * */
class QRCodeScanner(){
    /**
     * The QR code scanner page
     * Will allow entrants to scan QR codes to view campsite details
     * This method specifically gets camera permission from the user
     * before displaying the camera
     *
     * @param navController the navigation controller for the app
     *
     * Sources:
     * Title: Android QR Code Generator and Scanner with Kotlin and Jetpack Compose — Step-by-Step Guide
     * Date: 18/08/2026
     * Author: Andres Cruz
     * https://www.desarrollolibre.net/blog/android/how-to-create-a-qr-code-reader-on-android-with-android-studio
     *
     */
    @SuppressLint("NotConstructor")
    @Composable
    fun QRCodeScanner(navController: NavController, userID: String) {
        TopAppBar(
            navController = navController,
            screenTitle = "Scan QR Code"
        ) {
            val context = LocalContext.current
            val lifecycle = LocalLifecycleOwner.current
            var hasCameraPermission by remember { mutableStateOf(false) }
            var qrCodeResult by remember { mutableStateOf("Scanning...") }
            var foundQR by remember { mutableStateOf(false) }

            val permissionLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission(),
                onResult = {isGranted ->
                    hasCameraPermission = isGranted
                    if(!isGranted){
                        qrCodeResult = "Camera Permission Denied"
                    }
                }
            )

            LaunchedEffect(Unit) {
                when{
                    ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED -> {
                        hasCameraPermission = true
                    }
                    else -> {
                        permissionLauncher.launch(android.Manifest.permission.CAMERA)
                    }
                }
            }

            Column(modifier = Modifier.fillMaxSize()) {
                if(hasCameraPermission){
                    cameraPreview(onQRDetected = { result ->
                        qrCodeResult = result
                        foundQR = true
                    })
                    Spacer(modifier = Modifier.height(16.dp))
                    if(foundQR){
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                navController.navigate("CampsiteDetailScreen/$qrCodeResult/$userID")
                            }) {
                            Text("Go to Campsite")
                        }
                    }

                }
                else{
                    Text("Camera permission is needed to scan QR.", modifier = Modifier.padding(16.dp))
                    Button(onClick = { permissionLauncher.launch(android.Manifest.permission.CAMERA) }) {
                        Text("Grant Permission")
                    }
                }
            }

        }
    }

    @Composable
    fun cameraPreview(onQRDetected: (String) -> Unit){
        val context = LocalContext.current
        val lifecycleOwner = LocalLifecycleOwner.current
        val cameraExecutor = remember { java.util.concurrent.Executors.newSingleThreadExecutor() }
        AndroidView(
            modifier = Modifier
                .fillMaxWidth(),

            factory = {ctx ->
                val previewView = PreviewView(ctx)
                val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
                cameraProviderFuture.addListener({
                    val cameraProvider = cameraProviderFuture.get()
                    val preview = androidx.camera.core.Preview.Builder().build().also{
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }

                    val imageAnalyzer = ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build()
                        .also {
                            it.setAnalyzer(cameraExecutor, QRAnalyzer(onQRDetected))
                        }

                    val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                    try {
                        cameraProvider.unbindAll()
                        cameraProvider.bindToLifecycle(
                            lifecycleOwner,
                            cameraSelector,
                            preview,
                            imageAnalyzer
                        )
                    } catch (exc: Exception){

                    }
                }, ContextCompat.getMainExecutor(ctx))
                previewView
            }
        )
        DisposableEffect(Unit) {
            onDispose {
                cameraExecutor.shutdown()
            }
        }
    }


    @Preview(showBackground = true)
    @Composable
    fun QRCodeScannerPreview() {
        val navController = rememberNavController()
        QRCodeScanner(navController,"1")
    }
}
