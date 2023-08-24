
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.unit.dp
import nick.mirosh.androidsamples.R
import nick.mirosh.androidsamples.ui.parallax.InvertedParallaxColumn
import nick.mirosh.androidsamples.ui.parallax.Picture
import nick.mirosh.androidsamples.utils.loadLocalPictures
import org.junit.Rule
import org.junit.Test


class InvertedParallaxColumnTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun invertedParallaColumn_withValidBitmaps_displaysEverythingOK() {

        composeTestRule.setContent {
            //Arrange
            val context = LocalContext.current
            val bitmaps = remember {
                mutableStateListOf<Bitmap>()
            }

            if (bitmaps.isNotEmpty()) {
                //Act
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
        //Assert
        composeTestRule.onNodeWithTag("column").performScrollToNode(hasText("photo by Pixabay"))
        composeTestRule.onNodeWithText("photo by Matthew Montrone").assertIsDisplayed()
    }


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
    Picture(
        pictureId = R.raw.pixabay,
        author = "Pixabay",
        authorUrl = "https://www.pexels.com/@pixabay/"
    ),
    Picture(
        pictureId = R.raw.pixabay,
        author = "Pixabay",
        authorUrl = "https://www.pexels.com/@pixabay/"
    ),
    Picture(
        pictureId = R.raw.pixabay,
        author = "Pixabay",
        authorUrl = "https://www.pexels.com/@pixabay/"
    ),
)
