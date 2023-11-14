package nick.mirosh.androidsamples.ui.side_effects.disposable_effect

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun DisposableEffectScreen() {

    Column {
        Text(
            text = "DisposableEffect",
            modifier = Modifier
                .padding(24.dp)
        )
    }
}