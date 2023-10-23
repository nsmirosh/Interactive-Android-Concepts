package nick.mirosh.androidsamples.ui.animation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

const val ANIMATION_DURATION = 800

enum class AnimationState {
    FIRST_IN_PROGRESS, SECOND_IN_PROGRESS, INITIAL
}

@Composable
fun SmileyLoadingAnimation() {
    var animationState by remember {
        mutableStateOf(AnimationState.INITIAL)
    }
    LaunchedEffect(Unit) {
        while (true) {
            animationState = AnimationState.FIRST_IN_PROGRESS
            delay(ANIMATION_DURATION.toLong())
            animationState = AnimationState.SECOND_IN_PROGRESS
            delay(ANIMATION_DURATION.toLong())
        }
    }
    SmileyDrawer(animationState)
}

@Composable
fun SmileyDrawer(animationState: AnimationState) {
    val widthAndHeight = 200.dp
    val density = LocalDensity.current
    val widthInPixels = with(density) { widthAndHeight.toPx() }
    val oneThird = widthInPixels / 3
    val twoThirds = widthInPixels / 3 * 2
    val circleRadius = 30f
    val animationColor = Color.Blue

    val animatedX: Float by animateFloatAsState(
        targetValue = if (animationState == AnimationState.SECOND_IN_PROGRESS) twoThirds else oneThird,
        animationSpec = tween(durationMillis = ANIMATION_DURATION), label = ""
    )
    val animatedX2: Float by animateFloatAsState(
        targetValue = if (animationState == AnimationState.SECOND_IN_PROGRESS) widthInPixels else twoThirds,
        animationSpec = tween(durationMillis = ANIMATION_DURATION), label = ""
    )
    val animatedX3: Float by animateFloatAsState(
        targetValue = if (animationState == AnimationState.SECOND_IN_PROGRESS) oneThird else 0f,
        animationSpec = tween(durationMillis = ANIMATION_DURATION), label = ""
    )
    val sweepAngle by animateFloatAsState(
        targetValue = if (animationState == AnimationState.FIRST_IN_PROGRESS) 180f else 0f,
        animationSpec = tween(durationMillis = ANIMATION_DURATION),
        label = "",
    )
    val sweepAngle2 by animateFloatAsState(
        targetValue = if (animationState == AnimationState.SECOND_IN_PROGRESS) 0f else -180f,
        animationSpec = tween(durationMillis = ANIMATION_DURATION),
        label = "",
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Canvas(
            modifier = Modifier
                .height(widthAndHeight)
                .width(widthAndHeight)
                .align(Alignment.Center)
        ) {
            val circleY = size.height / 2
            if (animationState == AnimationState.FIRST_IN_PROGRESS) {
                drawCircle(
                    radius = circleRadius,
                    color = animationColor,
                    center = Offset(widthInPixels, circleY)
                )
                drawCircle(
                    radius = circleRadius,
                    color = animationColor,
                    center = Offset(oneThird, circleY)
                )
                drawCircle(
                    radius = circleRadius,
                    color = animationColor,
                    center = Offset(twoThirds, circleY)
                )
                drawArc(
                    color = animationColor,
                    topLeft = Offset(0f, 0f),
                    startAngle = 0f,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    style = Stroke(
                        width = circleRadius * 2,
                        cap = StrokeCap.Round
                    )
                )
            }
            else {
                val arcCenter =
                    Offset(animatedX3, 0f)
                drawCircle(
                    radius = circleRadius,
                    color = animationColor,
                    center = Offset(animatedX, circleY)
                )
                drawCircle(
                    radius = circleRadius,
                    color = animationColor,
                    center = Offset(animatedX2, circleY)
                )
                drawArc(
                    color = animationColor,
                    topLeft = arcCenter,
                    startAngle = 180f,
                    sweepAngle = sweepAngle2,
                    useCenter = false,
                    style = Stroke(
                        width = circleRadius * 2,
                        cap = StrokeCap.Round
                    )
                )
            }
        }
    }
}
