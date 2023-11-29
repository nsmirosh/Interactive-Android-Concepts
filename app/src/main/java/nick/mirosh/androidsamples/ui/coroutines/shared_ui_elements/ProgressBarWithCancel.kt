package nick.mirosh.androidsamples.ui.coroutines.shared_ui_elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProgressBarWithCancel(
    modifier: Modifier = Modifier,
    progress: Float,
    label: String,
    onCancelClick: () -> Unit,
) {

    var cancelled by remember {
        mutableStateOf(false)
    }
    Column {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            text = label,
            fontSize = 20.sp
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProgressBar(
                modifier = modifier
                    .weight(2f / 3f),
                progress = progress
            )

            Button(
                colors = if (cancelled) ButtonDefaults.buttonColors(
                    backgroundColor = Color.Red,
                    contentColor = Color.White
                )
                else ButtonDefaults.buttonColors(),
                modifier = Modifier
                    .weight(1f / 3f)
                    .padding(horizontal = 16.dp),
                onClick = {
                    cancelled = true
                    onCancelClick()
                }) {
                Text(if (cancelled) "Cancelled" else "Cancel")
            }
        }
    }
}
