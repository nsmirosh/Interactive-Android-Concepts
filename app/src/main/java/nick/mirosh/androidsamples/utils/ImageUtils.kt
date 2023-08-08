package nick.mirosh.androidsamples.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.FileInputStream
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL


fun downloadImage(
    context: Context,
    imageUrl: String,
    imageName: String
): Bitmap? {
    try {
        val url = URL(imageUrl)
        val connection = url.openConnection() as HttpURLConnection
        connection.doInput = true
        connection.connect()

        val inputStream = connection.inputStream
        val bitmap = BitmapFactory.decodeStream(inputStream)

        // Save the image to internal storage
        saveImageToInternalStorage(context, bitmap, imageName)

        return bitmap
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}

fun saveImageToInternalStorage(
    context: Context,
    bitmap: Bitmap,
    imageName: String
) {
    try {
        val fos: OutputStream =
            context.openFileOutput(imageName, Context.MODE_PRIVATE)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos)
        fos.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun decodeImageFromInternalStorage(
    context: Context,
    imageName: String
): Bitmap? {
    return try {
        val fis: FileInputStream = context.openFileInput(imageName)
        BitmapFactory.decodeStream(fis)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
