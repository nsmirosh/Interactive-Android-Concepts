import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
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
import nick.mirosh.androidsamples.ui.coroutines.async.ProgressBar
import nick.mirosh.androidsamples.ui.coroutines.cooperative_coroutine.CooperativeCancellationViewModel


@Composable
fun CooperativeCancellationScreen(
    viewModel: CooperativeCancellationViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    val progress by viewModel.job1flow.collectAsStateWithLifecycle()
    val coroutineStatus by viewModel.coroutineStatus.collectAsStateWithLifecycle()
    var stopTheWhileLoop by remember { mutableStateOf(false) }

    var makeCooperativeByCheckingIsActive by remember { mutableStateOf(false) }
    var makeCooperativeByCheckingCancellationException by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        HighlightedCodeText(
            isCooperative = stopTheWhileLoop,
            checkIsActive = makeCooperativeByCheckingIsActive,
            throwCancellationException = makeCooperativeByCheckingCancellationException
        )
        ProgressBar(progress = progress)
        Text(

            modifier = Modifier
                .align(CenterHorizontally)
                .padding(top = 8.dp),
            text = "Coroutine: $coroutineStatus",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "        }\n}",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
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
        StartingStoppingControls(
            onStopUnCooperative = {
                stopTheWhileLoop = true
                viewModel.stopUncooperative()
            },
            onStartCooperative = {
                viewModel.start()
            },
            onCancel = {
                viewModel.cancel()
            }
        )
    }
}


@Composable
fun StartingStoppingControls(
    onStopUnCooperative: () -> Unit,
    onStartCooperative: () -> Unit,
    onCancel: () -> Unit
) {

    Button(
        onClick = {
            onStopUnCooperative()
        }
    ) {
        Text("Stop this uncooperative coroutine")
    }
    Row(
        verticalAlignment = CenterVertically
    ) {
        Text(
            fontSize = 20.sp,
            text = "job.",
            fontWeight = FontWeight.Bold
        )
        Button(
            onClick = {
                onCancel()
            }
        ) {
            Text("cancel()")
        }
    }
    Row(
        verticalAlignment = CenterVertically
    ) {
        Text(
            fontSize = 20.sp,
            text = "job.",
            fontWeight = FontWeight.Bold
        )
        Button(
            onClick = {
                onStartCooperative()
            }
        ) {
            Text("start()")
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
            fontSize = 16.sp,
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
                text = "by checking if coroutine isActive",
                fontSize = 16.sp,
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
                text = "by throwing a CancellationException",
                fontSize = 16.sp,
            )
        }
    }
}

@Composable
fun HighlightedCodeText(
    isCooperative: Boolean = false,
    checkIsActive: Boolean = false,
    throwCancellationException: Boolean = false
) {
    val annotatedString = buildAnnotatedString {
        // Keywords
        val keywordStyle =
            SpanStyle(color = Color.Blue, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        val regularStyle =
            SpanStyle(color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        val correctStyle =
            SpanStyle(color = Color.Green, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        val wrongStyle =
            SpanStyle(color = Color.Red, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        withStyle(keywordStyle) {
            append("val ")
        }
        withStyle(regularStyle) {
            append("job = GlobalScope.launch {\n")
        }

        withStyle(keywordStyle) {
            append("        while(")
        }

        withStyle(if (isCooperative) correctStyle else wrongStyle) {
            append(if (isCooperative) "false" else "true")
        }

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

    Text(text = annotatedString, lineHeight = 30.sp)
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
