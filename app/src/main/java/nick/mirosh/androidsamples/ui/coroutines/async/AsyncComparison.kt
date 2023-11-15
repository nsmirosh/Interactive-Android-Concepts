package nick.mirosh.androidsamples.ui.coroutines.async

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle


@Composable
fun AsyncComparisonScreen(
    viewModel: AsyncComparisonViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    with (state) {
        when (state) {
            is AsyncComparisonUIState.DeferredUpdate -> {
                ProgressBar(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .height(32.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    progress = (progress * 100).toInt()
                )
            }
        }
    }

    Column {
        Button(
            onClick = {
                viewModel.launchAsyncs()
            }
        ) {
            Text("Launch asyncs")
        }

        Button(
            onClick = {
                viewModel.launchCoroutines()
            }
        ) {
            Text("Launch coroutines")
        }
    }
}


@Composable
fun ProgressBar(
    modifier: Modifier,
    progress: Int,
) {

    LinearProgressIndicator(
        progress = progress / 100f,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(32.dp)
            .clip(RoundedCornerShape(16.dp))
    )
}
