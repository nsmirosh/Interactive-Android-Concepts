package nick.mirosh.androidsamples.ui.animation

import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.cos
import kotlin.math.sin

const val TAG = "AnimationContent"

@Composable
fun AnimationContent() {
    var isAnimationRunning by remember { mutableStateOf(false) }
    Column {

        Content(isAnimationRunning)
        Button(
            modifier = Modifier.padding(top = 16.dp),
            onClick = {
                isAnimationRunning = true
            }) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    "Start",
                    fontSize = 30.sp,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun Content(isAnimationRunning: Boolean) {
    // Define the animation duration
    val animationDuration = 1000 // in milliseconds
    val density = LocalDensity.current
    val widthInPixels = with(density) { 400.dp.toPx() }
    val oneThird = widthInPixels / 3
    val twoThirds = widthInPixels / 3 * 2
    // Define the animated value for the x-coordinate
    var firstAnimationFinished by remember { mutableStateOf(false) }
    val animatedX: Float by animateFloatAsState(
        targetValue = if (firstAnimationFinished) twoThirds else oneThird,
        animationSpec = tween(durationMillis = animationDuration), label = ""
    )
    val animatedX2: Float by animateFloatAsState(
        targetValue = if (firstAnimationFinished) widthInPixels else twoThirds,
        animationSpec = tween(durationMillis = animationDuration), label = ""
    )

    val animatedX3: Float by animateFloatAsState(
        targetValue = if (firstAnimationFinished) oneThird else 0f,
        animationSpec = tween(durationMillis = animationDuration), label = ""
    )

    var secondPartFinished: Boolean by remember { mutableStateOf(false) }
    val sweepAngle by animateFloatAsState(
        targetValue = if (isAnimationRunning) 0f else -180f,
        animationSpec = tween(delayMillis = animationDuration, durationMillis = animationDuration),
        label = "", finishedListener = {
            Log.d("Animation", "first animation finished")
            secondPartFinished = true
        }
    )
    val sweepAngleFirst by animateFloatAsState(
        targetValue = if (isAnimationRunning) 180f else 0f,
        animationSpec = tween(durationMillis = animationDuration), label = "", finishedListener = {
            Log.d("Animation", "first animation finished")
            firstAnimationFinished = true
        }
    )

    Box(
        modifier = Modifier
            .height(400.dp)
            .width(400.dp)
            .border(1.dp, Color.Black)
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            // Calculate the start and end coordinates of the arc
            val startAngleRadians = if (firstAnimationFinished) 180f else 0f
            val endAngleRadians = if (firstAnimationFinished) sweepAngle else sweepAngleFirst
            val arcCenter = Offset(if (firstAnimationFinished) animatedX3 else 0f, 0f)

            drawCircle(
                radius = 25f,
                color = Color.Red,
                center = Offset(animatedX, size.height / 2 + 15)
            )
            drawCircle(
                radius = 25f,
                color = Color.Red,
                center = Offset(animatedX2, size.height / 2 + 15)
            )
            drawArc(
                color = Color.Red,
                topLeft = arcCenter,
                startAngle = startAngleRadians,
                sweepAngle = endAngleRadians,
                useCenter = false,
                style = Stroke(width = 50f, cap = StrokeCap.Round)
            )
        }
    }
}


@Composable
fun AnimatedCircle() {
    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 180f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val circleRadius = 50.dp

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val arcRadius = canvasWidth / 2
            val circleY = canvasHeight - circleRadius.toPx()

            val radian = Math.toRadians(angle.toDouble()).toFloat()
            val circleX = arcRadius + arcRadius * kotlin.math.cos(radian) - circleRadius.toPx()
            val offsetX = circleX * kotlin.math.cos(radian)
            val offsetY = circleY - circleY * kotlin.math.sin(radian)

            drawCircle(
                color = Color.Black,
                radius = circleRadius.toPx(),
                center = Offset(offsetX, offsetY)
            )
        }
    }
}


@Composable
fun HalfCircleMotion() {
    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 360f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    var lastXPosition by remember { mutableStateOf(0f)}
    var lastYPosition by remember { mutableStateOf(0f)}

//    val animation = animateFloatAsState(
//        targetValue = 0f,
//        animationSpec = infiniteRepeatable(
//            animation = tween(durationMillis = 2000, easing = LinearEasing),
//            repeatMode = RepeatMode.Restart
//        )
//    )
//
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = this.center
            val radius = size.minDimension / 4
            val angleRad = Math.toRadians(angle.toDouble())
            val x = center.x + radius * cos(angleRad)
            val y = center.y - radius * sin(angleRad)
            if (angle in 180f..360f) {
                drawCircle(
                    Color.Red,
                    50f,
                    center = center.copy(x = x.toFloat(), y = y.toFloat()),
                )
                lastXPosition = x.toFloat()
                lastYPosition = y.toFloat()
            }
            else {
                drawCircle(
                    Color.Red,
                    50f,
                    center = center.copy(x = lastXPosition, y = lastYPosition),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MaterialTheme {
        AnimatedCircle()
    }
}
