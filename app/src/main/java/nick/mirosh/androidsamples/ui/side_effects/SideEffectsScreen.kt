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
        Content()
    }
}

@Composable
fun Content() {
    val viewModel = hiltViewModel<SideEffectsViewModel>()
    val result by viewModel.updatedValue.collectAsStateWithLifecycle()
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        val (title, up, down, text, startTimer, messageId) = createRefs()
        Text(
            "Schedule an initial message to send",
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
        )
        var message by remember { mutableStateOf("") }
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

        val deciSeconds = result % 10
        val seconds = result / 10
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
            viewModel.increaseTimer()
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
            viewModel.decreaseTimer()
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
            viewModel.startTimer()
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
