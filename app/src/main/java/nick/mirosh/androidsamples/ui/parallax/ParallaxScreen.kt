package nick.mirosh.androidsamples.ui.parallax

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import nick.mirosh.androidsamples.R

@Composable
fun ParallaxScreen() {
//    CollapsibleHeaderScreen()
    CharacterScreen()
}


@Composable
fun CharacterScreen() {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
    ) {
        val imageAddress = R.drawable.bangkok
        Box {
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        alpha =
                            1f - ((scrollState.value.toFloat() / scrollState.maxValue) * 1.5f)
                        translationY = 0.5f * scrollState.value
                    },
                contentDescription = "Character",
                model = imageAddress,
                contentScale = ContentScale.FillBounds,
            )
        }
        Text(modifier = Modifier.background(Color.White), text = someRandomText)

    }
}


@Composable
fun Counter() {
    var count by remember { mutableIntStateOf(0) }

    MaterialTheme {
        Column {
            Text(text = "You've clicked the button $count times")
            Button(onClick = { count++ }) {
                Text(text = "Click me")
            }
        }
    }
}


const val someRandomText =
    "American rapper and singer Post Malone will have a Bangkok debut concert as part of his \"If Y'All Weren't Here, I'd Be Crying\" world tour, at Impact Challenger Hall, Muang Thong Thani, Nonthaburi province, on Sept 14 at 8.30pm.\n" +
            "\n" +
            "The eight-times diamond-certified global superstar just finished his highly successful \"Twelve Carat Tour\" to promote his fourth album Twelve Carat Toothache (2022) this May before he kicked off a new tour in North America early this month, leading up to the release of his fifth album, Austin, on Friday.\n" +
            "\n" +
            "He will stop by seven major cities on his Asian leg in September, starting with a gig in Bangkok before heading to perform in Singapore, Manila, Taipei, Seoul, Hong Kong and Tokyo.\n" +
            "\n" +
            "Born Austin Richard Post in 1995, Malone began his music career when he was 16 and gained recognition with his 2015 debut single White Iverson, which peaked at number 14 on the US Billboard Hot 100.\n" +
            "\n" +
            "He released his debut album Stoney in 2016. It set the record for most weeks on the US Billboard Top R&B/Hip-Hop Albums chart. His second album Beerbongs & Bentleys (2018) debuted at No.1 on the Billboard 200 and set several streaming records. It was nominated for Album of the Year at the 61st Grammy Awards.\n" +
            "\n" +
            "\n" +
            "(Photo courtesy of Live Nation Tero)\n" +
            "\n" +
            "His third album Hollywood's Bleeding (2019) also debuted at No.1 on the Billboard 200. It included Sunflower, the lead single to the soundtrack for the film Spider-Man: Into The Spider-Verse (2018). It was nominated for Album of the Year at the 2021 Grammy Awards.\n" +
            "\n" +
            "For the upcoming show, Thai fans can expect to enjoy his signature exhilarating performance with songs from his new album as well as fan-favourite hits such as Better Now, I Fall Apart and Circles."