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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay

const val MESSAGE_DELAY = 8000L
const val MESSAGE_INPUT_TAG = "message_input"
const val TIMER_UPDATE_TAG = "timer_update"
const val CHECKBOX_TAG = "checkbox"
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
            if (shouldShowTimer)
                TimerUpdates(viewModel, useRememberUpdatedState)
            else
                Column(
                    modifier = Modifier
                        .align(androidx.compose.ui.Alignment.TopCenter)
                        .padding(16.dp)
                ) {
                    var inSecondScreen by remember { mutableStateOf(false) }
                    Input { message, isFirstMessageSet, isSecondMessageSet ->
                        Log.d(
                            "SideEffectsScreen",
                            "MainLayout() called with: message = $message, isFirstMessageSet = $isFirstMessageSet, isSecondMessageSet = $isSecondMessageSet"
                        )
                        inSecondScreen =
                            isFirstMessageSet && !isSecondMessageSet
                        if (inSecondScreen) viewModel.scheduleMessage(message)
                        else if (isSecondMessageSet) viewModel.scheduleUpdate(
                            message
                        )
                        shouldShowTimer = isSecondMessageSet
                    }

                    if (inSecondScreen) {
                        Row {
                            Text(
                                text = "Use rememberUpdatedState ",
                                modifier = Modifier
                                    .padding(8.dp)
                                    .align(CenterVertically)

                            )
                            Checkbox(checked = useRememberUpdatedState,
                                modifier = Modifier.align(CenterVertically).testTag(CHECKBOX_TAG),
                                onCheckedChange = {
                                    useRememberUpdatedState = it
                                })
                        }
                    }
                }
        }
    }

}

@Composable
fun TimerUpdates(
    viewModel: SideEffectsViewModel,
    useRememberUpdatedState: Boolean
) {

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(
                    Alignment.TopCenter
                )
                .padding(16.dp)
        ) {
            val message by viewModel.messageToDisplay.collectAsStateWithLifecycle()
            val timerUpdate by viewModel.timerValue.collectAsStateWithLifecycle()
            val progressMessage by viewModel.progressMessage.collectAsStateWithLifecycle()
            Text(
                timerUpdate,
                fontSize = 48.sp,
                modifier = Modifier
                    .padding(16.dp)
                    .testTag(TIMER_UPDATE_TAG)
            )
            Text(
                progressMessage,
                fontSize = 24.sp,
                modifier = Modifier.padding(16.dp)
            )
            ShowToast(
                useRememberUpdatedState = useRememberUpdatedState,
                message
            )
        }
    }
}

@Composable
fun ShowToast(
    useRememberUpdatedState: Boolean, message: (() -> String)? = null
) {
    val context = LocalContext.current
    var actualTrueMessage: State<(() -> String)?>? = null
    if (useRememberUpdatedState) {
        actualTrueMessage = rememberUpdatedState(message)
        Log.d(
            "SideEffectsScreen",
            "actualTrueMessage = $actualTrueMessage"
        )
    }
    LaunchedEffect(Unit) {
        Log.d("SideEffectsScreen", " Entered LaunchedEffect")
        delay(MESSAGE_DELAY)
        Toast.makeText(
            context,
            actualTrueMessage?.value?.invoke() ?: message?.invoke(),
            Toast.LENGTH_SHORT
        ).show()
    }
}


@Composable
fun Input(
    onMessageScheduled: (String, Boolean, Boolean) -> Unit
) {
    Column {
        var isFirstMessageSet by remember { mutableStateOf(false) }
        var message by remember { mutableStateOf("") }
        Text(
            text = if (isFirstMessageSet) "Schedule an update to the message after 5 seconds"
            else "Schedule message to be sent in 8 seconds",
            modifier = Modifier
                .padding(16.dp)
                .align(CenterHorizontally)
        )
        TextField(modifier = Modifier
            .padding(16.dp)
            .align(CenterHorizontally)
            .testTag(MESSAGE_INPUT_TAG),
            value = message,
            onValueChange = {
                message = it
            },
            label = {})
        Button(
            modifier = Modifier
                .align(CenterHorizontally)
                .padding(16.dp),
            onClick = {
                if (isFirstMessageSet) {
                    onMessageScheduled(message, true, true)
                }
                else {
                    onMessageScheduled(message, true, false)
                    isFirstMessageSet = true
                }
                message = ""
            },
        ) {
            Text(text = "Schedule message", fontSize = 24.sp)
        }
    }
}

@Preview
@Composable
fun MainLayoutPreview() {
    MainLayout()
}