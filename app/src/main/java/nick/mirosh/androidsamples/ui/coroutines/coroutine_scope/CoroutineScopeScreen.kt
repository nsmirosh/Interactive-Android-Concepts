package nick.mirosh.androidsamples.ui.coroutines.coroutine_scope

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nick.mirosh.androidsamples.ui.theme.MyApplicationTheme


@Composable
fun CoroutineScopeScreen(
    viewModel: CoroutineScopeViewModel = hiltViewModel()
) {
    val task1Updates by viewModel.task1Flow.collectAsStateWithLifecycle()
    val task2Updates by viewModel.task2Flow.collectAsStateWithLifecycle()
    val task3Updates by viewModel.task3Flow.collectAsStateWithLifecycle()
    val task4Updates by viewModel.task4Flow.collectAsStateWithLifecycle()

    Column {
        var coroutineScopeLaunched by remember {
            mutableStateOf(false)
        }
        Button(
            colors = if (coroutineScopeLaunched) ButtonDefaults.buttonColors(
                backgroundColor = Color.Green,
                contentColor = Color.White
            )
            else ButtonDefaults.buttonColors(),
            onClick = {
                coroutineScopeLaunched = true
                viewModel.onCoroutineScopeClicked()
            }) {
            Text(if (coroutineScopeLaunched) "Launched" else "Launch")
        }
        ProgressBarWithLabel(
            modifier = Modifier.padding(16.dp),
            progress = task1Updates,
            label = "Task 1",
        )
        ProgressBarWithLabel(
            modifier = Modifier.padding(16.dp),
            progress = task2Updates,
            label = "Task 2",
        )
        ProgressBarWithLabel(
            modifier = Modifier.padding(16.dp),
            progress = task3Updates,
            label = "Task 3",
        )
        ProgressBarWithLabel(

            modifier = Modifier.padding(16.dp),
            progress = task4Updates,
            label = "Task 4",
        )
    }
}


@Composable
fun ProgressBarWithLabel(
    modifier: Modifier = Modifier,
    progress: Float,
    label: String,
) {
    Column {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            text = label,
            fontSize = 20.sp
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProgressBar(
                modifier = modifier
                    .weight(2f / 3f),
                progress = progress
            )
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


@Preview
@Composable
fun ProgressBarWithCancelPreview() {
    MyApplicationTheme {
        ProgressBarWithLabel(
            modifier = Modifier.padding(16.dp),
            progress = 0.5f,
            label = "Deferred 1",
        )
    }
}


@Preview
@Composable
fun CoroutineScopeScreenPreview() {
    MyApplicationTheme {
        CoroutineScopeScreen()
    }
}
