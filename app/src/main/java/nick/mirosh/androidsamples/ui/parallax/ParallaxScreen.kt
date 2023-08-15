package nick.mirosh.androidsamples.ui.parallax

import android.content.res.Configuration
import android.graphics.Bitmap
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import nick.mirosh.androidsamples.R
import nick.mirosh.androidsamples.utils.loadPictures


private const val TAG = "ParallaxScreen"

var screenWidthPx = 0
var screenHeightPx = 0
val cardHeightDp = 200

@Composable
fun ParallaxScreen2() {

    val pictures = listOf(
        R.raw.lukas_dlutko,
        R.raw.amine_msiouri,
        R.raw.connor_danylenko,
        R.raw.felix,
        R.raw.julia_volk,
        R.raw.matthew_montrone,
        R.raw.sam_willis,
        R.raw.pixabay
    )
    val pictureUrls = listOf(
        "https://images.pexels.com/photos/2832034/pexels-photo-2832034.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2",
        "https://images.pexels.com/photos/1154610/pexels-photo-1154610.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2",
        "https://images.pexels.com/photos/1179229/pexels-photo-1179229.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2",
        "https://images.pexels.com/photos/3284167/pexels-photo-3284167.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2",
        "https://images.pexels.com/photos/2627945/pexels-photo-2627945.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2",
    )

    LazyColumn {
        items(
            pictureUrls.size,
            {
                pictureUrls[it]
            },
        ) {
            Button(modifier = Modifier.padding(16.dp), onClick = {}) {
                Text(text = pictureUrls[it])
            }
        }
    }
}

@Composable
fun ParallaxScreenTest() {

    val pictureUrls = listOf(
        "https://images.pexels.com/photos/2832034/pexels-photo-2832034.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2",
        "https://images.pexels.com/photos/1154610/pexels-photo-1154610.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2",
        "https://images.pexels.com/photos/1179229/pexels-photo-1179229.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2",
        "https://images.pexels.com/photos/3284167/pexels-photo-3284167.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2",
        "https://images.pexels.com/photos/2627945/pexels-photo-2627945.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2",
    )


    val pictureIds = listOf(
        Pair(R.raw.lukas_dlutko, "Lukas Ldutko"),
        Pair(R.raw.amine_msiouri, "Amine Msiouri"),
        Pair(R.raw.connor_danylenko, "Connor Danylenko"),
        Pair(R.raw.felix, "Felix"),
        Pair(R.raw.julia_volk, "Julia Volk"),
        Pair(R.raw.matthew_montrone, "Matthew Montrone"),
        Pair(R.raw.sam_willis, "Sam Willis"),
        Pair(R.raw.pixabay, "Pixabay"),
    )
    ParallaxScreen(pictureIds = pictureIds.map { it.first }) {
        items(pictureIds) { index, item ->
            val context = LocalContext.current
//            val intent = remember {
//                Intent(
//                    Intent.ACTION_VIEW,
//                    Uri.parse(item)
//                )
//            }
            Button(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(8.dp),
                onClick = {/* context.startActivity(intent) */},
            ) {
                Text(text = "photo by balls")
            }
        }
    }
}

@Composable
fun ParallaxScreen(
    bitmaps: List<Bitmap>? = null,
    pictureUrls: List<String>? = null,
    pictureIds: List<Int>? = null,
    content: @Composable BoxScope.() -> Unit
) {
    var shimmer by remember {
        mutableStateOf(false)
    }
    val parsedBitmaps = remember {
        mutableStateListOf<Bitmap?>(null)
    }

    val configuration = LocalConfiguration.current
    val density = LocalDensity.current.density
    val context = LocalContext.current

    if (bitmaps == null) {
        LaunchedEffect(Unit) {
            shimmer = true
            initScreenWidthAndHeight(configuration, density)
            parsedBitmaps.removeFirstOrNull()
            parsedBitmaps.addAll(
                loadPictures(
                    pictureUrls, pictureIds, context
                )
            )
            shimmer = false
        }
    }

    if (parsedBitmaps.size == (pictureUrls?.size ?: pictureIds?.size)) {
        InvertedParallaxColumn2(parsedBitmaps.toList().filterNotNull()) {
            content()
        }
    }
}

@Composable
fun <T> BoxScope.items(
    items: List<T>,
    itemContent: @Composable (index: Int, item: T) -> Unit,
) {
    for (index in items.indices) {
        itemContent(index, items[index])
    }
}


@Composable
fun InvertedParallaxColumn2(
    bitmaps: List<Bitmap>,
    content: @Composable BoxScope.() -> Unit
) {
    val columnScrollState = rememberScrollState()
    val cardHeight = with(LocalDensity.current) { cardHeightDp.dp.roundToPx() }

    var prevScrollValue by remember { mutableIntStateOf(0) }
    val columnScrollFromTopInPx = columnScrollState.value
    prevScrollValue = columnScrollState.value
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(columnScrollState),
    ) {
        repeat(bitmaps.size) {
            Spacer(modifier = Modifier.height(16.dp))
            InvertedCard(originalBitmap = bitmaps[it],
                cardHeight = cardHeight,
                totalColumnScrollFromTop = columnScrollFromTopInPx,
                errorContent = {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = "SOMETHING WENT WRONG",
                        )
                    }
                }) {
                content()
            }
        }
    }
}


@Composable
fun ScrollableColumn(
    bitmaps: List<Bitmap?>
) {
    val columnScrollState = rememberScrollState()
    val cardHeight = with(LocalDensity.current) { cardHeightDp.dp.roundToPx() }

    var prevScrollValue by remember { mutableIntStateOf(0) }
    val columnScrollFromTopInPx = columnScrollState.value
    prevScrollValue = columnScrollState.value
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(columnScrollState),
    ) {
        repeat(bitmaps.size) {
            Spacer(modifier = Modifier.height(16.dp))
            InvertedCard(originalBitmap = if (it == 2) null else bitmaps[it],
                cardHeight = cardHeight,
                totalColumnScrollFromTop = columnScrollFromTopInPx,
                errorContent = {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = "SOMETHING WENT WRONG",
                        )
                    }
                }) {
            }
        }
    }
}

@Composable
fun InvertedCard(
    modifier: Modifier = Modifier,
    originalBitmap: Bitmap?,
    cardHeight: Int,
    totalColumnScrollFromTop: Int = 0,
    errorContent: @Composable BoxScope.() -> Unit,
    content: @Composable BoxScope.() -> Unit
) {
    Card(
        modifier = Modifier
            .height(cardHeightDp.dp)
            .padding(start = 16.dp, end = 16.dp)
    ) {
        Box(modifier = modifier.fillMaxSize()) {
            if (originalBitmap != null) {
                Canvas(
                    modifier = Modifier
                ) {
                    drawIntoCanvas { canvas ->
                        val width = originalBitmap.width
                        val height = originalBitmap.height
                        val yOffset = calculateYOffset(
                            (totalColumnScrollFromTop * 5) / 10,
                            cardHeight,
                            height
                        )
                        val newBitmap = Bitmap.createBitmap(
                            originalBitmap, 0, yOffset, width, cardHeight
                        )
                        canvas.nativeCanvas.drawBitmap(
                            newBitmap, 0f, 0f, null
                        )
                    }
                }
                content()
            }
            else {
                errorContent()
            }
        }
    }
}

fun calculateYOffset(
    totalColumnScrollFromTop: Int, cardHeight: Int, pictureHeight: Int
) = if (totalColumnScrollFromTop <= 0) {
    pictureHeight - cardHeight
}
else if (totalColumnScrollFromTop + cardHeight >= pictureHeight) {
    0
}
else {
    pictureHeight - cardHeight - (totalColumnScrollFromTop)
}


fun initScreenWidthAndHeight(configuration: Configuration, density: Float) {
    val screenHeightDp = configuration.screenHeightDp
    val screenWidthDp = configuration.screenWidthDp
    screenHeightPx = (screenHeightDp * density).toInt()
    screenWidthPx = (screenWidthDp * density).toInt()
}
