package nick.mirosh.androidsamples.ui.side_effects

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.TextField
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay

const val messageDelay = 8000L

@Composable
fun SideEffectsScreen() {
    MainLayout()
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainLayout() {
    Scaffold {
        val viewModel = hiltViewModel<SideEffectsViewModel>()
        var shouldShowTimer by remember { mutableStateOf(false) }
        var useRememberUpdatedState by remember { mutableStateOf(false) }
        Box(modifier = Modifier.fillMaxSize()) {
            if (shouldShowTimer) {
                Column(
                    modifier = Modifier
                        .align(
                            androidx.compose.ui.Alignment.TopCenter
                        )
                        .padding(16.dp)
                ) {
                    val message by viewModel.messageToDisplay.collectAsStateWithLifecycle()
                    val timerUpdate by viewModel.timerValue.collectAsStateWithLifecycle()
                    Text(
                        timerUpdate,
                        fontSize = 48.sp,
                        modifier = Modifier
                            .padding(16.dp)
                    )
                    ShowToast(
                        useRememberUpdatedState = useRememberUpdatedState,
                        message
                    )
                }
            } else {
                Column(
                    modifier = Modifier
                        .align(androidx.compose.ui.Alignment.TopCenter)
                        .padding(16.dp)
                ) {
                    var secondPhase by remember { mutableStateOf(false) }
                    Content { message, isFirstMessageSet, isSecondMessageSet ->
                        Log.d(
                            "SideEffectsScreen",
                            "MainLayout() called with: message = $message, isFirstMessageSet = $isFirstMessageSet, isSecondMessageSet = $isSecondMessageSet"
                        )
                        secondPhase =
                            isFirstMessageSet && !isSecondMessageSet
                        if (secondPhase)
                            viewModel.scheduleMessage(message)
                        else if (isSecondMessageSet)
                            viewModel.scheduleUpdate(message)
                        shouldShowTimer = isSecondMessageSet
                    }

                    if (secondPhase) {
                        Row {
                            Text(
                                text = "Use rememberUpdatedState: ",
                                modifier = Modifier.padding(16.dp)
                            )
                            Checkbox(
                                checked = useRememberUpdatedState,
                                onCheckedChange = {
                                    useRememberUpdatedState = it
                                })
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ShowToast(
    useRememberUpdatedState: Boolean,
    message: (() -> String)? = null
) {

    val context = LocalContext.current
    var actualTrueMessage: State<(() -> String)?>? = null
    if (useRememberUpdatedState) {
        actualTrueMessage = rememberUpdatedState(message)
        Log.d(
            "SideEffectsScreen",
            "MainLayout() called actualTrueMessage = $actualTrueMessage"
        )
    }
    LaunchedEffect(Unit) {
        Log.d("SideEffectsScreen", " Entered LaunchedEffect")
        delay(messageDelay)
        Toast.makeText(
            context,
            actualTrueMessage?.value?.invoke()
                ?: message?.invoke(),
            Toast.LENGTH_SHORT
        ).show()
    }
}

@Composable
fun Content(
    onMessageScheduled: (String, Boolean, Boolean) -> Unit
) {
    Column {
        var isFirstMessageSet by remember { mutableStateOf(false) }
        var message by remember { mutableStateOf("") }
        Text(
            text = if (isFirstMessageSet) "Schedule an update to the message after 5 secs" else "Schedule message to be sent in 8 secs",
            modifier = Modifier
        )
        TextField(modifier = Modifier,
            value = message,
            onValueChange = {
                message = it
            }, label = {}
        )
        Button(
            onClick = {
                if (isFirstMessageSet) {
                    onMessageScheduled(message, true, true)
                } else {
                    onMessageScheduled(message, true, false)
                    isFirstMessageSet = true
                }
                message = ""
            },
        ) {
            Text(text = "Schedule message")
        }
    }
}

@Preview
@Composable
fun MainLayoutPreview() {
    MainLayout()
}