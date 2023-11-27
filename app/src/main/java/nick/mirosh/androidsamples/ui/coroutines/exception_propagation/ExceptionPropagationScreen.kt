import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import nick.mirosh.androidsamples.ui.coroutines.exception_propagation.ExceptionPropagationViewModel


@Composable
fun ExceptionPropagationScreen(
    exceptionPropagationViewModel: ExceptionPropagationViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Button(onClick = {
            exceptionPropagationViewModel.start()
        }) {
            Text("start propagation")
        }
    }
}

