package nick.mirosh.androidsamples.ui.presentations

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.withContext
import kotlin.random.Random


@Composable
fun PresentationScreen() {
    CounterWithColumn()
}


@Composable
fun CounterComposableRunner() {
    Column {
        Counter()
    }
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
fun FunctionThatRunsFrequently() {
    functionThatDoesSomeHeavyLifting()
    Text("Something")
}

fun functionThatDoesSomeHeavyLifting() {
    //Something that takes a long time
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


//@Composable
//fun StatefulCounter2() {
//    LogCompositions(msg = "StatefulCounter WHOLE SCOPE")
//    Column {
//        LogCompositions(msg = "StatefulCounter ColumnScope")
//        var count by mutableIntStateOf(0)
//        Button(onClick = { count++ }) {
//            LogCompositions(msg = "StatefulCounter ButtonScope")
//            Text("Count is $count")
//        }
//    }
//}

//@Composable
//fun CounterWithColumn() {
//    LogCompositions(msg = "CounterWithColumn WHOLE SCOPE")
//    Column {
//        LogCompositions(msg = "CounterWithColumn ColumnScope")
//        var count by remember { mutableIntStateOf(0) }
//        Text("count is $count")
//        Button(onClick = { count++ }) {
//            LogCompositions(msg = "CounterWithColumn ButtonScope")
//            Text("Add one")
//        }
//    }
//}
//
//
//
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
fun CounterWithColumn() {
    LogCompositions(msg = "CounterWithColumn whole scope")
    Column {
        LogCompositions(msg = "CounterWithColumn ColumnScope")
        var count by remember { mutableIntStateOf(0) }
        Text("count is $count")
        Button(onClick = { count++ }) {
            LogCompositions(msg = "CounterWithColumn ButtonScope")
            Text("Add one")
        }
    }
}

@Composable
fun Counter() {
    LogCompositions(msg = "Counter whole scope")
    var count by remember { mutableIntStateOf(0) }
    Text("count is $count")
    Button(onClick = { count++ }) {
        LogCompositions(msg = "Counter ButtonScope")
        Text("Add one")
    }
}


//@Composable
//fun CounterWithColumn() {
//    Column {
//        var count by remember { mutableIntStateOf(0) }
//        Text("count is $count")
//        Button(onClick = { count++ }) {
//            Text("Add one")
//        }
//    }
//}
//
//@Composable
//fun Counter() {
//    var count by remember { mutableIntStateOf(0) }
//    Text("count is $count")
//    Button(onClick = { count++ }) {
//        Text("Add one")
//    }
//}


@Composable
fun StatelessCounterRunner() {
    var count by remember { mutableIntStateOf(0) }
    StatelessCounter(count = count, onCountIncremented = { count++ })
}

@Composable
fun StatelessCounter(count: Int, onCountIncremented: () -> Unit) {
    Button(
        interactionSource = NoRippleInteractionSource(),
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
fun LimitedCounter() {
    var count by remember { mutableIntStateOf(4) }
    if (count < 5)
        Button(onClick = {
            if (count == 5) throw Exception("Counter should not exceed the limit of 5")
            count += 1
        }) {
            Text("Clicked $count times")
        }
    else
        Text("Counter limit reached")
}


@Composable
fun OuterClickCounter() {
    LogCompositions(msg = "OuterClickCounter base scope")
    Column {
        var outerClicks by remember { mutableIntStateOf(0) }
        Button(
            onClick = { outerClicks++ },
            interactionSource = NoRippleInteractionSource()
        ) {
            Text("Outer click trigger")
        }
        InnerClickCounter(outerClicks)
    }
}

@Composable
fun InnerClickCounter(outerClicks: Int) {
    LogCompositions(msg = "InnerClickCounter base scope")
    var innerClicks by remember { mutableIntStateOf(0) }
    Column {
        Button(
            onClick = { innerClicks++ },
            interactionSource = NoRippleInteractionSource()
        ) {
            Text("Inner clicks  = $innerClicks")
        }
        Text("Outer clicks = $outerClicks")
    }
}


//////////////////=========================================================================================================


class Ref(var value: Int)

@Composable
inline fun LogCompositions(msg: String) {
    val ref = remember { Ref(0) }
    SideEffect { ref.value++ }
    Log.d("RecompositionLog", "Compositions: $msg ${ref.value}")
}


@Composable
fun Greeting() {
    var state by remember {
        mutableStateOf("Hi Foo")
    }
    LogCompositions(msg = "Greeting Scope")
    Text(text = state)
    Button(
        onClick = { state = "Hi Foo ${Random.nextInt()}" },
        modifier = Modifier
            .padding(top = 32.dp)
    ) {
        LogCompositions(msg = "Button Scope")
        Text(
            text = "Click Me!"
        )
    }
}

@Composable
fun Greeting2() {
    LogCompositions(msg = "Greeting Scope")
    var state by remember {  //We move this line of code after log recomposition and closer to its caller and
        mutableStateOf("Hi Foo")
    }
    Text(text = state)
    Button(
        onClick = { state = "Hi Foo ${Random.nextInt()}" },
        modifier = Modifier
            .padding(top = 32.dp)
    ) {
        LogCompositions(msg = "Button Scope")
        Text(
            text = "Click Me!"
        )
    }
}