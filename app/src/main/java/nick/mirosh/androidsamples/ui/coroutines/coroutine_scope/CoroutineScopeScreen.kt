package nick.mirosh.androidsamples.ui.coroutines.coroutine_scope

import android.graphics.Color.parseColor
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nick.mirosh.androidsamples.ui.theme.MyApplicationTheme


val firstLevelIndent = 16.dp
val secondLevelIndent = 32.dp
val thirdLevelIndent = 48.dp

const val firstLevelColor = "#4cc9f0"
const val secondLevelColor = "#f72585"
const val thirdLevelColor = "#4361ee"

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
        Text(
            modifier = Modifier.padding(start = firstLevelIndent, top = 16.dp),
            fontSize = 26.sp,
            fontWeight = Bold,
            color = Color(parseColor(firstLevelColor)),
            text = "viewModelScope.launch {"
        )
        Text(
            modifier = Modifier.padding(start = secondLevelIndent, top = 16.dp),
            fontSize = 26.sp,
            fontWeight = Bold,
            color = Color(parseColor(thirdLevelColor)),
            text = "launch {"
        )
        ProgressBarWithLabel(
            modifier = Modifier.padding(start = secondLevelIndent),
            progress = task1Updates.first,
            label = task1Updates.second
        )
        Text(
            modifier = Modifier.padding(start = secondLevelIndent),
            fontSize = 26.sp,
            fontWeight = Bold,
            color = Color(parseColor(thirdLevelColor)),
            text = "}"
        )
        Text(
            modifier = Modifier.padding(start = secondLevelIndent, top = 16.dp),
            fontSize = 26.sp,
            fontWeight = Bold,
            color = Color(parseColor(secondLevelColor)),
            text = "coroutineScope {"
        )
        Text(
            modifier = Modifier.padding(start = thirdLevelIndent, top = 8.dp),
            fontSize = 26.sp,
            fontWeight = Bold,
            color = Color(parseColor(secondLevelColor)),
            text = "launch {"
        )
        ProgressBarWithLabel(
            modifier = Modifier.padding(start = thirdLevelIndent),
            progress = task2Updates.first,
            label = task2Updates.second
        )
        Text(
            modifier = Modifier.padding(start = thirdLevelIndent),
            fontSize = 26.sp,
            fontWeight = Bold,
            color = Color(parseColor(secondLevelColor)),
            text = "}"
        )
        ProgressBarWithLabel(
            modifier = Modifier.padding(start = secondLevelIndent),
            progress = task3Updates.first,
            label = task3Updates.second
        )
        Text(
            modifier = Modifier.padding(start = secondLevelIndent),
            fontSize = 26.sp,
            fontWeight = Bold,
            color = Color(parseColor(secondLevelColor)),
            text = "}"
        )
        ProgressBarWithLabel(
            modifier = Modifier.padding(start = firstLevelIndent),
            progress = task4Updates.first,
            label = task4Updates.second
        )
        Text(
            modifier = Modifier.padding(start = firstLevelIndent),
            fontSize = 26.sp,
            fontWeight = Bold,
            color = Color(parseColor(firstLevelColor)),
            text = "}"
        )
    }
}

@Composable
fun CodeText(
    modifier: Modifier = Modifier,
    indentDp: Dp,
    color: String,
    text: String,
) {
    Text(
        modifier = Modifier.padding(start = indentDp),
        fontSize = 26.sp,
        fontWeight = Bold,
        color = Color(parseColor(color)),
        text = text
    )
}


@Composable
fun ProgressBarWithLabel(
    modifier: Modifier = Modifier,
    progress: Float,
    label: String,
) {
    Box(
        modifier = modifier
            .padding(end = 16.dp)
            .width(300.dp)
            .height(48.dp),
        contentAlignment = Alignment.Center
    )
    {
        ProgressBar(
            modifier = modifier
                .fillMaxWidth(),
            progress = progress
        )
        Text(
            modifier = Modifier,
            text = label,
            color = Color.White,
            fontSize = 20.sp
        )
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
            .height(32.dp)
            .clip(RoundedCornerShape(16.dp))
    )
}


@Preview
@Composable
fun ProgressBarWithCancelPreview() {
    MyApplicationTheme {
        ProgressBarWithLabel(
            progress = 0.5f,
            label = "Deferred 1",
        )
    }
}


@Preview
@Composable
fun CoroutineScopeScreenPreview() {
    MyApplicationTheme {
        Box(modifier = Modifier.background(Color.White)) {
            CoroutineScopeScreen()
        }
    }
}
