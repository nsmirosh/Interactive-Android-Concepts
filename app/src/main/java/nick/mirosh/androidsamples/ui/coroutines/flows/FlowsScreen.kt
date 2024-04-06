package nick.mirosh.androidsamples.ui.coroutines.flows

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun FlowsScreen(
    viewModel: FlowViewModel = hiltViewModel()
) {

    MaterialTheme {
        Column {
            Button(
                onClick = {
                    viewModel.runFlow()
                }
            ) {
                Text("Run Flow")
            }

            Button(
                onClick = {
                    viewModel.transformLatest()
                }
            ) {
                Text("Transform Latest")
            }

            Button(
                onClick = {
//                    viewModel.debounce()
                }
            ) {
                Text("debounce")
            }
        }
    }

}