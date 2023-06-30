package nick.mirosh.androidsamples.ui.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainScreenContent(
    modifier: Modifier = Modifier,
    onSimpleListClick: () -> Unit,
    onProgressBarClick: () -> Unit,
) {
    Column {
        Text(
            text = "Simple List",
            modifier = Modifier
                .clickable {
                    onSimpleListClick()
                }
                .padding(24.dp)
        )
        Text(
            text = "Progress Bar",
            modifier = Modifier
                .clickable {
                    onProgressBarClick()
                }
                .padding(24.dp)
        )
    }
}
