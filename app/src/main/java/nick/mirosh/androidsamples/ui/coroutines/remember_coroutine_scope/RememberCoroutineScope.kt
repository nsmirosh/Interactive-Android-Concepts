package nick.mirosh.androidsamples.ui.coroutines.remember_coroutine_scope


import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import nick.mirosh.androidsamples.ui.dialog.WebViewDialog


@Composable
fun CancelledIcon(
    modifier: Modifier = Modifier,
    color: Color = Color.Red,
    contentDescription: String? = null
) {
    Icon(
        imageVector = Icons.Default.Close,
        contentDescription = contentDescription,
        modifier = modifier.size(48.dp),
        tint = color
    )
}

@Composable
fun SuccessIcon(
    modifier: Modifier = Modifier,
    color: Color = Color.Green,
    contentDescription: String? = null
) {
    Icon(
        imageVector = Icons.Default.Check,
        contentDescription = contentDescription,
        modifier = modifier.size(48.dp),
        tint = color
    )
}

@Composable
fun RememberCoroutineScopeScreen(modifier: Modifier = Modifier) {

    var onCancelPressed by remember {
        mutableStateOf(false)
    }
    var success by remember {
        mutableStateOf(false)
    }

    var showDialog by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        Column {
            if (success) {
                SuccessState(
                    onTryAgainClicked = {
                        success = false
                        onCancelPressed = false
                    }
                )
            } else if (onCancelPressed) {
                CancelledState(onTryAgainClicked = {
                    success = false
                    onCancelPressed = false
                })
            } else {
                InitialState(
                    onCancelPressed = {
                        onCancelPressed = !onCancelPressed
                    },
                    onFinished = {
                        success = true
                    }
                )
            }
            Button(
                onClick = { showDialog = true },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Explanation")
            }
        }
        if (showDialog) {
            WebViewDialog(
                url = "https://www.nickmirosh.com/post/are-you-using-coroutines-inside-your-composables-make-sure-to-use-remembercoroutinescope",
                onDismissRequest = {
                    showDialog = false
                }
            )
        }
    }
}


@Composable
fun InitialState(
    onCancelPressed: () -> Unit,
    onFinished: () -> Unit,
) {

    var rememberCoroutineScope by remember { mutableStateOf(false) }

    Column {
        if (rememberCoroutineScope) {
            RememberCoroutineScopeComposable(
                onCancelPressed = {
                    onCancelPressed()
                },
                onFinished = {
                    onFinished()
                }
            )
        } else {
            RegularCoroutineScopeComposable(
                onCancelPressed = {
                    onCancelPressed()
                },
                onFinished = {
                    onFinished()
                }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = rememberCoroutineScope, onCheckedChange = {
                rememberCoroutineScope = !rememberCoroutineScope
            })
            Text(
                fontSize = 16.sp,
                text = "use rememberCoroutineScope"
            )
        }
    }
}

@Composable
fun CancelledState(
    onTryAgainClicked: () -> Unit,
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CancelledIcon()
            Text(
                fontSize = 24.sp,
                text = "Cancelled!"
            )
        }
        Button(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            onClick = {
                onTryAgainClicked()
            }
        ) {
            Text(
                text = "Try again"
            )
        }
    }
}

@Composable
fun SuccessState(
    onTryAgainClicked: () -> Unit,
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            SuccessIcon()
            Text(
                fontSize = 24.sp,
                text = "Success!"
            )
        }

        Button(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            onClick = {
                onTryAgainClicked()
            }
        ) {
            Text(
                text = "Try again"
            )
        }
    }
}


@Composable
fun RememberCoroutineScopeComposable(
    onCancelPressed: () -> Unit,
    onFinished: () -> Unit = {}
) {
    val rememberCoroutineScope = rememberCoroutineScope()
    var progress by remember { mutableIntStateOf(0) }
    Column {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    rememberCoroutineScope.launch {
                        runCounter {
                            progress = it
                        }
                        onFinished()
                    }
                }) {
                Text(
                    text = "Start"
                )
            }
            Button(
                modifier = Modifier.padding(start = 16.dp),
                onClick = {
                    onCancelPressed()
                }) {
                Text(
                    text = "Cancel"
                )
            }
        }
        ProgressWithCancel(progress = progress)
    }
}

@Composable
fun RegularCoroutineScopeComposable(
    onCancelPressed: () -> Unit,
    onFinished: () -> Unit = {}
) {
    var progress by remember { mutableIntStateOf(0) }
    Column {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                modifier = Modifier.padding(start = 16.dp),
                onClick = {
                    CoroutineScope(Dispatchers.Main).launch {
                        runCounter {
                            progress = it
                        }
                        onFinished()
                    }

                }) {
                Text(
                    text = "Start"
                )
            }
            Button(

                modifier = Modifier.padding(start = 16.dp),
                onClick = {
                    onCancelPressed()
                }) {
                Text(
                    text = "Cancel"
                )
            }
        }
        ProgressWithCancel(progress = progress)
    }
}

suspend fun runCounter(
    onCounterUpdate: (Int) -> Unit
) {
    var counter = 0
    while (counter < 100) {
        delay(500)
        counter += 10
        onCounterUpdate(counter)
    }
}

@Composable
fun ProgressWithCancel(progress: Int) {
    val progressAnimDuration = 300
    val progressAnimation by animateFloatAsState(
        targetValue = progress.toFloat() / 100f,
        animationSpec = tween(
            durationMillis = progressAnimDuration,
            easing = FastOutSlowInEasing
        ),
        label = ""
    )
    LinearProgressIndicator(
        progress = { progressAnimation },
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(32.dp)
            .clip(RoundedCornerShape(16.dp)),
    )
}

@Preview(showBackground = true)
@Composable
fun RememberCoroutineScopeScreenPreview() {
    RememberCoroutineScopeScreen()
}

@Preview(showBackground = true)
@Composable
fun CancelledStatePreview() {
    CancelledState { }
}
