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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import nick.mirosh.androidsamples.R
import nick.mirosh.androidsamples.utils.loadLocalPictures
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
fun ParallaxScreenTestWithBitmaps() {
    val context = LocalContext.current
    val bitmaps = remember {
        mutableStateListOf<Bitmap>()
    }
    Log.d(TAG, "ParallaxScreenTestWithBitmaps:  bitmaps = ${bitmaps.size}")
    if (bitmaps.isNotEmpty()) {
        InvertedParallaxColumn(bitmaps = bitmaps.toList()) {
            val item = picturesWithLocalIds[it]
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
    else {
        LaunchedEffect(Unit) {
            bitmaps.addAll(picturesWithLocalIds.mapNotNull {
                context.loadLocalPictures(it.pictureId)
            })
        }
    }
}


@Composable
fun ParallaxScreenTest() {
    UriParallaxColumn(pictureUris = picturesWithLocalIds.map { it.pictureId }) {
//    UriParallaxColumn(pictureUris = picturesWithUrls.map { it.pictureUrl }) {
//        val item = picturesWithUrls[it]
        val item = picturesWithLocalIds[it]
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

/**
 * @param pictureUris - URLs of pictures to be downloaded via network or R.raw.id's
 * to be loaded from the "raw" folder and drawn on top of the inverted card background
 * @param cardHeightInDp - height of the card in density pixels
 * @param parallaxScrollSpeed - speed of the parallax effect relative to the column scroll speed
 * @param content - content to be drawn on top of the card
 */

@Composable
fun <T> UriParallaxColumn(
    pictureUris: List<T>,
    cardHeightInDp: Int = cardHeightDp,
    parallaxScrollSpeed: Float = 0.5f,
    content: @Composable BoxScope.(index: Int) -> Unit,
) {
    if (pictureUris.any { it !is String } && pictureUris.any { it !is Int }) {
        throw IllegalArgumentException("pictureUris must be either of type Int or String")
    }

    val parsedBitmaps = remember {
        mutableStateListOf<Bitmap?>(null)
    }

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        parsedBitmaps.removeFirstOrNull()
        parsedBitmaps.addAll(
            loadPictures(
                pictureUris, context
            )
        )
    }

    val bitmaps = parsedBitmaps.toList().filterNotNull()
    if (bitmaps.isNotEmpty()) {
        InvertedParallaxColumn(
            bitmaps = bitmaps,
            cardHeightInDp = cardHeightInDp,
            parallaxScrollSpeed = parallaxScrollSpeed,
            content = content
        )
    }
}

@Composable
fun InvertedParallaxColumn(
    bitmaps: List<Bitmap>,
    cardHeightInDp: Int = cardHeightDp,
    parallaxScrollSpeed: Float = 0.5f,
    content: @Composable BoxScope.(index: Int) -> Unit,
) {
    val columnScrollState = rememberScrollState()
    val cardHeight = with(LocalDensity.current) { cardHeightInDp.dp.roundToPx() }

    var prevScrollValue by remember { mutableIntStateOf(0) }
    val columnScrollFromTopInPx = columnScrollState.value
    prevScrollValue = columnScrollState.value
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(columnScrollState),
    ) {
        repeat(bitmaps.size) { index ->
            Spacer(modifier = Modifier.height(16.dp))
            InvertedCard(
                originalBitmap = bitmaps[index],
                cardHeight = cardHeight,
                totalColumnScrollFromTop = columnScrollFromTopInPx,
                parallaxScrollSpeed = parallaxScrollSpeed
            ) {
                content(index)
            }
        }
    }
}

@Composable
fun InvertedCard(
    modifier: Modifier = Modifier,
    originalBitmap: Bitmap,
    cardHeight: Int,
    parallaxScrollSpeed: Float,
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
                        (totalColumnScrollFromTop * parallaxScrollSpeed).toInt(),
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


fun main() {
    val first = 3
    val second = 0.5
    println("${first * second}")
}