package nick.mirosh.androidsamples.ui.coroutines.exceptions.different_exceptions

import android.util.Log
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import nick.mirosh.androidsamples.R
import nick.mirosh.androidsamples.ui.coroutines.ProgressBarWithLabel


const val textEnlargeAnimationDuration = 500

@Composable
fun DifferentExceptionsScreen(
    viewModel: DifferentExceptionsViewModel = hiltViewModel()
) {

    val task1Updates by viewModel.task1Flow.collectAsStateWithLifecycle()
    val task2Updates by viewModel.task2Flow.collectAsStateWithLifecycle()
    val task3Updates by viewModel.task3Flow.collectAsStateWithLifecycle()

    val firstCoroutineCancelled by viewModel.firstCoroutineCancelled.collectAsStateWithLifecycle()
    val thirdCoroutineCancelled by viewModel.thirdCoroutineCancelled.collectAsStateWithLifecycle()


    val runTimeExceptFontAnimation by animateFloatAsState(
        targetValue = if (firstCoroutineCancelled) 26f else 22f,
        label = "animatedFontSize",
        animationSpec = TweenSpec(durationMillis = textEnlargeAnimationDuration)
    )

    val cancellationExceptFontAnimation by animateFloatAsState(
        targetValue = if (thirdCoroutineCancelled) 26f else 22f,
        label = "cancelledFontSize",
        animationSpec = TweenSpec(durationMillis = textEnlargeAnimationDuration)
    )

    ConstraintLayout(
        modifier = Modifier
            .padding(start = 8.dp)
            .fillMaxSize()
    ) {

        val (button, text1, text2, text3, cancellationExceptionText, runTimeExceptionText,
            cancel1, cancel2, cancel3, almostFinalBrace, finalBrace,

            progress1, progress2, progress3) = createRefs()

        val startGuideline = createGuidelineFromStart(0.15f)
        Button(
            modifier = Modifier
                .width(
                    100.dp
                )
                .constrainAs(button) {
                    top.linkTo(parent.top, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            onClick = {
                viewModel.simpleChallenge()
            }) {
            Text(
                fontSize =18.sp,
                fontWeight = FontWeight.Bold,
                text ="Start")
        }

        HighlightedText(
            modifier = Modifier.constrainAs(text1) {
                top.linkTo(button.bottom, margin = 16.dp)
                start.linkTo(parent.start)
            }
        )
        val progressBarModifier = Modifier
            .padding(top = 8.dp, bottom = 8.dp, end = 16.dp)

        ProgressBarWithLabel(
            modifier = progressBarModifier
                .width(300.dp)
                .padding(start = 8.dp)
                .constrainAs(progress1) {
                    start.linkTo(startGuideline)
                    top.linkTo(text1.bottom)

                },
            progress = task1Updates.progress,
            label = "delay(${task1Updates.label})"
        )
        ThrowRuntimeExceptionText(
            fontSize = runTimeExceptFontAnimation,
            modifier = Modifier.constrainAs(runTimeExceptionText) {
                top.linkTo(progress1.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
        )
        var runtimeAnimationPlayed by remember { mutableStateOf(false) }

        LaunchedEffect(runTimeExceptFontAnimation) {
            if (runTimeExceptFontAnimation == 26f) {
                runtimeAnimationPlayed = true
            }
        }
        if (runtimeAnimationPlayed) {
            CancelAnimation(
                modifier = Modifier
                    .constrainAs(cancel1) {
                        bottom.linkTo(runTimeExceptionText.bottom)
                        top.linkTo(progress1.top)
                        start.linkTo(progress1.start)
                        end.linkTo(progress1.end)
                    }
                    .height(140.dp)
                    .width(140.dp)
            )
        }

        HighlightedText2(
            modifier = Modifier
                .constrainAs(text2) {
                    top.linkTo(runTimeExceptionText.bottom)
                }
                .fillMaxWidth(),
        )

        ProgressBarWithLabel(
            modifier = progressBarModifier
                .width(300.dp)
                .padding(start = 8.dp)
                .constrainAs(progress2) {
                    start.linkTo(startGuideline)
                    top.linkTo(text2.bottom)

                },
            progress = task2Updates.progress,
            label = "delay(${task2Updates.label})"
        )

        if (runtimeAnimationPlayed) {
            CancelAnimation(
                modifier = Modifier
                    .constrainAs(cancel2) {
                        bottom.linkTo(progress2.bottom)
                        top.linkTo(progress2.top)
                        start.linkTo(progress2.start)
                        end.linkTo(progress2.end)
                    }
                    .height(120.dp)
                    .width(120.dp)
            )
        }
        HighlightedText3(
            modifier = Modifier.constrainAs(text3) {
                top.linkTo(progress2.bottom)
            }
        )
        ProgressBarWithLabel(
            modifier = progressBarModifier
                .width(300.dp)
                .padding(start = 8.dp)
                .constrainAs(progress3) {
                    start.linkTo(startGuideline)
                    top.linkTo(text3.bottom)

                },
            progress = task3Updates.progress,
            label = "delay(${task3Updates.label})"
        )

        var cancellationAnimPlayed by remember { mutableStateOf(false) }

        LaunchedEffect(cancellationExceptFontAnimation) {
            if (cancellationExceptFontAnimation == 26f) {
                cancellationAnimPlayed = true
            }
        }
        CancellationExceptionText(
            fontSize = cancellationExceptFontAnimation,
            modifier = Modifier.constrainAs(cancellationExceptionText) {
                top.linkTo(progress3.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        if (cancellationAnimPlayed) {
            CancelAnimation(
                modifier = Modifier
                    .constrainAs(cancel3) {
                        bottom.linkTo(cancellationExceptionText.bottom)
                        top.linkTo(progress3.top)
                        start.linkTo(progress3.start)
                        end.linkTo(progress3.end)
                    }
                    .height(140.dp)
                    .width(140.dp)
            )
        }
        Text(
            color = Color.Black,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.constrainAs(almostFinalBrace) {
                top.linkTo(cancellationExceptionText.bottom)
            },
            text = "    }",
        )
        Text(
            color = Color.Black,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.constrainAs(finalBrace) {
                top.linkTo(almostFinalBrace.bottom)
            },
            text = "}",
        )
    }
}

@Composable
fun CancelAnimation(
    modifier: Modifier = Modifier,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.fast_cancel))
    val progress by animateLottieCompositionAsState(
        composition,
        speed = 0.5f,
    )
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
fun HighlightedText(modifier: Modifier = Modifier) {
    val annotatedString = buildAnnotatedString {
        val keywordStyle =
            SpanStyle(color = Color.Blue, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        val regularStyle =
            SpanStyle(color = Color.Black, fontSize = 22.sp, fontWeight = FontWeight.Bold)

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
        modifier = modifier,
        lineHeight = 32.sp,
        text = annotatedString,
        letterSpacing = 1.sp,
    )
}


@Composable
fun ThrowRuntimeExceptionText(
    modifier: Modifier = Modifier,
    fontSize: Float = 22f
) {
    Log.d(TAG, "fontSize = $fontSize")
    val annotatedString = buildAnnotatedString {
        val increasingSizeKeywordStyle2 = SpanStyle(
            color = Color.Magenta,
            fontSize = fontSize.sp,
            fontWeight = FontWeight.Bold
        )
        val increasingSizeRegularStyle = SpanStyle(
            color = Color.Black,
            fontSize = fontSize.sp,
            fontWeight = FontWeight.Bold
        )
        withStyle(increasingSizeRegularStyle) {
            append("throw ")
        }
        withStyle(increasingSizeKeywordStyle2) {
            append("RuntimeException")
        }
        withStyle(increasingSizeRegularStyle) {
            append("()")
        }
    }
    Box(
        modifier = modifier
            .height(60.dp)
            .fillMaxWidth(),
    ) {
        Text(
            modifier = Modifier
                .align(alignment = Alignment.Center),
            text = annotatedString,
            letterSpacing = 1.sp,
        )
    }
}


@Composable
fun HighlightedText2(
    modifier: Modifier = Modifier,
) {
    val annotatedString = buildAnnotatedString {
        val keywordStyle =
            SpanStyle(color = Color.Blue, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        val regularStyle =
            SpanStyle(color = Color.Black, fontSize = 22.sp, fontWeight = FontWeight.Bold)

        withStyle(regularStyle) {
            append("$secondIndent}\n")
        }

        withStyle(keywordStyle) {
            append("${secondIndent}launch")
        }

        withStyle(regularStyle) {
            append(" {")
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
fun HighlightedText3(
    modifier: Modifier = Modifier
) {
    val annotatedString = buildAnnotatedString {
        val keywordStyle =
            SpanStyle(color = Color.Blue, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        val regularStyle =
            SpanStyle(color = Color.Black, fontSize = 22.sp, fontWeight = FontWeight.Bold)

        withStyle(regularStyle) {
            append("$secondIndent}\n")
        }

        withStyle(keywordStyle) {
            append("${secondIndent}launch")
        }

        withStyle(regularStyle) {
            append(" {")
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
fun CancellationExceptionText(
    modifier: Modifier = Modifier,
    fontSize: Float = 22f,
) {
    val annotatedString = buildAnnotatedString {
        val keywordStyle2 =
            SpanStyle(color = Color.Cyan, fontSize = fontSize.sp, fontWeight = FontWeight.Bold)
        val regularStyle =
            SpanStyle(color = Color.Black, fontSize = fontSize.sp, fontWeight = FontWeight.Bold)
        withStyle(regularStyle) {
            append("throw ")
        }
        withStyle(keywordStyle2) {
            append("CancellationException")
        }
        withStyle(regularStyle) {
            append("()")
        }
    }

    Box(
        modifier = modifier
            .height(60.dp)
            .fillMaxWidth(),
    ) {
        Text(
            modifier = Modifier
                .align(alignment = Alignment.Center),
            text = annotatedString,
            letterSpacing = 1.sp,
        )
    }
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