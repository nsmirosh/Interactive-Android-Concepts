package nick.mirosh.androidsamples.ui.side_effects

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.TextField
import androidx.compose.material3.Button
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

@Composable
fun SideEffectsScreen() {
    MainLayout()
}


// [ ] Add an explanation for the timer
// [ ] Add seconds for the timer
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainLayout() {
    Scaffold {
        val viewModel = hiltViewModel<SideEffectsViewModel>()
        val timerUpdate by viewModel.timerValue.collectAsStateWithLifecycle()
        val message by viewModel.messageToDisplay.collectAsStateWithLifecycle()
        var shouldShowTimer by remember { mutableStateOf(false) }

        if (shouldShowTimer) {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .align(
                            androidx.compose.ui.Alignment.Center
                        )
                ) {
                    Text(
                        timerUpdate,
                        fontSize = 48.sp,
                        modifier = Modifier
                            .padding(16.dp)
                    )
                    val context = LocalContext.current
                    val check = true
                    var actualTrueMessage: State<(() -> String)?>? = null
                    if (check) {
                        actualTrueMessage =  rememberUpdatedState(message)
                    }
                    LaunchedEffect(Unit) {
                        delay(8000)
                        Toast.makeText(
                            context,
                            actualTrueMessage?.value?.invoke(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        } else {
            Column {
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
                    Button(
                        onClick = {
                            shouldShowTimer = true
                        },
                    ) {
                        Text(text = "Schedule without rememberUpdatedState")
                    }
                }
            }
        }
        // [ ] Write a message "emitted initial lambda with message .... "
        // [ ] Write a message "emitted an update lambda with message .... "
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
            text = if (isFirstMessageSet) "Schedule an update to the message after 3 secs" else "Schedule message to be sent in 5 secs",
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
            Text(text = if (isFirstMessageSet) "Schedule with rememberUpdatedState" else "Schedule message")
        }
    }
}

@Preview
@Composable
fun MainLayoutPreview() {
    MainLayout()
}

@Composable
fun SideEffectsNew(
    onTimeout: () -> Unit
) {
    val currentOnTimeout by rememberUpdatedState(onTimeout)
    LaunchedEffect(Unit)
    {
        delay(
            500
        )
        currentOnTimeout()
    }
}


@Composable
fun SideEffectsIncorrect(
    onTimeout: () -> Unit
) {

    LaunchedEffect(Unit)
    {
        delay(
            500
        )
        onTimeout()
    }
}
