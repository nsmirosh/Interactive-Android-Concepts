package nick.mirosh.androidsamples.ui.recomposition

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp

@Composable
fun RecompositionListScreen() {
    Column {
        generateListOfMovieNames().forEach { movieName ->
            MovieItem(movieName = movieName)
        }
    }
}

fun generateListOfMovieNames() =
    listOf(
        "The Matrix",
        "The Matrix Reloaded",
        "The Matrix Revolutions",
        "The Animatrix",
        "The Matrix 4"
    )


@Composable
fun MovieItem(movieName: String) {
    Text(text = movieName, fontSize = 24.sp)
}