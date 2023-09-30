package nick.mirosh.androidsamples.ui.parallax

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import nick.mirosh.androidsamples.R
import nick.mirosh.parallaxcolumn.ParallaxColumn
import nick.mirosh.parallaxcolumn.PictureUri
import nick.mirosh.parallaxcolumn.UriParallaxColumn


@Composable
fun UriParallaxColumnRunner() {

    val localUris = listOf(
        R.raw.amine_msiouri,
        R.raw.connor_danylenko,
        R.raw.julia_volk,
        R.raw.lukas_dlutko
    ).map {
        PictureUri.RawResource(it)
    }

    val authors = listOf(
        "Amine Msiouri",
        "Connor Danylenko",
        "Julia Volk",
        "Lukas Dlutko"
    )

    UriParallaxColumn(pictureUris = localUris) {

        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(8.dp),
        ) {
            Text(
                text = authors[it],
                modifier = Modifier
                    .background(Color.White)
                    .clip(RoundedCornerShape(4.dp)) // Rounded corners
            )
        }
    }
}


@Composable
fun ParallaxColumnRunner(bitmaps: List<Bitmap>) {
    val authors = listOf(
        "Amine Msiouri",
        "Connor Danylenko",
        "Julia Volk",
        "Lukas Dlutko"
    )

    ParallaxColumn(bitmaps = bitmaps) {
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(8.dp),
        ) {
            Text(
                text = authors[it],
                modifier = Modifier
                    .background(Color.White)
                    .clip(RoundedCornerShape(4.dp)) // Rounded corners
            )
        }
    }
}