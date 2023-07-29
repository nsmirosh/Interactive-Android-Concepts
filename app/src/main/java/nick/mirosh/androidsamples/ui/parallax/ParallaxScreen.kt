package nick.mirosh.androidsamples.ui.parallax

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.util.TypedValue
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nick.mirosh.androidsamples.R


private const val TAG = "ParallaxScreen"

@Composable
fun ParallaxScreen() {
//    ScrollableCard()
    GetEverythingByPixelsPreparation()
    ScrollableCard()
}

@Composable
fun ScrollableCard() {
    val columnScrollState = rememberScrollState()
    GetEverythingByPixelsPreparation()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(columnScrollState),
    ) {
        ScrollableItem2(
            columnScrollState,
            "Massimiliano Morosinotto",
            R.drawable.picture1
        )
        ScrollableItem2(
            columnScrollState,
            "Massimiliano Morosinotto",
            R.drawable.picture1
        )
        ScrollableItem2(
            columnScrollState,
            "Massimiliano Morosinotto",
            R.drawable.picture1
        )
        ScrollableItem2(
            columnScrollState,
            "Massimiliano Morosinotto",
            R.drawable.picture1
        )
    }
}


@Composable
fun ScrollableItem(columnScrollState: ScrollState, author: String, image: Int) {
    var prevScrollValue by remember { mutableIntStateOf(0) }
    val scrollState = rememberScrollState()
    Spacer(modifier = Modifier.height(16.dp))
    Card(
        modifier = Modifier.height(200.dp)
    ) {
        Box(
            modifier = Modifier
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = "Your Image",
                modifier = Modifier
                    .background(Color.Yellow)
                    .verticalScroll(columnScrollState)
                    .gesturesDisabled()
                    .graphicsLayer {
                        val yMovement =
                            columnScrollState.value - prevScrollValue
                        prevScrollValue = columnScrollState.value
                        val adjusted = 0.5f * yMovement.toFloat()
                        Log.d(TAG, "ScrollableCard: $adjusted")
                        translationY = adjusted
                    },
            )
            Text(
                text = author,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp),
                color = Color.White,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun ScrollableItem2(
    columnScrollState: ScrollState,
    author: String,
    image: Int
) {
    Spacer(modifier = Modifier.height(16.dp))
    Card(
        modifier = Modifier.height(200.dp)
    ) {
        GetEverythingByPixels()
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

@Preview
@Composable
fun ScrollableItemPreview() {
    val columnScrollState = rememberScrollState()
    ScrollableItem2(
        columnScrollState = columnScrollState,
        author = "balls",
        image = R.drawable.picture1
    )
}

private var originalBitmap: Bitmap? = null

@Composable
fun GetEverythingByPixelsPreparation() {
    val resources = LocalContext.current.resources
    val opts = BitmapFactory.Options().apply {
        inScaled =
            false  // ensure the bitmap is not scaled based on device density
    }
    val inputStream = resources.openRawResource(R.raw.picture1)
    originalBitmap = BitmapFactory.decodeResourceStream(
        resources,
        TypedValue(),
        inputStream,
        null,
        opts
    )

    Log.d(TAG, " GetEverythingByPixelsPreparation: ${originalBitmap?.width}")
}

@Composable
fun GetEverythingByPixels(modifier: Modifier = Modifier, scrollBy: Int = 0) {
    var launch by remember { mutableStateOf(false) }
    var y by remember { mutableIntStateOf(50) }
    //convert 200dp to px
    val pixelValue = with(LocalDensity.current) { 200.dp.roundToPx() }

   //get the amount of pixels that the image column was scrolled
   //apply that same scroll to the bitmap

    Canvas(
        modifier = modifier
            .aspectRatio(originalBitmap!!.width.toFloat() / y)
    ) {
        drawIntoCanvas { canvas ->
            val width = originalBitmap!!.width
            Log.d(TAG, "GetEverythingByPixels: $width")
            val newBitmap =
                Bitmap.createBitmap(originalBitmap!!, 0, 0, width, pixelValue)
            canvas.nativeCanvas.drawBitmap(newBitmap, 0f, 0f, null)
        }
    }
}


