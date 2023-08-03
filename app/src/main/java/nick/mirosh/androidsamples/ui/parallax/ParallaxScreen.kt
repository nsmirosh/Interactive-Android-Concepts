package nick.mirosh.androidsamples.ui.parallax

import android.R.attr.bitmap
import android.content.res.Configuration
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


const val PICTURE_HEIGHT = 2280
var screenWidthPx = 0
var screenHeightPx = 0

@Composable
fun ParallaxScreen() {
    var bitmap by remember {
        mutableStateOf<Bitmap?>(null)
    }
    val resources = LocalContext.current.resources
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current.density

    LaunchedEffect(Unit) {
        initScreenWidthAndHeight(configuration, density)
        decodeBitmap(resources)?.let {
            bitmap = Bitmap.createScaledBitmap(
                it,
                screenWidthPx,
                screenHeightPx,
                true
            )
        }
    }
    bitmap?.let { ScrollableColumn(it) }
}

@Composable
fun ScrollableColumn(bitmap: Bitmap) {
    val columnScrollState = rememberScrollState()
    val cardHeight = with(LocalDensity.current) { 200.dp.roundToPx() }
    Log.d(TAG, "ScrollableColumn: cardHeight $cardHeight")

    val initialUnscrolledPartOfThePicture = PICTURE_HEIGHT - cardHeight
    val noOfItems = 4
    val initialScrollLeftInColumn = abs(screenHeightPx - cardHeight * noOfItems)

    val pictureYMovementRatioPx =
        initialUnscrolledPartOfThePicture / initialScrollLeftInColumn

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
        repeat(noOfItems) {
            if (it % 2 == 0)
                RenderingCard(
                    originalBitmap = bitmap,
                    cardHeight = cardHeight,
                    totalColumnScrollFromTop = columnScrollFromTopInPx
                )
            else
                InvertedCard(
                    originalBitmap = bitmap,
                    cardHeight = cardHeight,
                    totalColumnScrollFromTop = columnScrollFromTopInPx
                )
        }
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
                val newBitmap =
                    Bitmap.createBitmap(
                        originalBitmap,
                        0,
                        totalColumnScrollFromTop,
                        originalBitmap.width,
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
    //yMovementRatioPx: Float
) {
    Card(
        modifier = Modifier.height(200.dp)
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            drawIntoCanvas { canvas ->
                val width = originalBitmap.width
                Log.d(TAG, "GetEverythingByPixels: oldBitmap.width $width")
                val height = originalBitmap.height
                Log.d(TAG, "GetEverythingByPixels: oldBitmap.height $height")
                val yOffset = calculateYOffset(
                    totalColumnScrollFromTop,
                    cardHeight,
                    height
                )
                Log.d(TAG, "InvertedCard: yOffset $yOffset")
                Log.d(TAG, "InvertedCard: original picture height = $height")
                //y + height must be <= bitmap.height()
                Log.d(TAG, "InvertedCard: y + height = ${yOffset + cardHeight}")
                Log.d(TAG, "originalBitmap.height ${originalBitmap.height}")
                val newBitmap =
                    Bitmap.createBitmap(
                        originalBitmap,
                        0,
                        yOffset,
                        width,
                        cardHeight
                    )
                Log.d(TAG, "InvertedCard: newBitmap.height ${newBitmap.height}")
                Log.d(TAG, "InvertedCard: newBitmap.width ${newBitmap.width}")
                canvas.nativeCanvas.drawBitmap(newBitmap, 0f, 0f, null)
            }
        }
    }
}

fun calculateYOffset(
    totalColumnScrollFromTop: Int,
    cardHeight: Int,
    pictureHeight: Int
) =
    if (totalColumnScrollFromTop <= 0) {
        Log.d(
            TAG,
            "calculateYOffset: pictureHeight - cardHeight - 100 ${pictureHeight - cardHeight - 100}"
        )
        pictureHeight - cardHeight - 100
    }
    else {
        pictureHeight - cardHeight - 100
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

fun initScreenWidthAndHeight(configuration: Configuration, density: Float) {
    val screenHeightDp = configuration.screenHeightDp
    val screenWidthDp = configuration.screenWidthDp
    screenHeightPx = (screenHeightDp * density).toInt()
    screenWidthPx = (screenWidthDp * density).toInt()
}