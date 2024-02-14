import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import nick.mirosh.androidsamples.ui.coroutines.deadlock.DeadLockViewModel


@Composable
fun DeadLockScreen(
    viewModel: DeadLockViewModel = hiltViewModel()
) {
    MaterialTheme {
        Box(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.align(Alignment.Center)) {
                Button(onClick = {
                    viewModel.runLogicalDeadlock()
                }) {
                    Text("Run Logical Deadlock")
                }

                Button(onClick = {
                    viewModel.runActualDeadlock()
                }) {
                    Text("Run Actual Deadlock. Warning: this will freeze the app.")
                }
            }
        }
    }
}

