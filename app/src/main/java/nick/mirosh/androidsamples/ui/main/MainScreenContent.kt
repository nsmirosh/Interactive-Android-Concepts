package nick.mirosh.androidsamples.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/*@Composable
fun FavoriteArticlesScreenContent(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
) {
    val articles by viewModel.articles.collectAsStateWithLifecycle(listOf())

    if (articles.isNotEmpty()) {

        LazyColumn {

            items(articles.size) { index ->
                val article = articles[index]
                Row(
                    modifier = Modifier.padding(8.dp, 4.dp, 8.dp, 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .height(150.dp)
                            .padding(8.dp)
                            .width(200.dp)
                            .clip(shape = RoundedCornerShape(8.dp)),

                        model = article.urlToImage,
                        contentDescription = "Translated description of what the image contains"
                    )
                    Text(
                        text = article.title,
                        lineHeight = 18.sp,
                        fontSize = 14.sp

                    )
                }
            }
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No saved articles",
                fontSize = 24.sp,
            )
        }
    }
}*/