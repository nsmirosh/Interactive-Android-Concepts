package nick.mirosh.androidsamples.ui.parallax

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import nick.mirosh.androidsamples.R
import nick.mirosh.androidsamples.utils.decodeRawResource
import nick.mirosh.androidsamples.utils.loadPictureFromNetwork


private const val TAG = "ParallaxScreen"

var screenWidthPx = 0
var screenHeightPx = 0
val cardHeightDp = 200

@Composable
fun ParallaxScreen() {

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
    InvertedParallaxColumn(pictureUrls)
}

@Composable
fun InvertedParallaxColumn(
    pictureUrls: List<String>? = null, pictureIds: List<Int>? = null
) {
    val bitmaps = remember {
        mutableStateListOf<Bitmap?>(null)
    }

    val authorsAndLinks = listOf(
        "Lukáš Dlutko" to "https://www.pexels.com/@lukas-dlutko-1278617/",
        "Amine M'siouri" to "https://www.pexels.com/@amine-m-siouri-1025778/",
        "Connor Danylenko" to "https://www.pexels.com/@connor-danylenko-534256/",
        "Felix Mittermeier" to "https://www.pexels.com/@felixmittermeier/",
        "Julia Volk" to "https://www.pexels.com/@julia-volk/",
        "Matthew Montrone" to "https://www.pexels.com/@matthew-montrone-230847/",
        "Sam Willis" to "https://www.pexels.com/@sam-willis-457311/",
        "Pixabay" to "https://www.pexels.com/@pixabay/",
    )
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current.density
    val context = LocalContext.current

    Log.d(TAG, "ParallaxScreen: prior to Launched effect ")
    LaunchedEffect(Unit) {
        Log.d(TAG, "ParallaxScreen: entering Launched effect ")
        initScreenWidthAndHeight(configuration, density)
        bitmaps.removeFirstOrNull()
        loadPictures(
            pictureUrls, pictureIds, context
        ) {
            val scaledBitmap = Bitmap.createScaledBitmap(
                it, screenWidthPx, screenHeightPx, true
            )
            bitmaps.add(scaledBitmap)
        }
    }
    if (bitmaps.size == pictureUrls?.size) {
        Log.d(TAG, "ParallaxScreen: showing column")
        ScrollableColumn(bitmaps.toList(), authorsAndLinks)
    }
}

suspend fun loadPictures(
    pictureUrls: List<String>? = null,
    pictureIds: List<Int>? = null,
    context: Context,
    onBitmapLoaded: (Bitmap) -> Unit,
) {
    withContext(Dispatchers.IO) {
        pictureUrls?.forEachIndexed { index, url ->
            async {
                loadPictureFromNetwork(
                    "$index", url, context
                )?.let {
                    onBitmapLoaded(it)
                }
            }
        } ?: pictureIds?.forEach {
            async {
                decodeRawResource(
                    context.resources, it
                )?.let {
                    onBitmapLoaded(it)
                }
            }
        }
    }
}

@Composable
fun ScrollableColumn(
    bitmaps: List<Bitmap?>, authorAndLinkList: List<Pair<String, String>>
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
                val context = LocalContext.current
                val intent = remember {
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(authorAndLinkList[it].second)
                    )
                }
                Button(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(8.dp),
                    onClick = { context.startActivity(intent) },
                ) {
                    Text(text = "photo by ${authorAndLinkList[it].first}")
                }
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
        }) {
            Text("Inner clicks = $innerClicks")
        }
        Text("Outer clicks= $outerClicks")
    }
}
