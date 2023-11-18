package nick.mirosh.androidsamples.ui.coroutines.async

import android.graphics.Color.parseColor
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
import androidx.compose.material.LinearProgressIndicator
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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

                var useSupervisorScope by remember {
                    mutableStateOf(false)
                }

                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        colors = if (asyncsLaunched) ButtonDefaults.buttonColors(
                            backgroundColor = Color(parseColor("#00ab41")),
                            contentColor = Color.White
                        )
                        else ButtonDefaults.buttonColors(),
                        onClick = {
                            if (useSupervisorScope)
                                viewModel.launchAsyncsWithSupervisor()
//                            else viewModel.launchAsyncs()
                            else viewModel.runMyChallenge()
                            asyncsLaunched = true
                        }
                    ) {
                        Text(if (asyncsLaunched) "Asyncs Launched!" else "Launch asyncs")
                    }
                    Checkbox(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        checked = useSupervisorScope, onCheckedChange = {
                            useSupervisorScope = it
                        })
                    Text(
                        text = "Use supervisorScope"
                    )
                }

                ProgressBarWithCancel(
                    progress = deferred1Updates,
                    label = "Async{} #1",
                    onCancelClick = {
                        viewModel.cancelAsync1()
                    }
                )
                ProgressBarWithCancel(
                    progress = deferred2Updates,
                    label = "Async{} #2",
                    onCancelClick = {
                        viewModel.cancelAsync2()
                    }
                )
//                AsyncContent(
//                    progressAsync1 = deferred1Updates,
//                    progressAsync2 = deferred2Updates,
//                    launchAsyncs = {
//                        viewModel.launchAsyncs()
//                    },
//                    launchAsyncsWithSupervisor = {
//                        viewModel.launchAsyncsWithSupervisor()
//                    },
//                    cancelAsync1 = {
//                        viewModel.cancelAsync1()
//                    },
//                    cancelAsync2 = {
//                        viewModel.cancelAsync2()
//                    }
//                )
                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    colors = if (coroutinesLaunched) ButtonDefaults.buttonColors(
                        backgroundColor = Color(parseColor("#00ab41")),
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
                    progress = job1FlowUpdates,
                    label = "Coroutine 1",
                    onCancelClick = {
                        viewModel.cancelCoroutine1()
                    }
                )
                ProgressBarWithCancel(
                    progress = job2FlowUpdates,
                    label = "Coroutine 2",
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
fun AsyncContent(
    progressAsync1: Float,
    progressAsync2: Float,
    launchAsyncs: () -> Unit,
    launchAsyncsWithSupervisor: () -> Unit,
    cancelAsync1: () -> Unit,
    cancelAsync2: () -> Unit,
) {
    Column {
        var asyncsLaunched by remember {
            mutableStateOf(false)
        }

        var useSupervisorScope by remember {
            mutableStateOf(false)
        }

        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                colors = if (asyncsLaunched) ButtonDefaults.buttonColors(
                    backgroundColor = Color(parseColor("#00ab41")),
                    contentColor = Color.White
                )
                else ButtonDefaults.buttonColors(),
                onClick = {
                    if (useSupervisorScope)
                        launchAsyncsWithSupervisor()
                    else launchAsyncs()
                    asyncsLaunched = true
                }
            ) {
                Text(if (asyncsLaunched) "Asyncs Launched!" else "Launch asyncs")
            }
            Checkbox(
                modifier = Modifier.padding(horizontal = 8.dp),
                checked = useSupervisorScope, onCheckedChange = {
                    useSupervisorScope = it
                })
            Text(
                text = "Use supervisorScope"
            )
        }

        ProgressBarWithCancel(
            progress = progressAsync1,
            label = "Async{} #1",
            onCancelClick = {
                cancelAsync1()
            }
        )
        ProgressBarWithCancel(
            progress = progressAsync2,
            label = "Async{} #2",
            onCancelClick = {
                cancelAsync2()
            }
        )
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


@Composable
fun ProgressBar(
    modifier: Modifier = Modifier,
    progress: Float,
) {

    val progressAnimDuration = 300
    val progressAnimation by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(
            durationMillis = progressAnimDuration,
            easing = FastOutSlowInEasing
        ),
        label = ""
    )
    LinearProgressIndicator(
        progress = progressAnimation,
        modifier = modifier
            .padding(horizontal = 16.dp)
            .height(32.dp)
            .clip(RoundedCornerShape(16.dp))
    )
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

