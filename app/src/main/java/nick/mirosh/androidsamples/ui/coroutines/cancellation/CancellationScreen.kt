package nick.mirosh.androidsamples.ui.coroutines.cancellation

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun CancellationScreen(
    cancellationViewModel: CancellationViewModel = hiltViewModel()
) {

    Column {
        Button(onClick = {  }) {

        }

    }
}