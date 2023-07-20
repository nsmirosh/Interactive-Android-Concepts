package com.example.androidcomposeexample.ui.sideeffects.launchedeffect

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay
import nick.mirosh.androidsamples.R

const val MESSAGE_DELAY = 8000L
const val MESSAGE_INPUT_TAG = "message_input"
const val TIMER_UPDATE_TAG = "timer_update"
const val CHECKBOX_TAG = "checkbox"


/*
This screen is intended to demonstrate the usage of LaunchedEffect
and the way it interacts with rememberUpdatedState.

This screen has 3 states:
    1) entering an initial message
    2) a message that will update the initial message
    3) a timer that shows the progress and the way the messages are updated.

The example shows a toast that will show a message that we receive from a lambda from our ViewModel.
Initially the timer is set for 8 seconds and scheduled to show a message that we enter in the first
screen. After 3 seconds the initial lambda gets substituted for a new one with a new message that
was set in the 2nd screen. After the 2nd message is scheduled, LaunchedEffect gets launched along
with the timer. It has a "Unit" as a key therefore it will not update on recomposition. Hence
LaunchedEffect will NOT pick up a new lambda. However, if we “Use rememberUpdatedState” - the new
lambda update WILL be picked up. If we do use that option - then the user will see the update
correctly.

Codelab explaining the usage of LaunchedEffect and rememberUpdatesState - https://shorturl.at/jnrwM

 */
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LaunchedEffectScreen() {
    val viewModel = hiltViewModel<SideEffectsViewModel>()
    var shouldShowTimer by remember { mutableStateOf(false) }
    var useRememberUpdatedState by remember { mutableStateOf(false) }
    val message by viewModel.messageToDisplay.collectAsStateWithLifecycle()
    val timerUpdate by viewModel.timerValue.collectAsStateWithLifecycle()
    val progressMessage by viewModel.progressMessage.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {
        if (shouldShowTimer) {
            TimerUpdates(
                messageLambda = message,
                timerUpdate = timerUpdate,
                progressMessage = progressMessage,
                useRememberUpdatedState = useRememberUpdatedState
            )
        }
        else {
            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(16.dp)
            ) {
                var inSecondScreen by remember { mutableStateOf(false) }
                Input { message, isFirstMessageSet, isSecondMessageSet ->
                    inSecondScreen =
                        isFirstMessageSet && !isSecondMessageSet
                    if (inSecondScreen) {
                        viewModel.scheduleMessage(message)
                    }
                    else if (isSecondMessageSet) {
                        viewModel.scheduleUpdate(
                            message
                        )
                    }
                    shouldShowTimer = isSecondMessageSet
                }

                if (inSecondScreen) {
                    Row {
                        Text(
                            text = stringResource(id = R.string.use_remember_updated_state),
                            modifier = Modifier
                                .padding(8.dp)
                                .align(CenterVertically)

                        )
                        Checkbox(
                            checked = useRememberUpdatedState,
                            modifier = Modifier
                                .align(CenterVertically)
                                .testTag(CHECKBOX_TAG),
                            onCheckedChange = {
                                useRememberUpdatedState = it
                            }
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun TimerUpdates(
    messageLambda: (() -> String)?,
    timerUpdate: String,
    progressMessage: String,
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
                messageLambda
            )
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
    }
    LaunchedEffect(Unit) {
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
            text = stringResource(
                if (isFirstMessageSet) {
                    R.string.launched_effect_update_description
                }
                else {
                    R.string.launched_effect_initial_description
                }
            ),
            modifier = Modifier
                .padding(16.dp)
                .align(CenterHorizontally)
        )
        TextField(
            modifier = Modifier
                .padding(16.dp)
                .align(CenterHorizontally)
                .testTag(MESSAGE_INPUT_TAG),
            label = { Text(text = stringResource(id = R.string.enter_message)) },
            value = message,
            onValueChange = {
                message = it
            }
        )
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
            }
        ) {
            Text(
                text = stringResource(id = R.string.schedule),
                fontSize = 16.sp
            )
        }
    }
}
