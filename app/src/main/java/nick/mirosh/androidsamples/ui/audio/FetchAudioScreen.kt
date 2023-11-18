package nick.mirosh.androidsamples.ui.audio

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState

// Download a file to internal storage
// Read that file from internal storage
@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun FetchAudioScreen(
    viewModel: FetchAudioViewModel = hiltViewModel()
) {
    val audioPermissionState = rememberPermissionState(android.Manifest.permission.RECORD_AUDIO)
    when (audioPermissionState.status) {
        PermissionStatus.Granted -> {
            // Permission is granted, you can use audio source here
            viewModel.recordAudio()
        }

        is PermissionStatus.Denied -> {
            val status = audioPermissionState.status as PermissionStatus.Denied
            if (status.shouldShowRationale) {
                // Explain why the permission is needed
            }
            FetchAudioScreenContent {
                audioPermissionState.launchPermissionRequest()
            }
        }
    }
}

@Composable
fun FetchAudioScreenContent(
    onPermissionClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Button(onClick = { onPermissionClick() }) {
            Text("Request Permission")
        }
    }
}

@Preview
@Composable
fun FetchAudioScreenPreview() {
    FetchAudioScreenContent()
}
