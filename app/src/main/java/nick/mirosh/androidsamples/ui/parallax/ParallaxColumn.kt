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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import nick.mirosh.androidsamples.R
import nick.mirosh.androidsamples.utils.calculateYOffset
import nick.mirosh.androidsamples.utils.loadLocalPictures
import nick.mirosh.androidsamples.utils.loadPictures


private const val TAG = "ParallaxScreen"

var screenWidthPx = 0
var screenHeightPx = 0
val defaulCardHeightDp = 200.dp
val defaultParallaxScrollSpeed = 0.5f

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
//    UriInvertedParallaxColumn(
//        pictureUris = picturesWithLocalIds.map { PictureUri.RawResource(it.pictureId) },
//        cardHeight = 500.dp,
//    ) {
    UriInvertedParallaxColumn(pictureUris = picturesWithUrls.map { PictureUri.RemoteUrl(it.pictureUrl) }) {
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
sealed class PictureUri {
    data class RemoteUrl(val value: String) : PictureUri()
    data class RawResource(val value: Int) : PictureUri()
}

@Composable
fun UriInvertedParallaxColumn(
    modifier: Modifier = Modifier,
    pictureUris: List<PictureUri>,
    cardHeight: Dp = defaulCardHeightDp,
    parallaxScrollSpeed: Float = defaultParallaxScrollSpeed,
    content: @Composable BoxScope.(index: Int) -> Unit,
) {

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
            modifier = modifier,
            bitmaps = bitmaps,
            cardHeight = cardHeight,
            parallaxScrollSpeed = parallaxScrollSpeed,
            content = content
        )
    }
}

val defaultCardModifier = Modifier
    .padding(start = 16.dp, end = 16.dp)
    .fillMaxWidth()

val defaultCanvasModifier = Modifier

val defaultColumnModifier = Modifier
    .fillMaxWidth()

@Composable
fun InvertedParallaxColumn(
    modifier: Modifier = defaultColumnModifier,
    cardModifier: Modifier = Modifier,
    bitmaps: List<Bitmap>,
    spacerHeight: Dp = 16.dp,
    cardHeight: Dp = defaulCardHeightDp,
    parallaxScrollSpeed: Float = defaultParallaxScrollSpeed,
    content: @Composable BoxScope.(index: Int) -> Unit,
) {
    val columnScrollState = rememberScrollState()

    var prevScrollValue by remember { mutableIntStateOf(0) }
    val columnScrollFromTopInPx = columnScrollState.value
    prevScrollValue = columnScrollState.value
    Column(
        modifier = modifier
            .verticalScroll(columnScrollState)
            .testTag("column"),
    ) {
        repeat(bitmaps.size) { index ->
            Spacer(modifier = Modifier.height(spacerHeight))
            InvertedCard(
                cardModifier = cardModifier,
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
    cardModifier: Modifier =/* defaultCardModifier*/ Modifier,
    originalBitmap: Bitmap,
    cardHeight: Dp = defaulCardHeightDp,
    parallaxScrollSpeed: Float,
    totalColumnScrollFromTop: Int = 0,
    content: @Composable BoxScope.() -> Unit
) {
    val cardHeightInPx = with(LocalDensity.current) {
        cardHeight.roundToPx()
    }
    Card(
        modifier = cardModifier
            .height(cardHeight)
            .padding(start = 16.dp, end = 16.dp),
    ) {
        Box(
            modifier = cardModifier
                .fillMaxWidth()
        ) {
            Canvas(
                modifier = defaultCanvasModifier
            ) {
                drawIntoCanvas { canvas ->
                    val width = originalBitmap.width
                    val height = originalBitmap.height
                    val yOffset = calculateYOffset(
                        (totalColumnScrollFromTop * parallaxScrollSpeed).toInt(),
                        cardHeightInPx,
                        height
                    )
                    val newBitmap = Bitmap.createBitmap(
                        originalBitmap, 0, yOffset, width, cardHeightInPx
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