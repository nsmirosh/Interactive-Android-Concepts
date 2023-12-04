import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nick.mirosh.androidsamples.ui.coroutines.CancellableProgressBar
import nick.mirosh.androidsamples.ui.coroutines.exceptions.exception_propagation.ExceptionPropagationViewModel
import nick.mirosh.androidsamples.ui.coroutines.firstLevelIndent
import nick.mirosh.androidsamples.ui.coroutines.secondLevelIndent


@Composable
fun ExceptionPropagationScreen(
    viewModel: ExceptionPropagationViewModel = hiltViewModel()
) {

    val grandParentProgress by viewModel.grandParentProgressUpdate.collectAsStateWithLifecycle()
    val parentProgress by viewModel.parentProgressUpdate.collectAsStateWithLifecycle()
    val childProgress by viewModel.childProgressUpdate.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()
    var cancelled by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {

        Button(onClick = {
            viewModel.simpleChallenge()
        }) {
            Text("start propagation")
        }
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = "GrandParent",
        )
        Row(
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            CancellableProgressBar(
                modifier = Modifier
                    .width(200.dp),
                progress = grandParentProgress,
                cancelled = cancelled,
            )
            TextCheckBox(
                checked = false,
                onCheckedChange = {
                    cancelled = it
                }
            )
        }
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = "Parent",
        )
        CancellableProgressBar(
            modifier = Modifier
                .padding(top = 16.dp)
                .firstLevelIndent(),
            progress = parentProgress,
            cancelled = cancelled,
        )
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = "Child",
        )
        CancellableProgressBar(
            modifier = Modifier
                .padding(top = 16.dp)
                .secondLevelIndent(),
            progress = childProgress,
            cancelled = cancelled,
        )
    }
}

@Composable
fun TextCheckBox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    )
    {
        Checkbox(
            checked = checked,
            onCheckedChange = {
                onCheckedChange(it)
            }
        )
        Text(
            "Catch here"
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

