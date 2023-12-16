package nick.mirosh.androidsamples.ui.jetpack_compose.drag_drop_modifier

import android.util.Log
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
import nick.mirosh.androidsamples.ui.coroutines.Teal
import nick.mirosh.androidsamples.ui.coroutines.Yellow
import nick.mirosh.androidsamples.ui.coroutines.myGreen
import kotlin.math.roundToInt


const val TAG = "DragDropModifierScreen"


@Composable
fun GeneratedBox(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(20.dp))
                .border(32.dp, Purple)
                .background(Yellow)
                .padding(32.dp)
                .border(32.dp, Blue)
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
    var offset2 by remember { mutableStateOf(Offset.Zero) }
    val modifier1TargetPosition1 = Offset(100f, -800f) // Define the target position
    val modifier1TargetPosition2 = Offset(100f, -650f) // Define the target position
    val modifier1TargetPosition3 = Offset(100f, -500f) // Define the target position
    val modifier1TargetPosition4 = Offset(100f, -350f) // Define the target position

    val modifier2TargetPosition1 = Offset(100f, -880f) // Define the target position
    val modifier2TargetPosition2 = Offset(100f, -720f) // Define the target position
    val modifier2TargetPosition3 = Offset(100f, -550f) // Define the target position
    val modifier2TargetPosition4 = Offset(100f, -400f) // Define the target position

    var modifier1InTargetArea1 by remember { mutableStateOf(false) }
    var modifier1InTargetArea2 by remember { mutableStateOf(false) }
    var modifier1InTargetArea3 by remember { mutableStateOf(false) }
    var modifier1InTargetArea4 by remember { mutableStateOf(false) }

    var modifier2InTargetArea1 by remember { mutableStateOf(false) }
    var modifier2InTargetArea2 by remember { mutableStateOf(false) }
    var modifier2InTargetArea3 by remember { mutableStateOf(false) }
    var modifier2InTargetArea4 by remember { mutableStateOf(false) }
    val modifier1InAnyTargetArea =
        modifier1InTargetArea1 || modifier1InTargetArea2 || modifier1InTargetArea3 || modifier1InTargetArea4

    val modifier2InAnyTargetArea =
        modifier2InTargetArea1 || modifier2InTargetArea2 || modifier2InTargetArea3 || modifier2InTargetArea4

    Box(modifier = Modifier.fillMaxSize()) {
        HighlightedText2(
            modifier = Modifier
                .padding(16.dp)
        )
        Text(
            text = ".padding(24.dp) ",
            color = if (modifier1InAnyTargetArea) myGreen() else Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            lineHeight = 30.sp,
            modifier = Modifier
                .padding(start = 20.dp, top = 390.dp)
                .offset { IntOffset(offset.x.roundToInt(), offset.y.roundToInt()) }
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        val newPosition = offset + dragAmount
                        offset = newPosition
                        modifier1InTargetArea1 =
                            (newPosition - modifier1TargetPosition1).getDistance() < 50f // Adjust this threshold as needed

                        modifier1InTargetArea2 =
                            (newPosition - modifier1TargetPosition2).getDistance() < 50f // Adjust this threshold as needed

                        modifier1InTargetArea3 =
                            (newPosition - modifier1TargetPosition3).getDistance() < 50f // Adjust this threshold as needed

                        modifier1InTargetArea4 =
                            (newPosition - modifier1TargetPosition4).getDistance() < 50f // Adjust this threshold as needed


                        change.consume()
                    }
                }
        )

        Text(
            text = ".clip(RoundedCornerShape(16.dp)) ",
            color = if (modifier2InAnyTargetArea) myGreen() else Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            lineHeight = 30.sp,
            modifier = Modifier
                .padding(start = 20.dp, top = 430.dp)
                .offset { IntOffset(offset2.x.roundToInt(), offset2.y.roundToInt()) }
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        val newPosition = offset2 + dragAmount
                        offset2 = newPosition
                        modifier2InTargetArea1 =
                            (newPosition - modifier2TargetPosition1).getDistance() < 50f // Adjust this threshold as needed

                        modifier2InTargetArea2 =
                            (newPosition - modifier2TargetPosition2).getDistance() < 50f // Adjust this threshold as needed

                        modifier2InTargetArea3 =
                            (newPosition - modifier2TargetPosition3).getDistance() < 50f // Adjust this threshold as needed

                        modifier2InTargetArea4 =
                            (newPosition - modifier2TargetPosition4).getDistance() < 50f // Adjust this threshold as needed
                        change.consume()
                    }
                }
        )


        var firstModifierDragDrop: ModifierDragDrop? = null
        var secondModifierDragDrop: ModifierDragDrop? = null

        if (modifier1InTargetArea1 || modifier1InTargetArea2 || modifier1InTargetArea3 || modifier1InTargetArea4) {
            firstModifierDragDrop = ModifierDragDrop(
                position = if (modifier1InTargetArea1) 1 else if (modifier1InTargetArea2) 2 else if (modifier1InTargetArea3) 3 else 4,
                modifier = Modifier
                    .padding(32.dp)
            )

        }

        if (modifier2InTargetArea1 || modifier2InTargetArea2 || modifier2InTargetArea3 || modifier2InTargetArea4) {
            secondModifierDragDrop = ModifierDragDrop(
                position = if (modifier2InTargetArea1) 1 else if (modifier2InTargetArea2) 2 else if (modifier2InTargetArea3) 3 else 4,

                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
            )
        }
        ModifierChallenge2Solved3(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            firstModifier = firstModifierDragDrop ?: ModifierDragDrop(0, Modifier),
            secondModifier = secondModifierDragDrop ?: ModifierDragDrop(0, Modifier)
        )
    }
}

@Composable
fun HighlightedText2(
    modifier: Modifier = Modifier,
) {
    val annotatedString = buildAnnotatedString {
        val regularStyle =
            SpanStyle(color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.Bold)


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
            append(")")
        }
    }

    Text(
        modifier = modifier,
        text = annotatedString,
        letterSpacing = 1.sp,
        lineHeight = 32.sp,
    )
}

@Composable
fun ModifierChallenge2Solved3(
    modifier: Modifier = Modifier,
    firstModifier: ModifierDragDrop,
    secondModifier: ModifierDragDrop,
) {
    Log.d(TAG, "ModifierChallenge2Solved3: $firstModifier")
    Log.d(TAG, "ModifierChallenge2Solved3: $secondModifier")
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .then(if (firstModifier.position == 1) firstModifier.modifier else Modifier)
                .then(if (secondModifier.position == 1) secondModifier.modifier else Modifier)
                .size(200.dp)
                .then(if (firstModifier.position == 2) firstModifier.modifier else Modifier)
                .then(if (secondModifier.position == 2) secondModifier.modifier else Modifier)
                .border(32.dp, Purple)
                .then(if (firstModifier.position == 3) firstModifier.modifier else Modifier)
                .then(if (secondModifier.position == 3) secondModifier.modifier else Modifier)
                .background(Yellow)
                .then(if (firstModifier.position == 4) firstModifier.modifier else Modifier)
                .then(if (secondModifier.position == 4) secondModifier.modifier else Modifier)
                .border(32.dp, Blue)
                .align(Alignment.Center)
        )
    }
}

@Composable
fun ModifierChallenge2Solved4(
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .size(200.dp)
                .border(32.dp, Purple)
                .padding(32.dp)
                .background(Yellow)
                .clip(RoundedCornerShape(20.dp))
                .border(32.dp, Blue)
                .align(Alignment.Center)
        )
    }
}

@Preview(name = "New_Pixel_2_API_UpsideDownCake")
@Composable
fun ModifierChallengePreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        DragDropModifierScreen()
    }
}

