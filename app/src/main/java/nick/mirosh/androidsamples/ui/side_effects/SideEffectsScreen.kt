package nick.mirosh.androidsamples.ui.side_effects

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.TextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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
        val timerUpdate by viewModel.initialTimer.collectAsStateWithLifecycle()
        var shouldShowSecondButton by remember { mutableStateOf(false) }
        var isSecondMessageSet by remember { mutableStateOf(false) }
        Column {
            Content { message, isFirstMessageSet ->
                shouldShowSecondButton = isFirstMessageSet
                if (isFirstMessageSet)
                    viewModel.scheduleMessage(message)
                else
                    viewModel.scheduleUpdate(message)
            }

            if (shouldShowSecondButton) {
                Button(
                    onClick = {
                        isSecondMessageSet = true
                    },
                ) {
                    Text(text = "Schedule without rememberUpdatedState")
                }
            }
        }

        if (isSecondMessageSet) {
            val deciSeconds = timerUpdate % 10
            val seconds = timerUpdate / 10
            Text(
                " $seconds,${deciSeconds}s",
                fontSize = 32.sp,
                modifier = Modifier
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun Content(
    onMessageScheduled: (String, Boolean) -> Unit
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
                onMessageScheduled(message, true)
                isFirstMessageSet = true
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
