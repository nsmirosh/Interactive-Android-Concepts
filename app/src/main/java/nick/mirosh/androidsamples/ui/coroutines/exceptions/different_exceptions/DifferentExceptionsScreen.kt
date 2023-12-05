package nick.mirosh.androidsamples.ui.coroutines.exceptions.different_exceptions

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import nick.mirosh.androidsamples.ui.coroutines.exceptions.exception_propagation.ExceptionPropagationViewModel


@Composable
fun DifferentExceptionsScreen(
    viewModel: ExceptionPropagationViewModel = hiltViewModel()
) {

    Button(onClick = {
        viewModel.simpleChallenge()
    }) {
        Text("start")
    }
}