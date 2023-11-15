package nick.mirosh.androidsamples.ui.coroutines.async

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle


@Composable
fun AsyncComparisonScreen(
    viewModel: AsyncComparisonViewModel = hiltViewModel()
) {
    val deferred1Updates by viewModel.deferred1Flow.collectAsStateWithLifecycle()
    val deferred2Updates by viewModel.deferred2Flow.collectAsStateWithLifecycle()
    val job1FlowUpdates by viewModel.job1flow.collectAsStateWithLifecycle()
    val job2FlowUpdates by viewModel.job2flow.collectAsStateWithLifecycle()
    var restart by remember {
        mutableStateOf(false)
    }
    key(restart) {
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

            ProgressBarWithCancel(
                progress = deferred1Updates,
                label = "Deferred 1",
                onCancelClick = {
                    viewModel.cancelAsync1()
                }
            )
            ProgressBarWithCancel(
                progress = deferred2Updates,
                label = "Deferred 2",
                onCancelClick = {
                    viewModel.cancelAsync2()
                }
            )
            ProgressBarWithCancel(
                progress = job1FlowUpdates,
                label = "Coroutine 1",
                onCancelClick = {
                    viewModel.cancelCoroutine1()
                }
            )
            ProgressBarWithCancel(
                progress = job2FlowUpdates,
                label = "Coroutine 2",
                onCancelClick = {
                    viewModel.cancelCoroutine2()
                }
            )
            Button(onClick = {
                viewModel.clear()
                restart = !restart
            }) {
                Text("Restart")
            }
        }
    }
}


@Composable
fun ProgressBarWithCancel(
    modifier: Modifier = Modifier,
    progress: Float,
    label: String,
    onCancelClick: () -> Unit,
) {

    var cancelled by remember {
        mutableStateOf(false)
    }
    Spacer(modifier = Modifier.height(16.dp))
    Text(label)
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        ProgressBar(
            modifier = modifier
                .weight(2f / 3f)
                .padding(horizontal = 16.dp),
            progress = progress
        )

        Button(
            colors = if (cancelled) ButtonDefaults.buttonColors(
                backgroundColor = Color.Red,
                contentColor = Color.White
            )
            else ButtonDefaults.buttonColors(),
            modifier = Modifier
                .weight(1f / 3f)
                .padding(horizontal = 16.dp),
            onClick = {
                cancelled = true
                onCancelClick()
            }) {
            Text(if (cancelled) "Cancelled" else "Cancel")
        }
    }
}


@Composable
fun ProgressBar(
    modifier: Modifier = Modifier,
    progress: Float,
) {

    val progressAnimDuration = 300
    val progressAnimation by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(
            durationMillis = progressAnimDuration,
            easing = FastOutSlowInEasing
        ),
        label = ""
    )
    LinearProgressIndicator(
        progress = progressAnimation,
        modifier = modifier
            .padding(horizontal = 16.dp)
            .height(32.dp)
            .clip(RoundedCornerShape(16.dp))
    )
}
