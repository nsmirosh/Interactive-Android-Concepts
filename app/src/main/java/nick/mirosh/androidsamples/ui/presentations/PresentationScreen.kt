package nick.mirosh.androidsamples.ui.presentations

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.withContext


@Composable
fun PresentationScreen() {
//    Column {
//        StatefulCounter()
//        StatefulCounter2()
//    }
    Log.d("PresentationScreen", "PresentationScreen: recomposing")
    StatelessCounterRunner()
}

@Composable
fun FirstComposable() {
    val context = LocalContext.current
    Toast.makeText(
        context,
        "Hello",
        Toast.LENGTH_SHORT
    ).show()
}

@Composable
fun SecondComposable() {
    val context = LocalContext.current
    Toast.makeText(
        context,
        "Bye bye",
        Toast.LENGTH_SHORT
    ).show()
}


@Composable
fun ParallelExample() {
    Column {
        var followHappyPath by remember { mutableStateOf(false) }
        ComposableA {
            followHappyPath = true
        }
        ComposableB(followHappyPath = followHappyPath)
    }
}

@Composable
fun ComposableA(onSomeEvent: () -> Unit) {
    LaunchedEffect(Unit) {
        withContext(Dispatchers.Main) {
            onSomeEvent()
        }
    }
}

@Composable
fun ComposableB(followHappyPath: Boolean) {
    if (followHappyPath)
        Text("Something we want to happen")
    else
        throw Exception("Something we don't want to happen")
}


@Composable
fun StatefulCounter() {
    Column {
        var count by remember { mutableIntStateOf(0) }
        Button(onClick = { count++ }) {
            Text("Count is $count")
        }
    }
}

@Composable
fun StatefulCounter2() {
    Column {
        var count by remember { mutableIntStateOf(0) }
        Text("count is $count")
        Button(onClick = { count++ }) {
            Text("Add one")
        }
    }
}

@Composable
fun StatelessCounterRunner() {
    var count by remember { mutableIntStateOf(0) }
    StatelessCounter(count = count, onCountIncremented = { count++ })
}

@Composable
fun StatelessCounter(count: Int, onCountIncremented: () -> Unit) {
    Button(
        onClick = { onCountIncremented() }) {
        Text("Count is $count")
    }
}

class NoRippleInteractionSource : MutableInteractionSource {
    override val interactions: Flow<Interaction> = emptyFlow()
    override suspend fun emit(interaction: Interaction) {}
    override fun tryEmit(interaction: Interaction) = true
}

@Composable
fun Counter(count: Int, onCountChanged: (Int) -> Unit) {

    Column {
        Text("count is $count")
        Button(onClick = { onCountChanged(count + 1) }) {
            Text("Add one")
        }
    }
}

@Composable
fun ButtonThatDisappearsOnClick() {
    var showButton by remember { mutableStateOf(true) }
    if (showButton) {
        androidx.compose.material3.Button(onClick = {
            check(showButton)
            showButton = false
        }) {
            androidx.compose.material3.Text("My button")
        }
    }
    LimitedCounter()
}

@Composable
fun LimitedCounter() {
    var count by remember { mutableIntStateOf(3) }
    if (count < 5) {
        androidx.compose.material3.Button(onClick = {
            check(count < 5) { "Counter should not exceed the limit of 5" }
            count += 1
        }) {
            androidx.compose.material3.Text("Clicked $count times")
        }
    }
    else {
        androidx.compose.material3.Text("Counter limit reached")
    }
}
