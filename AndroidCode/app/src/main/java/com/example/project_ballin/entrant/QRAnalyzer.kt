package com.example.project_ballin.entrant

import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

/**
 * A class built to analyze QR codes (used in conjunction with the QRCodeScanner screen)
 *
 * @param onQRDetected the function (defined in the QRCodeScanner Screen) that says what to do
 * when a qr code is seen
 *
 * Sources:
 *      * Title: Android QR Code Generator and Scanner with Kotlin and Jetpack Compose — Step-by-Step Guide
 *      * Date: 18/08/2026
 *      * Author: Andres Cruz
 *      * https://www.desarrollolibre.net/blog/android/how-to-create-a-qr-code-reader-on-android-with-android-studio
 * */

class QRAnalyzer(private val onQRDetected: (String) -> Unit): ImageAnalysis.Analyzer {

    private val scanner = BarcodeScanning.getClient(
        BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .build()
    )

    @OptIn(ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if(mediaImage != null){
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    if(barcodes.isNotEmpty()){
                        barcodes.firstOrNull()?.rawValue?.let {rawValue ->
                            onQRDetected(rawValue)
                        }
                    }
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }

        }
        else{
            imageProxy.close()
        }
    }

}