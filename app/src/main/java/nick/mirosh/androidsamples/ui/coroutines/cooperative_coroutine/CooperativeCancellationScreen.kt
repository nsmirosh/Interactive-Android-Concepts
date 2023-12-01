import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nick.mirosh.androidsamples.ui.coroutines.ProgressBar
import nick.mirosh.androidsamples.ui.coroutines.cooperative_coroutine.CooperativeCancellationViewModel
import nick.mirosh.androidsamples.ui.coroutines.myGreen


@Composable
fun CooperativeCancellationScreen(
    viewModel: CooperativeCancellationViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    val progress by viewModel.job1flow.collectAsStateWithLifecycle()
    val coroutineStatus by viewModel.coroutineStatus.collectAsStateWithLifecycle()
    var restart by remember {
        mutableStateOf(false)
    }

    MaterialTheme {
        key(restart) {
            var makeCooperativeByCheckingIsActive by remember { mutableStateOf(false) }
            var makeCooperativeByCheckingCancellationException by remember { mutableStateOf(false) }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .verticalScroll(scrollState)
            ) {
                HighlightedCodeText(
                    breakLoop = {
                        viewModel.stopUncooperative()
                    },
                    checkIsActive = makeCooperativeByCheckingIsActive,
                    throwCancellationException = makeCooperativeByCheckingCancellationException
                )
                ProgressBar(
                    progress = progress, modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                )
                Text(
                    modifier = Modifier
                        .align(CenterHorizontally)
                        .padding(top = 8.dp),
                    text = coroutineStatus,
                    fontSize = 24.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    color = getColorBasedOnCoroutineStatus(coroutineStatus)
                )
                Text(
                    text = "        }\n}",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )

                StartingStoppingControls(
                    onStart = {
                        viewModel.start()
                    },
                    onCancel = {
                        viewModel.cancel()
                    }
                )
                CoooperativeCoroutineControls(
                    checkingIsActive = makeCooperativeByCheckingIsActive,
                    onMakeCooperativeByCheckingIsActive = {
                        viewModel.setUpJobWithIsActiveCheck()
                        makeCooperativeByCheckingIsActive = true
                        makeCooperativeByCheckingCancellationException = false
                    },
                    throwingCancellationException = makeCooperativeByCheckingCancellationException,
                    onMakeCooperativeByThrowingCancellationException = {
                        viewModel.setUpJobWithCancellationException()
                        makeCooperativeByCheckingIsActive = false
                        makeCooperativeByCheckingCancellationException = true
                    }
                )

                Button(
                    modifier = Modifier
                        .align(CenterHorizontally)
                        .padding(top = 16.dp),
                    onClick = {
                        viewModel.clear()
                        restart = !restart
                    }) {
                    Text(
                        fontSize = 20.sp,
                        text = "Restart"
                    )
                }
            }
        }
    }
}


fun getColorBasedOnCoroutineStatus(status: String) =
    when (status) {
        "Active" -> myGreen()
        "Completed" -> Color.Blue
        "Cancelled" -> Color.Red
        else -> Color.Black
    }


@Composable
fun StartingStoppingControls(
    onStart: () -> Unit,
    onCancel: () -> Unit
) {
    var cancelled by remember {
        mutableStateOf(false)
    }
    var started by remember {
        mutableStateOf(false)
    }
    Row(
        modifier = Modifier.padding(top = 16.dp),
        verticalAlignment = CenterVertically
    ) {
        Text(
            fontSize = 26.sp,
            text = "job.",
            fontWeight = FontWeight.Bold
        )

        Button(
            colors = if (started) ButtonDefaults.buttonColors(
                backgroundColor = myGreen(),
                contentColor = Color.White
            )
            else ButtonDefaults.buttonColors(),
            onClick = {
                started = true
                onStart()
            }) {
            Text(
                fontSize = 24.sp,
                text = if (started) "Started" else ".start()"
            )
        }
    }
    Row(

        modifier = Modifier.padding(top = 8.dp),
        verticalAlignment = CenterVertically
    ) {
        Text(
            fontSize = 26.sp,
            text = "job.",
            fontWeight = FontWeight.Bold
        )
        Button(
            colors = if (cancelled) ButtonDefaults.buttonColors(
                backgroundColor = Color.Red,
                contentColor = Color.White
            )
            else ButtonDefaults.buttonColors(),
            onClick = {
                cancelled = true
                onCancel()
            }) {
            Text(

                fontSize = 24.sp,
                text = if (cancelled) "Cancelled" else ".cancel()"
            )
        }
    }
}


@Composable
fun CoooperativeCoroutineControls(
    checkingIsActive: Boolean = false,
    throwingCancellationException: Boolean = false,
    onMakeCooperativeByCheckingIsActive: () -> Unit,
    onMakeCooperativeByThrowingCancellationException: () -> Unit,
) {

    var makeCoroutineCooperative by remember {
        mutableStateOf(false)
    }

    Row(
        verticalAlignment = CenterVertically
    ) {
        Checkbox(checked = makeCoroutineCooperative, onCheckedChange = {
            makeCoroutineCooperative = it
        })
        Text(
            text = "Make this coroutine cooperative",
            fontSize = 20.sp,
        )
    }

    if (makeCoroutineCooperative) {
        Row(
            verticalAlignment = CenterVertically
        ) {
            RadioButton(
                selected = checkingIsActive,
                onClick = {
                    onMakeCooperativeByCheckingIsActive()
                }
            )
            Text(
                text = "check if coroutine isActive",
                fontSize = 20.sp,
            )
        }

        Row(
            verticalAlignment = CenterVertically
        ) {
            RadioButton(
                selected = throwingCancellationException,
                onClick = {
                    onMakeCooperativeByThrowingCancellationException()
                }
            )
            Text(
                text = "throw a CancellationException",
                fontSize = 20.sp,
            )
        }
    }
}

@Composable
fun HighlightedCodeText(
    breakLoop: () -> Unit,
    checkIsActive: Boolean = false,
    throwCancellationException: Boolean = false
) {
    var loopBroken by remember { mutableStateOf(false) }
    val annotatedString = buildAnnotatedString {
        val keywordStyle =
            SpanStyle(color = Color.Blue, fontSize = 26.sp, fontWeight = FontWeight.Bold)
        val regularStyle =
            SpanStyle(color = Color.Black, fontSize = 26.sp, fontWeight = FontWeight.Bold)
        val correctStyle =
            SpanStyle(color = myGreen(), fontSize = 26.sp, fontWeight = FontWeight.Bold)
        val wrongStyle =
            SpanStyle(color = Color.Red, fontSize = 26.sp, fontWeight = FontWeight.Bold)
        withStyle(keywordStyle) {
            append("val ")
        }
        withStyle(regularStyle) {
            append("job = GlobalScope.launch {\n")
        }

        withStyle(keywordStyle) {
            append("        while(")
        }

        pushStringAnnotation(tag = "clickable", annotation = "true")
        if (loopBroken) {
            withStyle(wrongStyle) {
                append("false")
            }
        }
        else {
            withStyle(correctStyle) {
                append("true")
            }
        }
        pop()

        if (checkIsActive) {
            withStyle(keywordStyle) {
                append(" && isActive")
            }
        }

        withStyle(keywordStyle) {
            append(") {\n")
        }
        withStyle(regularStyle) {
            append("            ")
        }

        withStyle(regularStyle) {
            append(if (throwCancellationException) "delay(500)" else "Thread.sleep(500)")
        }
    }

    ClickableText(
        text = annotatedString,
        style = TextStyle(lineHeight = 32.sp),
        onClick = { offset ->
            annotatedString.getStringAnnotations(tag = "clickable", start = offset, end = offset)
                .firstOrNull()?.let {
                    loopBroken = true
                    breakLoop()
                }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(name = "New_Pixel_2_API_UpsideDownCake")
@Composable
fun PreviewCooperativeCancellationScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        CooperativeCancellationScreen()
    }
}
