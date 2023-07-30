package nick.mirosh.androidsamples.ui.parallax

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.util.TypedValue
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import nick.mirosh.androidsamples.R


private const val TAG = "ParallaxScreen"


// [ ] - Make the picture scroll the whole height
// [ ] - Implement picture scrolling in the opposite direction
@Composable
fun ParallaxScreen() {
    var bitmap by remember {
        mutableStateOf<Bitmap?>(null)
    }
    val resources = LocalContext.current.resources

    LaunchedEffect(Unit) {
        bitmap = decodeBitmap(resources)
    }
    bitmap?.let { ScrollableColumn(it) }
}

@Composable
fun ScrollableColumn(bitmap: Bitmap) {
    val columnScrollState = rememberScrollState()
    val cardHeight = with(LocalDensity.current) { 200.dp.roundToPx() }
    var prevScrollValue by remember { mutableIntStateOf(0) }
//    val yMovement =
//        columnScrollState.value - prevScrollValue
    val yMovement =
        columnScrollState.value
    prevScrollValue = columnScrollState.value
    Log.d(
        TAG,
        "ScrollableColumn: yMovement $yMovement, prevScrollValue $prevScrollValue"
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(columnScrollState),
    ) {
        RenderingCard(
            originalBitmap = bitmap,
            cardHeight = cardHeight,
            totalColumnScrollFromTop = yMovement
        )
        InvertedCard(
            originalBitmap = bitmap,
            cardHeight = cardHeight,
            totalColumnScrollFromTop = yMovement
        )
        RenderingCard(
            originalBitmap = bitmap,
            cardHeight = cardHeight,
            totalColumnScrollFromTop = yMovement
        )
        InvertedCard(
            originalBitmap = bitmap,
            cardHeight = cardHeight,
            totalColumnScrollFromTop = yMovement
        )
        RenderingCard(
            originalBitmap = bitmap,
            cardHeight = cardHeight,
            totalColumnScrollFromTop = yMovement
        )
        RenderingCard(
            originalBitmap = bitmap,
            cardHeight = cardHeight,
            totalColumnScrollFromTop = yMovement
        )
    }
}

fun decodeBitmap(resources: Resources): Bitmap? {
//    val resources = LocalContext.current.resources
    val opts = BitmapFactory.Options().apply {
        inScaled =
            false  // ensure the bitmap is not scaled based on device density
    }
    val inputStream = resources.openRawResource(R.raw.picture1)
    return BitmapFactory.decodeResourceStream(
        resources,
        TypedValue(),
        inputStream,
        null,
        opts
    )
}

@Composable
fun RenderingCard(
    modifier: Modifier = Modifier,
    originalBitmap: Bitmap,
    cardHeight: Int,
    totalColumnScrollFromTop: Int = 0
) {
    Card(
        modifier = Modifier.height(200.dp)
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            drawIntoCanvas { canvas ->
                val width = originalBitmap.width
                Log.d(TAG, "GetEverythingByPixels: $width")
                val newBitmap =
                    Bitmap.createBitmap(
                        originalBitmap,
                        0,
                        (totalColumnScrollFromTop * 2) / 10,
                        width,
                        cardHeight
                    )
                canvas.nativeCanvas.drawBitmap(newBitmap, 0f, 0f, null)
            }
        }
    }
}

@Composable
fun InvertedCard(
    modifier: Modifier = Modifier,
    originalBitmap: Bitmap,
    cardHeight: Int,
    totalColumnScrollFromTop: Int = 0
) {
    Card(
        modifier = Modifier.height(200.dp)
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            drawIntoCanvas { canvas ->
                val width = originalBitmap.width
                Log.d(TAG, "GetEverythingByPixels: $width")
                val newBitmap =
                    Bitmap.createBitmap(
                        originalBitmap,
                        0,
                        1920 - (totalColumnScrollFromTop * 2) / 10,
                        width,
                        cardHeight
                    )
                canvas.nativeCanvas.drawBitmap(newBitmap, 0f, 0f, null)
            }
        }
    }
}

fun Modifier.gesturesDisabled(disabled: Boolean = true) =
    if (disabled) {
        pointerInput(Unit) {
            awaitPointerEventScope {
                // we should wait for all new pointer events
                while (true) {
                    awaitPointerEvent(pass = PointerEventPass.Initial)
                        .changes
                        .forEach(PointerInputChange::consume)
                }
            }
        }
    }
    else {
        this
    }

