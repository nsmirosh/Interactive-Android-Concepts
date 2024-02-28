package nick.mirosh.androidsamples.ui.background_processing.multiple_processes

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext


@Composable
fun ProcessesScreen(
) {
    val localContext = LocalContext.current
    MaterialTheme {
        Column {
            Button(
                onClick = {
                    val intent = Intent(
                        localContext,
                        MySeparateProcessService::class.java
                    )
                    localContext.startService(intent)
                }
            ) {
                Text("Separate Process")
            }

            Button(
                onClick = {
                    val intent = Intent(
                        localContext,
                        MySameProcessService::class.java
                    )
                    localContext.startService(intent)
                }
            ) {
                Text("Same Process")
            }
        }
    }

}