package nick.mirosh.androidsamples.ui.side_effects

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay

@Composable
fun SideEffectsScreen() {
    MainLayout()
}

@Composable
fun Timer(value: Int) {
    val deciSeconds = value % 10
    val seconds = value / 10
    Text(
        "$seconds,$deciSeconds",
        style = MaterialTheme.typography.labelLarge
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainLayout() {
    Scaffold {
        val viewModel = hiltViewModel<SideEffectsViewModel>()
        val result by viewModel.initialTimer.collectAsStateWithLifecycle()
        var initialMessageSet by remember { mutableStateOf(false) }
        if (!initialMessageSet) {
            Content { message, time ->
                viewModel.scheduleMessage(message, time)
                initialMessageSet = true
            }
        } else {

            Content { message, time ->
                viewModel.scheduleUpdate(message, time)
                initialMessageSet = true
            }
        }
    }
}

@Composable
fun Content(onMessageScheduled: (String, Int) -> Unit) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        val (title, up, down, text, startTimer, messageId) = createRefs()
        var message by remember { mutableStateOf("") }
        var timeSet by remember { mutableIntStateOf(0) }
        Text(
            "Schedule an initial message to send",
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
        )
        TextField(modifier = Modifier
            .constrainAs(messageId) {
                top.linkTo(title.bottom)
                start.linkTo(parent.start)
            },
            value = message,
            onValueChange = {
                message = it
            }, label = {}
        )

        val deciSeconds = timeSet % 10
        val seconds = timeSet / 10
        Text(
            "$seconds,$deciSeconds",
            fontSize = 32.sp,
            modifier = Modifier
                .constrainAs(text) {
                    top.linkTo(messageId.bottom)
                    start.linkTo(parent.start)
                }
                .padding(16.dp)
        )
        Button(onClick = {
            timeSet += 10
        },
            modifier = Modifier.constrainAs(up) {
                top.linkTo(text.top)
                start.linkTo(text.end)
            }
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowUp,
                contentDescription = "Navigation Icon"
            )
        }
        Button(onClick = {
            timeSet -= 10
        },
            modifier = Modifier.constrainAs(down) {
                top.linkTo(up.bottom)
                start.linkTo(text.end)
            }
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "Navigation Icon"
            )
        }
        Button(onClick = {
            onMessageScheduled(message, timeSet)
        },
            modifier = Modifier.constrainAs(startTimer) {
                top.linkTo(down.bottom)
                start.linkTo(text.start)
            }
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
