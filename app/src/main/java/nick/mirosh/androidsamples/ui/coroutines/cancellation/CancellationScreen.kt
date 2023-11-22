package nick.mirosh.androidsamples.ui.coroutines.cancellation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun CancellationScreen(
    cancellationViewModel: CancellationViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Button(onClick = {
            cancellationViewModel.unCooperativeCancellation()
        }) {
            Text("Uncooperative cancellation")
        }
        Button(onClick = {
            cancellationViewModel.catchingAndNotReThrowingException()
        }) {
            Text("Catching and not re-throwing exception")
        }
        Button(onClick = {
            cancellationViewModel.checkCancellationWithIsActive()
        }) {
            Text("Check for cancellation with isActive")
        }
        Button(onClick = {
            cancellationViewModel.closingResourcesWithFinally()
        }) {
            Text("Closing resources with finally")
        }
        Text(
            text = "Cooperative cancellation examples",
            modifier = Modifier
                .padding(top = 16.dp, bottom = 16.dp)
                .align(
                    CenterHorizontally
                )
        )
        Button(onClick = {
            cancellationViewModel.uncooperativeSeparateCancelAndJoin()
        }) {
            Text("separate cancel and join")
        }
        Button(onClick = {
            cancellationViewModel.cancelAndJoinCooperativeWithIsActive()
        }) {
            Text("cooperative separate cancel and join with isActive")
        }
        Text(
            text = "Exception handling",
            modifier = Modifier
                .padding(top = 16.dp, bottom = 16.dp)
                .align(
                    CenterHorizontally
                )
        )
        Button(onClick = {
            cancellationViewModel.propagationOfExceptionsInLaunch()
        }) {
            Text("Propagation of exceptions in launch")
        }
        Button(onClick = {
            cancellationViewModel.propagationOfExceptionsInAsync()
        }) {
            Text("Propagation of exceptions in async")
        }

        Button(onClick = {
            cancellationViewModel.coroutineExceptionHandler()
        }) {
            Text("CoroutineExceptionHandler")
        }

        Button(onClick = {
            cancellationViewModel.cancellingWithCancelAndParentRunning()
        }) {
            Text("Cancelling with cancel and parent running")
        }
        Button(onClick = {
            cancellationViewModel. cancellingWithCancellationExceptionAndParentRunning()
        }) {
            Text("Cancelling with CancellationException and parent running")
        }

        Text(
            text = "Context and dispatchers",
            modifier = Modifier
                .padding(top = 16.dp, bottom = 16.dp)
                .align(
                    CenterHorizontally
                )
        )
        Button(onClick = {
            cancellationViewModel.separateJob()
        }) {
            Text("separate job")
        }

        Button(onClick = {
            cancellationViewModel.separateJobViewModelScopes()
        }) {
            Text("separate viewModelScopes")
        }
    }
}


@Preview
@Composable
fun CancellationScreenPreview() {
    Box(modifier = Modifier.background(Color.White)) {
        CancellationScreen()
    }
}