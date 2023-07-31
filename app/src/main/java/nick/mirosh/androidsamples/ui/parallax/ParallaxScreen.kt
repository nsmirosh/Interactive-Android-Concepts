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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import nick.mirosh.androidsamples.R
import kotlin.math.abs


private const val TAG = "ParallaxScreen"


// [ ] - Make the picture scroll the whole height
// [x] - Implement picture scrolling in the opposite direction
// height of the picture / no of non-visible items left in the column (i.e. how much left to scroll)


// picture scroll = rest of the height of the picture / rest of the scroll left in the column
// rest of the height of the picture =  pictureHeight - heighOfTheCardInPixels
// rest of the scroll left in the column = screenHeight - card height * no of items in the column
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

    val configuration = LocalConfiguration.current
    val screenHeightDp = configuration.screenHeightDp
    val density = LocalDensity.current.density
    val screenHeightPx = screenHeightDp * density

    val initialUnscrolledPartOfThePicture = 1920 - cardHeight
    Log.d(
        TAG,
        "ScrollableColumn: unscrolledPartOfThePicture $initialUnscrolledPartOfThePicture"
    )
    val noOfItems = 7
    val initialScrollLeftInColumn = abs(screenHeightPx - cardHeight * noOfItems)

    Log.d(
        TAG,
        "ScrollableColumn: scrollLeftInColumn $initialScrollLeftInColumn"
    )
    val pictureYMovementRatioPx =
        initialUnscrolledPartOfThePicture / initialScrollLeftInColumn
    Log.d(TAG, "ScrollableColumn: yMovementRatio $pictureYMovementRatioPx")

    val amountOfPixelsPictureShouldBeScrolledBy = screenHeightPx / cardHeight
    Log.d(
        TAG,
        "ScrollableColumn: item $amountOfPixelsPictureShouldBeScrolledBy"
    )
    var prevScrollValue by remember { mutableIntStateOf(0) }
    val columnScrollFromTopInPx =
        columnScrollState.value
    prevScrollValue = columnScrollState.value
    Log.d(
        TAG,
        "ScrollableColumn: yMovement $columnScrollFromTopInPx, prevScrollValue $prevScrollValue"
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(columnScrollState),
    ) {
        InvertedCard(
            originalBitmap = bitmap,
            cardHeight = cardHeight,
            totalColumnScrollFromTop = columnScrollFromTopInPx,
            yMovementRatioPx = pictureYMovementRatioPx
        )
        RenderingCard(
            originalBitmap = bitmap,
            cardHeight = cardHeight,
            totalColumnScrollFromTop = columnScrollFromTopInPx
        )
        InvertedCard(
            originalBitmap = bitmap,
            cardHeight = cardHeight,
            totalColumnScrollFromTop = columnScrollFromTopInPx,
            yMovementRatioPx = pictureYMovementRatioPx
        )
        RenderingCard(
            originalBitmap = bitmap,
            cardHeight = cardHeight,
            totalColumnScrollFromTop = columnScrollFromTopInPx
        )
        InvertedCard(
            originalBitmap = bitmap,
            cardHeight = cardHeight,
            totalColumnScrollFromTop = columnScrollFromTopInPx,
            yMovementRatioPx = pictureYMovementRatioPx
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
    totalColumnScrollFromTop: Int = 0,
    yMovementRatioPx: Float
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
                        calculateYOffset(
                            totalColumnScrollFromTop,
                            cardHeight
                        ),
//                        ((1920f - (totalColumnScrollFromTop.toFloat() * yMovementRatioPx))).toInt(),
//                        1920 - (totalColumnScrollFromTop * 30) / 10,
                        width,
                        cardHeight
                    )
                canvas.nativeCanvas.drawBitmap(newBitmap, 0f, 0f, null)
            }
        }
    }
}

fun calculateYOffset(
    totalColumnScrollFromTop: Int,
    cardHeight: Int,
    pictureHeight: Int = 2880
) =
    //there is no scroll yet
    if (totalColumnScrollFromTop <= 0) {
        Log.d(TAG, "calculateYOffset: pictureHeight - cardHeight ${pictureHeight - cardHeight}")
        pictureHeight - cardHeight
    }
    else {
        pictureHeight - cardHeight - (totalColumnScrollFromTop  * 5) / 10
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

@Composable
fun OuterClickCounter() {
    Column {
        var outerClicks by remember { mutableIntStateOf(0) }
        Button(onClick = { outerClicks++ }) {
            Text("Outer click trigger")
        }
        InnerClickCounter(outerClicks)
    }
}

@Composable
fun InnerClickCounter(outerClicks: Int) {
    var innerClicks by mutableIntStateOf(0)
    Column {
        Button(onClick = {
            innerClicks++
        })
        {
            Text("Inner clicks = $innerClicks")
        }
        Text("Outer clicks= $outerClicks")
    }
}
