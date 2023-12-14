package nick.mirosh.androidsamples.ui.coroutines

import android.util.Log
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import nick.mirosh.androidsamples.R
import nick.mirosh.androidsamples.ui.coroutines.exceptions.different_exceptions.ProgressUpdate
import java.io.PrintWriter
import java.io.StringWriter


const val greenColor = "#00ab41"

fun myGreen() = Color(android.graphics.Color.parseColor(greenColor))

val Purple = Color(0xFF9b5de5)
val Yellow = Color(0xFFfee440)
val Blue = Color(0xFF00bbf9)
fun getColor(color: String) = Color(android.graphics.Color.parseColor(color))

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

suspend fun runElapsingUpdates(
    flow: MutableStateFlow<ProgressUpdate>,
    delay: Long,
) {
    for (i in (delay / 100).toInt() downTo 0) {
        delay(100)
        Log.d(
            "runElapsingUpdates",
            "sendTimeUpdatesInto: $${i.toFloat() / (delay / 100)} i = $i"
        )
        flow.emit(ProgressUpdate(i.toFloat() / (delay / 100), " ${i / 10},${i % 10}s"))
    }
}

fun Throwable.logStackTrace(tag: String) {
    val sw = StringWriter()
    this.printStackTrace(PrintWriter(sw))
    val exceptionAsString = sw.toString()
    Log.e(tag, exceptionAsString)
}

@Composable
fun ProgressBarWithLabel(
    modifier: Modifier = Modifier,
    progress: Float,
    label: String,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(45.dp),
        contentAlignment = Alignment.Center
    )
    {
        ProgressBar(
            modifier = Modifier.fillMaxSize(),
            progress = progress
        )
        Text(
            text = label,
            color = Color.White, fontSize = 20.sp,
            letterSpacing = 1.sp,

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

@Composable
fun CancellableProgressBar(
    modifier: Modifier = Modifier,
    progress: Float,
    label: String = "",
    cancelled: Boolean = false,
    finished: Boolean = false,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
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
        if (cancelled) {
            CancelAnimation(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .height(50.dp)
                    .width(50.dp)
            )
        }

        LinearProgressIndicator(
            progress = progressAnimation,
            modifier = Modifier
                .height(32.dp)
                .padding(start = 60.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .align(Alignment.Center) // Center the progress indicator
        )

        if (label.isNotEmpty()) {
            Text(
                modifier = Modifier,
                text = label,
                color = Color.White,
                fontSize = 20.sp
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
        Column {
            CancellableProgressBar(
                progress = 0.3f,
                label = "30%",
                cancelled = false
            )
            CancellableProgressBar(
                progress = 0.3f,
                label = "30%",
                cancelled = true
            )
            ProgressBarWithLabel(
                modifier = Modifier.padding(start = 32.dp),
                progress = 0.5f,
                label = "30%",
            )
        }
    }
}

// Custom Modifier
fun Modifier.firstLevelIndent() = this.then(padding(start = 20.dp))
fun Modifier.secondLevelIndent() = this.then(padding(start = 40.dp))
fun Modifier.thirdLevelIndent() = this.then(padding(start = 60.dp))
