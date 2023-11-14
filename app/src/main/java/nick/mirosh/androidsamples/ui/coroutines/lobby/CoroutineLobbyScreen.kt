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
                }
            )

        CoroutineLobbyScreenEvent.NavigateToRememberCoroutineScope ->
            Coroutines()

    }
}

@Composable
fun LobbyContent(
    onRememberCoroutineScopeClicked: (() -> Unit)? = null,
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
    }
}


sealed class CoroutineLobbyScreenEvent {
    object NavigateToLobby : CoroutineLobbyScreenEvent()
    object NavigateToRememberCoroutineScope : CoroutineLobbyScreenEvent()

}