package nick.mirosh.androidsamples.ui.coroutines.async

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nick.mirosh.androidsamples.ui.coroutines.ProgressBar
import nick.mirosh.androidsamples.ui.coroutines.myGreen
import nick.mirosh.androidsamples.ui.theme.MyApplicationTheme


@Composable
fun AsyncComparisonScreen(
    viewModel: AsyncComparisonViewModel = hiltViewModel()
) {
    val deferred1Updates by viewModel.deferred1Flow.collectAsStateWithLifecycle()
    val deferred2Updates by viewModel.deferred2Flow.collectAsStateWithLifecycle()
    val job1FlowUpdates by viewModel.job1flow.collectAsStateWithLifecycle()
    val job2FlowUpdates by viewModel.job2flow.collectAsStateWithLifecycle()
    var restart by remember {
        mutableStateOf(false)
    }
    MaterialTheme {
        key(restart) {
            Column {
                var asyncsLaunched by remember {
                    mutableStateOf(false)
                }

                var coroutinesLaunched by remember {
                    mutableStateOf(false)
                }
                Button(
                    colors = if (asyncsLaunched) ButtonDefaults.buttonColors(
                        backgroundColor = myGreen(),
                        contentColor = Color.White
                    )
                    else ButtonDefaults.buttonColors(),
                    modifier = Modifier.padding(horizontal = 16.dp),
                    onClick = {
                        viewModel.launchAsyncs()
                        asyncsLaunched = true
                    }
                ) {
                    Text(if (asyncsLaunched) "Asyncs Launched!" else "Launch asyncs")
                }
                Button(
                    colors = if (coroutinesLaunched) ButtonDefaults.buttonColors(
                        backgroundColor = myGreen(),
                        contentColor = Color.White
                    )
                    else ButtonDefaults.buttonColors(),
                    modifier = Modifier.padding(horizontal = 16.dp),
                    onClick = {
                        viewModel.launchCoroutines()
                        coroutinesLaunched = true
                    }
                ) {
                    Text(if (coroutinesLaunched) "Coroutines Launched!" else "Launch Coroutines")
                }

                ProgressBarWithCancel(
                    progress = deferred1Updates,
                    label = "Async #1",
                    onCancelClick = {
                        viewModel.cancelAsync1()
                    }
                )
                Column {

                }
                ProgressBarWithCancel(
                    progress = deferred2Updates,
                    label = "Async #2",
                    onCancelClick = {
                        viewModel.cancelAsync2()
                    }
                )
                ProgressBarWithCancel(
                    progress = job1FlowUpdates,
                    label = "Launch #1",
                    onCancelClick = {
                        viewModel.cancelCoroutine1()
                    }
                )
                ProgressBarWithCancel(
                    progress = job2FlowUpdates,
                    label = "Launch #2",
                    onCancelClick = {
                        viewModel.cancelCoroutine2()
                    }
                )
                Button(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 16.dp),
                    onClick = {
                        viewModel.clear()
                        restart = !restart
                    }) {
                    Text("Restart")
                }
            }
        }
    }
}


@Composable
fun ProgressBarWithCancel(
    modifier: Modifier = Modifier,
    progress: Float,
    label: String,
    onCancelClick: () -> Unit,
) {

    var cancelled by remember {
        mutableStateOf(false)
    }
    Column {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            text = label,
            fontSize = 20.sp
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProgressBar(
                modifier = modifier
                    .weight(2f / 3f),
                progress = progress
            )

            Button(
                colors = if (cancelled) ButtonDefaults.buttonColors(
                    backgroundColor = Color.Red,
                    contentColor = Color.White
                )
                else ButtonDefaults.buttonColors(),
                modifier = Modifier
                    .weight(1f / 3f)
                    .padding(horizontal = 16.dp),
                onClick = {
                    cancelled = true
                    onCancelClick()
                }) {
                Text(if (cancelled) "Cancelled" else "Cancel")
            }
        }
    }
}


@Preview
@Composable
fun AsyncComparisonScreenPreview() {
    MyApplicationTheme {
        AsyncComparisonScreen()
    }
}

@Preview
@Composable
fun ProgressBarWithCancelPreview() {
    MyApplicationTheme {
        ProgressBarWithCancel(
            modifier = Modifier.padding(16.dp),
            progress = 0.5f,
            label = "Deferred 1",
            onCancelClick = {}
        )
    }
}

