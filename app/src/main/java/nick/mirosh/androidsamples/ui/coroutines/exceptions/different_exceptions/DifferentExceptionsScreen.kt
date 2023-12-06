package nick.mirosh.androidsamples.ui.coroutines.exceptions.different_exceptions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import nick.mirosh.androidsamples.R
import nick.mirosh.androidsamples.ui.coroutines.ProgressBarWithLabel
import nick.mirosh.androidsamples.ui.coroutines.thirdLevelIndent


@Composable
fun DifferentExceptionsScreen(
    viewModel: DifferentExceptionsViewModel = hiltViewModel()
) {

    val task1Updates by viewModel.task1Flow.collectAsStateWithLifecycle()
    val task2Updates by viewModel.task2Flow.collectAsStateWithLifecycle()
    val task3Updates by viewModel.task3Flow.collectAsStateWithLifecycle()

    val firstCoroutineCancelled by viewModel.firstCoroutineCancelled.collectAsStateWithLifecycle()
    val secondCoroutineCancelled by viewModel.secondCoroutineCancelled.collectAsStateWithLifecycle()
    val thirdCoroutineCancelled by viewModel.thirdCoroutineCancelled.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp)
    ) {

        Button(onClick = {
            viewModel.simpleChallenge()
        }) {
            Text("start")
        }
        HighlightedText()
        val progressBarModifier = Modifier
            .thirdLevelIndent()
            .padding(top = 8.dp, bottom = 8.dp, end = 16.dp)

        if (firstCoroutineCancelled) {
            CancelAnimation(
                modifier = Modifier
                    .height(50.dp)
                    .width(50.dp)
                    .background(Color.White)
            )
        }
        ProgressBarWithLabel(
            modifier = progressBarModifier,
            progress = task1Updates.progress,
            label = "delay(${task1Updates.label})"
        )
        HighlightedText2()


        if (secondCoroutineCancelled) {
            CancelAnimation(
                modifier = Modifier
                    .height(50.dp)
                    .width(50.dp)
                    .background(Color.White)
            )
        }
        ProgressBarWithLabel(
            modifier = progressBarModifier,
            progress = task2Updates.progress,
            label = "delay(${task2Updates.label})"
        )

        HighlightedText3()
        if (thirdCoroutineCancelled) {
           CancelAnimation(
               modifier = Modifier
                   .height(50.dp)
                   .width(50.dp)
                   .background(Color.White)
           )
        }
        ProgressBarWithLabel(
            modifier = progressBarModifier,
            progress = task3Updates.progress,
            label = "delay(${task3Updates.label})"
        )
        HighlightedText4()
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

const val indent = ""
const val secondIndent = "$indent    "
const val thirdIndent = "$secondIndent    "

@Composable
fun HighlightedText() {
    val annotatedString = buildAnnotatedString {
        val keywordStyle =
            SpanStyle(color = Color.Blue, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        val keywordStyle2 =
            SpanStyle(color = Color.Magenta, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        val regularStyle =
            SpanStyle(color = Color.Black, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        withStyle(regularStyle) {
            append("CoroutineScope(Dispatchers.")
        }
        withStyle(keywordStyle2) {
            append("IO")
        }
        withStyle(regularStyle) {
            append(")\n")
        }
        withStyle(regularStyle) {
            append("$indent.")
        }

        withStyle(keywordStyle) {
            append("launch")
        }
        withStyle(regularStyle) {
            append(" {\n")
        }
        withStyle(keywordStyle) {
            append("$secondIndent launch")
        }

        withStyle(regularStyle) {
            append(" {")
        }
    }

    Text(
        lineHeight = 30.sp,
        text = annotatedString,
        letterSpacing = 1.sp,
    )
}


@Composable
fun HighlightedText2() {
    val annotatedString = buildAnnotatedString {
        val keywordStyle =
            SpanStyle(color = Color.Blue, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        val keywordStyle2 =
            SpanStyle(color = Color.Magenta, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        val regularStyle =
            SpanStyle(color = Color.Black, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        withStyle(regularStyle) {
            append("$thirdIndent throw ")
        }
        withStyle(keywordStyle2) {
            append("RuntimeException")
        }
        withStyle(regularStyle) {
            append("()\n")
        }

        withStyle(regularStyle) {
            append("$secondIndent}\n\n")
        }

        withStyle(keywordStyle) {
            append("$secondIndent launch")
        }

        withStyle(regularStyle) {
            append(" {")
        }

    }

    Text(
        text = annotatedString,
        letterSpacing = 1.sp,
    )
}

@Composable
fun HighlightedText3() {
    val annotatedString = buildAnnotatedString {
        val keywordStyle =
            SpanStyle(color = Color.Blue, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        val keywordStyle2 =
            SpanStyle(color = Color.Magenta, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        val regularStyle =
            SpanStyle(color = Color.Black, fontSize = 22.sp, fontWeight = FontWeight.Bold)

        withStyle(regularStyle) {
            append("$secondIndent}\n\n")
        }

        withStyle(keywordStyle) {
            append("$secondIndent launch")
        }

        withStyle(regularStyle) {
            append(" {")
        }

    }

    Text(
        text = annotatedString,
        letterSpacing = 1.sp,
    )
}

@Composable
fun HighlightedText4() {
    val annotatedString = buildAnnotatedString {
        val keywordStyle =
            SpanStyle(color = Color.Blue, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        val keywordStyle2 =
            SpanStyle(color = Color.Cyan, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        val regularStyle =
            SpanStyle(color = Color.Black, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        withStyle(regularStyle) {
            append("$thirdIndent throw ")
        }
        withStyle(keywordStyle2) {
            append("CancellationException")
        }
        withStyle(regularStyle) {
            append("()\n")
        }

        withStyle(regularStyle) {
            append("$secondIndent}\n")
        }
    }

    Text(
        text = annotatedString,
        letterSpacing = 1.sp,
    )
}


@Preview
@Composable
fun DifferentExceptionsScreenPreview() {
    Box(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxSize()
    ) {
        DifferentExceptionsScreen()
    }
}