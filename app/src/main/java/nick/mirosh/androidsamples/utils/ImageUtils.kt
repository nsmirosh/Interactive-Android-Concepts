package nick.mirosh.androidsamples.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.util.TypedValue
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import nick.mirosh.androidsamples.ui.parallax.screenHeightPx
import nick.mirosh.androidsamples.ui.parallax.screenWidthPx
import java.io.FileInputStream
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL


fun downloadImage(
    imageUrl: String,
) = try {
    val url = URL(imageUrl)
    val connection = url.openConnection() as HttpURLConnection
    connection.doInput = true
    connection.connect()

    val inputStream = connection.inputStream
    Log.d("ParallaxScreen", "downloadImage: success")
    BitmapFactory.decodeStream(inputStream)

} catch (e: Exception) {
    e.printStackTrace()
    Log.d("ParallaxScreen", "downloadImage: failure")
    null
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
        Log.d("ParallaxScreen", "saveImageToInternalStorage: success")
    } catch (e: Exception) {
        e.printStackTrace()
        Log.e("ParallaxScreen", "saveImageToInternalStorage: failure")
        Log.e("ParallaxScreen", "${e.message}")
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
        Log.d("ParallaxScreen", "decodeImageFromInternalStorage: failure")
        e.printStackTrace()
        null
    }
}

fun downloadPictureToInternalStorage(
    fileName: String,
    url: String,
    context: Context
) {
    downloadImage(
        imageUrl = url,
    )?.let {
        saveImageToInternalStorage(context = context, bitmap = it, imageName = fileName)
    }
}

fun decodeRawResource(resources: Resources, pictureId: Int): Bitmap? {
    val opts = BitmapFactory.Options().apply {
        inScaled =
            false  // ensure the bitmap is not scaled based on device density
    }
    val inputStream = resources.openRawResource(pictureId)
    return BitmapFactory.decodeResourceStream(
        resources,
        TypedValue(),
        inputStream,
        null,
        opts
    )
}

suspend fun loadPictures(
    pictureUrls: List<String>? = null,
    pictureIds: List<Int>? = null,
    context: Context,
) =
    withContext(Dispatchers.IO) {
        val deferreds = mutableListOf<Deferred<Bitmap>>()
        pictureUrls?.forEach { url ->
            deferreds + async {
                val fileName = "img_${System.currentTimeMillis()}"
                downloadPictureToInternalStorage(
                    fileName = fileName,
                    url = url,
                    context = context,
                )
                val bitmap = decodeImageFromInternalStorage(
                    context,
                    fileName
                )
                Log.d("ParallaxScreen", "bitmap.size = ${bitmap?.byteCount} bytes")
                bitmap
            }
        } ?: pictureIds?.forEach {
            deferreds + async {
                decodeRawResource(
                    context.resources, it
                )
                Log.d("ParallaxScreen", "loadPictures: $it decoded")
            }
        }
        deferreds.awaitAll()
    }


