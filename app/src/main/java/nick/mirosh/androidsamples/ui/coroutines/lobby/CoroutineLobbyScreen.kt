package nick.mirosh.androidsamples.ui.coroutines.lobby

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nick.mirosh.androidsamples.ui.coroutines.async.AsyncComparisonScreen
import nick.mirosh.androidsamples.ui.coroutines.cancellation.CancellationScreen
import nick.mirosh.androidsamples.ui.coroutines.coroutine_scope.CoroutineScopeScreen
import nick.mirosh.androidsamples.ui.coroutines.remember_coroutine_scope.Coroutines

@Composable
fun CoroutineLobbyScreen() {

    var currentState by remember {
        mutableStateOf<CoroutineLobbyScreenEvent>(
            CoroutineLobbyScreenEvent.NavigateToLobby
        )
    }
    when (currentState) {
        CoroutineLobbyScreenEvent.NavigateToLobby ->
            LobbyContent(
                onRememberCoroutineScopeClicked = {
                    currentState = CoroutineLobbyScreenEvent.NavigateToRememberCoroutineScope
                },
                onAsyncComparisonClicked = {
                    currentState = CoroutineLobbyScreenEvent.NavigateToAsync
                },
                onCoroutineScopeClicked = {
                    currentState = CoroutineLobbyScreenEvent.CoroutineScope
                },
                onCancellationClicked = {
                    currentState = CoroutineLobbyScreenEvent.Cancellation
                }
            )

        CoroutineLobbyScreenEvent.NavigateToRememberCoroutineScope ->
            Coroutines()

        CoroutineLobbyScreenEvent.NavigateToAsync -> {
            AsyncComparisonScreen()
        }

        CoroutineLobbyScreenEvent.CoroutineScope -> {
            CoroutineScopeScreen()
        }

        CoroutineLobbyScreenEvent.Cancellation -> {
            CancellationScreen()
        }
    }
}

@Composable
fun LobbyContent(
    onRememberCoroutineScopeClicked: (() -> Unit)? = null,
    onAsyncComparisonClicked: (() -> Unit)? = null,
    onCoroutineScopeClicked: (() -> Unit)? = null,
    onCancellationClicked: (() -> Unit)? = null,
) {

    val scrollState = rememberScrollState()

    Column(modifier = Modifier.verticalScroll(scrollState)) {
        Text(
            text = "Remember coroutine scope",
            modifier = Modifier
                .clickable {
                    onRememberCoroutineScopeClicked?.invoke()
                }
                .padding(24.dp)
        )
        Text(
            text = "Async comparison",
            modifier = Modifier
                .clickable {
                    onAsyncComparisonClicked?.invoke()
                }
                .padding(24.dp)
        )
        Text(
            text = "Coroutine scope",
            modifier = Modifier
                .clickable {
                    onCoroutineScopeClicked?.invoke()
                }
                .padding(24.dp)
        )
        Text(
            text = "Cancellation",
            modifier = Modifier
                .clickable {
                    onCancellationClicked?.invoke()
                }
                .padding(24.dp)
        )
    }
}


sealed class CoroutineLobbyScreenEvent {
    object NavigateToLobby : CoroutineLobbyScreenEvent()
    object NavigateToRememberCoroutineScope : CoroutineLobbyScreenEvent()
    object NavigateToAsync : CoroutineLobbyScreenEvent()
    object CoroutineScope : CoroutineLobbyScreenEvent()
    object Cancellation : CoroutineLobbyScreenEvent()

}