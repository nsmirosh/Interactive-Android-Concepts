package nick.mirosh.androidsamples.ui.jetpack_compose.drag_drop_modifier

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nick.mirosh.androidsamples.R
import nick.mirosh.androidsamples.ui.coroutines.Blue
import nick.mirosh.androidsamples.ui.coroutines.Purple
import nick.mirosh.androidsamples.ui.coroutines.Yellow
import kotlin.math.roundToInt


@Composable
fun ModifierChallenge() {
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .width(150.dp)
                .height(150.dp)
//            .size(150.dp)
                .padding(16.dp)
                .border(5.dp, Color.Magenta)
                .padding(10.dp)
                .background(Color.Cyan)
                .padding(5.dp)
        ) {
            Text("Hello Compose", Modifier.align(Alignment.Center))
        }
    }
}

@Composable
fun ModifierChallenge2() {
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .size(150.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(24.dp, Purple)
                .background(Yellow)
                .padding(24.dp)
                .border(24.dp, Blue)
                .align(Alignment.Center)
        )
    }
}

@Composable
fun ModifierChallenge2Solved(clip: Boolean, padding: Boolean) {
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .size(150.dp)
                .then(if (clip) Modifier.clip(RoundedCornerShape(16.dp)) else Modifier)
                .border(24.dp, Purple)
                .background(Yellow)
                .then(if (padding) Modifier.padding(24.dp) else Modifier)
                .border(24.dp, Blue)
                .align(Alignment.Center)
        )
    }
}

data class ModifierDragDrop(
    val position: Int,
    val modifier: Modifier
)

@Composable
fun DragDropModifierScreen() {
    var offset by remember { mutableStateOf(Offset.Zero) }
    val targetPosition = Offset(100f, 100f) // Define the target position
    var inTargetArea by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        HighlightedText2(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp)
        )
        Text(
            text = "Drag me",
            modifier = Modifier
                .offset { IntOffset(offset.x.roundToInt(), offset.y.roundToInt()) }
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        val newPosition = offset + dragAmount
                        offset = newPosition
                        inTargetArea =
                            (newPosition - targetPosition).getDistance() < 100f // Adjust this threshold as needed
                        change.consume()
                    }
                }
        )

        if (inTargetArea) {
            // Snap text into place
            offset = targetPosition
        }

        if (inTargetArea) {
            Text(text = "In target area", Modifier.align(Alignment.Center))
        }

        // Visual representation of the target area
        Box(
            modifier = Modifier
                .size(100.dp)
                .offset { IntOffset(targetPosition.x.roundToInt(), targetPosition.y.roundToInt()) }
//                .background(if (inTargetArea) Color.Green else Color.LightGray)
        )
    }
}


@Composable
fun ModifierText(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    Text(
        modifier = modifier,
        text = context.getString(R.string.modifier_text)
    )
}

@Composable
fun HighlightedText2(
    modifier: Modifier = Modifier,
) {
    val annotatedString = buildAnnotatedString {
        val regularStyle =
            SpanStyle(color = Color.Black, fontSize = 22.sp, fontWeight = FontWeight.Bold)


        withStyle(regularStyle) {
            append("Box(\n")
        }

        withStyle(regularStyle) {
            append("    modifier = Modifier\n\n")
        }

        withStyle(regularStyle) {
            append("        .size(150.dp)\n\n")
        }

        withStyle(regularStyle) {
            append("        .border(24.dp, Purple)\n\n")
        }

        withStyle(regularStyle) {
            append("        .background(Yellow)\n\n")
        }

        withStyle(regularStyle) {
            append("        .border(24.dp, Blue)\n")
        }

        withStyle(regularStyle) {
            append(")\n")
        }
    }

    Text(
        lineHeight = 32.sp,
        modifier = modifier,
        text = annotatedString,
        letterSpacing = 1.sp,
    )
}

@Composable
fun ModifierChallenge2Solved2(firstModifier: ModifierDragDrop, secondModifier: ModifierDragDrop) {
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .then(if (firstModifier.position == 0) firstModifier.modifier else Modifier)
                .then(if (secondModifier.position == 0) secondModifier.modifier else Modifier)
                .size(150.dp)
                .then(if (firstModifier.position == 1) firstModifier.modifier else Modifier)
                .then(if (secondModifier.position == 1) secondModifier.modifier else Modifier)
                .border(24.dp, Purple)
                .then(if (firstModifier.position == 2) firstModifier.modifier else Modifier)
                .then(if (secondModifier.position == 2) secondModifier.modifier else Modifier)
                .background(Yellow)
                .then(if (firstModifier.position == 3) firstModifier.modifier else Modifier)
                .then(if (secondModifier.position == 3) secondModifier.modifier else Modifier)
                .border(24.dp, Blue)
                .then(if (firstModifier.position == 4) firstModifier.modifier else Modifier)
                .then(if (secondModifier.position == 4) secondModifier.modifier else Modifier)
                .align(Alignment.Center)
        )
    }
}


@Preview
@Composable
fun ModifierChallengePreview() {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        DragDropModifierScreen()
    }
}

