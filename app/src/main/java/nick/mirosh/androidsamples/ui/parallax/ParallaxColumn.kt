package nick.mirosh.androidsamples.ui.parallax

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

data class Picture(
    val pictureId: Int,
    val author: String,
    val authorUrl: String,
)

data class PictureWithUrl(
    val pictureUrl: String,
    val author: String,
    val authorUrl: String,
)

@Composable
fun ParallaxScreenTest() {
    ParallaxColumn(pictureIds = picturesWithLocalIds.map { it.pictureId }) {
//    ParallaxColumn(pictureUrls = picturesWithUrls.map { it.pictureUrl }) {
        items(picturesWithLocalIds) { _, item ->
            val context = LocalContext.current
            val intent =
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(item.authorUrl)
                )
            Button(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(8.dp),
                onClick = { context.startActivity(intent) },
            ) {
                Text(text = "photo by ${item.author}")
            }
        }
    }
}

@Composable
fun ParallaxColumn(
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
            Log.d(TAG, "ParallaxColumn: parsedBitmaps.size = ${parsedBitmaps.size}")
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
            InvertedCard(
                originalBitmap = bitmaps[it],
                cardHeight = cardHeight,
                totalColumnScrollFromTop = columnScrollFromTopInPx,
            ) {
                content()
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
    content: @Composable BoxScope.() -> Unit
) {
    Card(
        modifier = Modifier
            .height(cardHeightDp.dp)
            .padding(start = 16.dp, end = 16.dp)
    ) {
        Box(modifier = modifier.fillMaxSize()) {
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


val picturesWithLocalIds = listOf(
    Picture(
        pictureId = R.raw.lukas_dlutko,
        author = "Lukas Ldutko",
        authorUrl = "https://www.pexels.com/@lukas-dlutko-1278617/"
    ),
    Picture(
        pictureId = R.raw.amine_msiouri,
        author = "Amine Msiouri",
        authorUrl = "https://www.pexels.com/@amine-m-siouri-1025778/"
    ),
    Picture(
        pictureId = R.raw.connor_danylenko,
        author = "Connor Danylenko",
        authorUrl = "https://www.pexels.com/@connor-danylenko-534256/"
    ),
    Picture(
        pictureId = R.raw.julia_volk,
        author = "Julia Volk",
        authorUrl = "https://www.pexels.com/@julia-volk/"
    ),
    Picture(
        pictureId = R.raw.matthew_montrone,
        author = "Matthew Montrone",
        authorUrl = "https://www.pexels.com/th-th/@matthew-montrone-230847/"
    ),
    Picture(
        pictureId = R.raw.sam_willis,
        author = "Sam Willis",
        authorUrl = "https://www.pexels.com/@sam-willis-457311/"
    ),
    Picture(
        pictureId = R.raw.pixabay,
        author = "Pixabay",
        authorUrl = "https://www.pexels.com/@pixabay/"
    ),
)
val picturesWithUrls = listOf(

    PictureWithUrl(
        pictureUrl = "https://images.pexels.com/photos/1179229/pexels-photo-1179229.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2",
        author = "Lukas Ldutko",
        authorUrl = "https://www.pexels.com/@lukas-dlutko-1278617/"
    ),
    PictureWithUrl(
        pictureUrl = "https://images.pexels.com/photos/1179229/pexels-photo-1179229.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2",
        author = "Lukas Ldutko",
        authorUrl = "https://www.pexels.com/@lukas-dlutko-1278617/"
    ),
    PictureWithUrl(
        pictureUrl = "https://images.pexels.com/photos/1179229/pexels-photo-1179229.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2",
        author = "Lukas Ldutko",
        authorUrl = "https://www.pexels.com/@lukas-dlutko-1278617/"
    ),
)
