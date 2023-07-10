package nick.mirosh.androidsamples.ui.side_effects

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun SideEffectsScreen() {

    var recomposeInt by remember { mutableIntStateOf(0) }
    Button(onClick = {
        recomposeInt++
    }) {
        Text("Recompose")
    }
    Column {
        MyComposable(recomposeInt)
        Spacer(modifier = Modifier.height(16.dp))
        val newLambda: () -> Unit = {
            Log.d(
                "SideEffectsScreen",
                "$recomposeInt"
            )
        }
        SideEffectsNew(startingWith = recomposeInt, onTimeout = newLambda)

    }
}

@Composable
fun MyComposable(startingWith: Int) {
    Text("$startingWith")
}

@Composable
fun SideEffectsNew(
    startingWith: Int,
    onTimeout: () -> Unit
) {
    val currentOnTimeout by rememberUpdatedState(onTimeout)

    LaunchedEffect(
        key1 = onTimeout,
        block = {
            Log.d(
                "SideEffectsScreen",
                "starting $startingWith"
            )
            delay(
                2000
            )
            currentOnTimeout()
        }
    )

}