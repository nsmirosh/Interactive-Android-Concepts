package nick.mirosh.androidsamples.ui.progress

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProgressBarContent2(
    viewModel: ProgressBarViewModel,
    modifier: Modifier = Modifier
) {
    var isLinearProgress by remember { mutableStateOf(false) }
    val indicatorProgress by viewModel.progress.collectAsState()
    val progressAnimDuration = 300
    val progressAnimation by animateFloatAsState(
        targetValue = indicatorProgress.toFloat() / 100f,
        animationSpec = tween(
            durationMillis = progressAnimDuration,
            easing = FastOutSlowInEasing
        ),
        label = ""
    )
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(Alignment.Center),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedCounter(count = indicatorProgress)
            Text(
                text = "Start",
                fontSize = 30.sp,
                modifier = Modifier
                    .clickable {
                        viewModel.onStartPressed()
                    }
                    .padding(24.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Switch(
                checked = isLinearProgress,
                onCheckedChange = { isLinearProgress = !isLinearProgress }
            )
            if (isLinearProgress) {
                LinearProgressIndicator(
                    progress = progressAnimation,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .height(32.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
            } else {
                CircularProgressIndicator(
                    progress = progressAnimation,
                    strokeWidth = 16.dp,
                    trackColor = androidx.compose.ui.graphics.Color.LightGray,
                    modifier = Modifier
                        .width(150.dp)
                        .height(150.dp)
                )
            }
        }
    }
}

@Composable
fun IncrementDecrement() {
    var counter by remember { mutableIntStateOf(0) }

    Button(onClick = {
        counter += 8
    }) {
        Text("increment")
    }
    Button(onClick = {
        counter -= 8
    }) {
        Text("decrement")
    }
}

@Composable
fun ProgressBarContent(
    viewModel: ProgressBarViewModel,
    modifier: Modifier = Modifier
) {
    var isAnimationRunning by remember { mutableStateOf(false) }
    Column {

        Content(isAnimationRunning)
        Button(
            modifier = Modifier.padding(top = 16.dp),
            onClick = {
                isAnimationRunning = true
            }) {
            Text("Start")
        }
    }
}

@Composable
fun DrawCanvas() {
    Canvas(
        modifier = Modifier
            .height(100.dp)
            .width(100.dp)
    ) {
        //Draw a half circle line
        drawArc(
            color = Color.Red,
            topLeft = Offset(100f, 100f),
            startAngle = 0f,
            sweepAngle = 180f,
            useCenter = false,
            style = Stroke(width = 10f)
        )
    }
}


@Composable
fun AnimatedArc() {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val sweepAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 180f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    Canvas(
        modifier = Modifier
            .height(100.dp)
            .width(100.dp)
    ) {
        // Draw a half circle line
        drawArc(
            color = Color.Red,
            topLeft = Offset(100f, 100f),
            startAngle = 0f,
            sweepAngle = sweepAngle,
            useCenter = false,
            style = Stroke(width = 15f)
        )
        drawCircle(
            radius = 15f,
            color = Color.Red,
        )

    }
}


@Composable
fun Content(isAnimationRunning: Boolean) {
    // Define the animation duration
    val animationDuration = 1000 // in milliseconds
    val density = LocalDensity.current
    val widthInPixels = with(density) { 150.dp.toPx() }
    val oneThird = widthInPixels / 3
    val twoThirds = widthInPixels / 3 * 2
    // Define the animated value for the x-coordinate
    val animatedX: Float by animateFloatAsState(
        targetValue = if (isAnimationRunning) twoThirds else oneThird,
        animationSpec = tween(durationMillis = animationDuration), label = ""
    )
    val animatedX2: Float by animateFloatAsState(
        targetValue = if (isAnimationRunning) widthInPixels else twoThirds,
        animationSpec = tween(durationMillis = animationDuration), label = ""
    )

    val sweepAngle by animateFloatAsState(
        targetValue = if (isAnimationRunning) 180f else 0f,
        animationSpec = tween(durationMillis = animationDuration), label = "",
    )
    Box(
        modifier = Modifier
            .height(150.dp)
            .width(150.dp)
            .border(1.dp, Color.Black)
            .onSizeChanged {
            }
    ) {

        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            // Draw the circle with the animated x-coordinate
            /*drawCircle(
                radius = 15f,
                color = Color.Red,
                center = Offset(animatedX, size.height / 2 + 15)
            )
            drawCircle(
                radius = 15f,
                color = Color.Red,
                center = Offset(animatedX2, size.height / 2 + 15)
            )*/
            drawCircle(
                radius = 15f,
                color = Color.Red,
                center = Offset(animatedX, size.height / 2 + 15)
            )
            drawCircle(
                radius = 15f,
                color = Color.Red,
                center = Offset(animatedX2, size.height / 2 + 15)
            )
            drawArc(
                color = Color.Red,
                topLeft = Offset(0f, 0f),
                startAngle = 0f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = 15f)
            )
        }
    }
}





