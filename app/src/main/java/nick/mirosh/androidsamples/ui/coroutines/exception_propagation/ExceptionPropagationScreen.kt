
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import nick.mirosh.androidsamples.ui.coroutines.CancellableProgressBar
import nick.mirosh.androidsamples.ui.coroutines.exception_propagation.ExceptionPropagationViewModel

@Composable
fun ExceptionPropagationScreen(
    exceptionPropagationViewModel: ExceptionPropagationViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    var cancelled by remember { mutableStateOf(false) }
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
        Button(onClick = {
            cancelled = true
        }) {
            Text("start propagation")
        }
        CancellableProgressBar(
            modifier = Modifier.padding(top = 16.dp),
            progress =  0.3f,
            cancelled = cancelled,
        )
    }
}

@Composable
@Preview
fun ExceptionPropagationScreenPreview() {
    Box(
        modifier = Modifier
            .background(color = White)
            .fillMaxSize()
    ) {
        ExceptionPropagationScreen()
    }
}

