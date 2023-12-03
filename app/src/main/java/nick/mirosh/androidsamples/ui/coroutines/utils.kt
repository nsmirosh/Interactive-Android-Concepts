package nick.mirosh.androidsamples.ui.coroutines

import android.util.Log
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import nick.mirosh.androidsamples.R
import java.io.PrintWriter
import java.io.StringWriter


const val greenColor = "#00ab41"

fun myGreen() = Color(android.graphics.Color.parseColor(greenColor))

suspend fun runUpdatesIn(
    flow: MutableStateFlow<Float>,
    delayMs: Long = 500L,
) {
    var counter = 0
    while (counter <= 100) {
        delay(delayMs)
        flow.value = counter / 100f
        counter += 10
    }
}

fun Throwable.logStackTrace(tag: String) {
    val sw = StringWriter()
    this.printStackTrace(PrintWriter(sw))
    val exceptionAsString = sw.toString()
    Log.e(tag, exceptionAsString)
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

@Composable
fun CancellableProgressBar(
    modifier: Modifier = Modifier,
    progress: Float,
    cancelled: Boolean = false,
    finished: Boolean = false,
) {
    Box(modifier = modifier) {
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
            modifier = Modifier
                .height(32.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .align(Alignment.Center) // Center the progress indicator
        )

        if (cancelled) {
            CancelAnimation(
                modifier = Modifier
                    .align(Alignment.Center)
                    .height(100.dp)
                    .width(100.dp)
            )
        }
    }
}

@Composable
fun CancelAnimation(
    modifier: Modifier = Modifier,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.cancel3))
    val progress by animateLottieCompositionAsState(composition)
    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = modifier
    )
}

@Preview
@Composable
fun ProgressBarPreview() {
    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp)
    ) {
        CancellableProgressBar(
            progress = 0.3f
        )
    }
}

// Custom Modifier
fun Modifier.firstLevelIndent() = this.then(padding(start = 20.dp))
fun Modifier.secondLevelIndent() = this.then(padding(start = 40.dp))
fun Modifier.thirdLevelIndent() = this.then(padding(start = 60.dp))
