package com.example.project_ballin

import android.graphics.Bitmap
import android.graphics.Color
import androidx.core.graphics.createBitmap
import androidx.core.graphics.set
import com.google.firebase.firestore.Blob
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import java.io.ByteArrayOutputStream


/**
 * Class that creates a qr Code for an item
 *
 * */

class QRCodeGenerator {

    /**
     * A function that takes a plain text string and encodes it into a qr code blob.
     * this code can then be scanned. In theory, this plain text item should
     * be used as a navController destination for the campsite.
     *
     * Ref:
     * Title: Android QR Code Generator and Scanner with Kotlin and Jetpack Compose — Step-by-Step Guide
     * Date: 18/08/2026
     * Author: Andres Cruz
     * https://www.desarrollolibre.net/blog/android/how-to-create-a-qr-code-reader-on-android-with-android-studio
     *
     *
     *
     */

    fun encodeQRBlobFromString(input: String): Blob {
        val writer = QRCodeWriter()
        val bitMatrix = writer.encode(input, BarcodeFormat.QR_CODE, 512, 512)
        val bitmap = createBitmap(512, 512, Bitmap.Config.RGB_565)
        for (x in 0 until 512) {
            for (y in 0 until 512) {
                bitmap[x, y] = if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE
            }
        }
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val newByteArray = baos.toByteArray()
        val returnBlob = Blob.fromBytes(newByteArray)
        return returnBlob

    }
}