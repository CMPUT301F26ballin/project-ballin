package com.example.project_ballin

import com.google.firebase.firestore.Blob


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
     */

    fun encodeQRBlobFromString(input: String): Blob {
        val multiFormatWriter = MultiFormatWriter()

}