package nick.mirosh.androidsamples.ui.jetpack_compose.measuring

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.tooling.preview.Preview
import kotlin.math.ceil

private const val TAG = "MeasuringComposable"

@Composable
fun MeasuringComposable(
    passedInText: String
) {
    var text by remember { mutableStateOf(passedInText) }

    var textX by remember { mutableFloatStateOf(0f) }
    var buttonX by remember { mutableFloatStateOf(0f) }
    var widthOfText by remember { mutableIntStateOf(0) }
    LaunchedEffect(
        buttonX
    ) {
        val shouldCut = buttonX - textX < widthOfText

        Log.d(
            TAG, "buttonX = $buttonX" +
                    "\n textX = $textX" +
                    "\n widthOfText = $widthOfText"
        )
        if (shouldCut) {
            val avCharWidth = (widthOfText / text.length)
            val spaceToCut = widthOfText - (buttonX - textX)
            val charToCut = ceil(spaceToCut / avCharWidth).toInt() + 2
            text = "${text.substring(0, text.length - charToCut)}.."
            Log.d(
                TAG,
                "buttonX: $buttonX," +
                        "\n textX: $textX," +
                        "\n widthOfText: $widthOfText, " +
                        "\n avCharWidth: $avCharWidth, " +
                        "\n spaceToCut: $spaceToCut, " +
                        "\n charToCut: $charToCut"
            )
        }
    }
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {

        Button(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .onGloballyPositioned {
                    buttonX = it.positionInRoot().x
                },
            onClick = {
                text += " Word"
            }
        ) {
            Text("Increase text size")
        }
        Text(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .onGloballyPositioned {
                    textX = it.positionInRoot().x
                    widthOfText = it.size.width
                },
            text = text
        )
//        val density: Density = LocalDensity.current
//        if (buttonX != 0f) {
//            Text(
//                "X",
//                modifier = Modifier
//                    .offset(x = with(density) {
//                        buttonX.toDp()
//                    }, y = 20.dp),
//                style = TextStyle(fontSize = 20.sp, color = androidx.compose.ui.graphics.Color.Red)
//            )
//        }
    }
}

@Preview(
    showBackground = true,
)
@Composable
fun MeasuringComposablePreview() {
    MeasuringComposable("balls")
}

@Preview(
    showBackground = true,
)
@Composable
fun MeasuringComposablePreview2() {
    MeasuringComposable("balls asdfkasljd;faskjdf;laskdjf fjdkfjdksfjj fjdkfjskdfj dkjfjskdfj")
}

@Preview(
    showBackground = true,
)
@Composable
fun MeasuringComposablePreview3() {
    MeasuringComposable("balls asdfkasljd;faskjdf;fjdkfjdksfjj fjdkfjskdfj dkjfjskdfj")
}
