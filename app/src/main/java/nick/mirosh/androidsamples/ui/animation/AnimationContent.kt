package nick.mirosh.androidsamples.ui.animation

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
    val sweepAngle by animateFloatAsState(
        targetValue = if (isAnimationRunning) 0f else -180f,
        animationSpec = tween(delayMillis = animationDuration, durationMillis = animationDuration),
        label = "",
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
            .onSizeChanged {
            }
    ) {

        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            // Draw the circle with the animated x-coordinate
            //drawCircle(
            //    radius = 25f,
            //    color = Color.Red,
            //    center = Offset(animatedX, size.height / 2 + 15)
            //)
            //draw a 50 by 50 pixel red rectangle

            drawRect(
                color = Color.Red,
                topLeft = Offset(animatedX, size.height / 2),
                size = size.copy(width = 50f, height = 50f)
            )
            //drawCircle(
            //    radius = 25f,
            //    color = Color.Red,
            //    center = Offset(animatedX2, size.height / 2 + 15)
            //)
            drawRect(
                color = Color.Red,
                topLeft = Offset(animatedX2, size.height / 2),
                size = size.copy(width = 50f, height = 50f)
            )
            drawArc(
                color = Color.Red,
                topLeft = Offset(if (firstAnimationFinished) animatedX3 else 0f, 0f),
                startAngle = if (firstAnimationFinished) 180f else 0f,
                sweepAngle = if (firstAnimationFinished) sweepAngle else sweepAngleFirst,
                useCenter = false,
                style = Stroke(width = 50f)
            )
        }
    }
}
