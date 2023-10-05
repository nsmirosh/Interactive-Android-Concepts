package nick.mirosh.androidsamples.ui

import android.util.Log
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch


val logTag = "Coroutines"


enum class UIState {
    REGULAR, REMEMBER, CANCEL
}

@Composable
fun Coroutines(
) {

    var onCancelPressed by remember {
        mutableStateOf(false)
    }


    var rememberCoroutineScope by remember { mutableStateOf(false) }


    Column {

        if (rememberCoroutineScope) {
            RememberCoroutineScopeComposable(
                onCancelPressed = {
                    onCancelPressed = !onCancelPressed
                }
            )
        }
        else {
            RegularCoroutineScopeComposable() {

            }
        }

        Row {
            Checkbox(checked = rememberCoroutineScope, onCheckedChange = {
                rememberCoroutineScope = !rememberCoroutineScope
            })
            Text(
                text = "with remember coroutine scope"
            )
        }
    }

}

@Composable
fun RememberCoroutineScopeComposable(
    onCancelPressed: () -> Unit,
) {
    val rememberCoroutineScope = rememberCoroutineScope()
    var progress by remember { mutableIntStateOf(0) }
    Column {
        Row {
            Button(onClick = {
                rememberCoroutineScope.launch {
                    Log.d(logTag, "RememberCoroutineScopeComposable: coroutine started")
                    runCounter {
                        progress = it
                    }
                    Log.d(logTag, "RememberCoroutineScopeComposable: coroutine finished")
                }
            }) {
                Text(
                    text = "Start"
                )
            }

            Button(onClick = {
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
) {

    var progress by remember { mutableIntStateOf(0) }
    Column {
        Row {
            Button(onClick = {
                CoroutineScope(Dispatchers.Main).launch  {
                    runCounter {
                        progress = it
                    }
                }

            }) {
                Text(
                    text = "Start"
                )
            }

            Button(onClick = {
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
fun RememberCoroutineScopeComposable() {
    val scope = rememberCoroutineScope()
    Button(onClick = {
        CoroutineScope(Dispatchers.Main).launch {
            Log.d(logTag, "RememberCoroutineScopeComposable: coroutine started")
            delay(5000)
            Log.d(logTag, "RememberCoroutineScopeComposable: coroutine finished")

        }
//        scope.launch {
//            //This scope should cancel when the composition changes
//            //Because rememberCoroutineScope() only lives as long as
//            //the composable inside which it is called lives
//
//            Log.d(logTag, "RememberCoroutineScopeComposable: coroutine started")
//            delay(5000)
//            Log.d(logTag, "RememberCoroutineScopeComposable: coroutine finished")
//
//        }
    }) {
        Text(
            text = "Click me"
        )
    }

}


suspend fun runCounter(
    onCounterUpdate: (Int) -> Unit
) {
    var counter = 0
    while (counter < 100) {
        counter += 10

        delay(500)
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
        progress = progressAnimation,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(32.dp)
            .clip(RoundedCornerShape(16.dp))
    )
}

@Composable
fun LaunchedEffectCoroutines() {
    LaunchedEffect(Unit) {
        flowOf(1, 2, 3)
            .onCompletion { Log.d(logTag, "Coroutines: onCompletion ") }
            .collect { Log.d(logTag, "collect: $it") }

    }
}
