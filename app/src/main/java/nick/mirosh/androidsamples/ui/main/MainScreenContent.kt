package nick.mirosh.androidsamples.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nick.mirosh.androidsamples.ui.theme.MyApplicationTheme

@Composable
fun MainScreenContent(
    modifier: Modifier = Modifier,
    onCoroutinesClicked: (() -> Unit)? = null,
    onComposeClicked: (() -> Unit)? = null,
) {
    val scrollState = rememberScrollState()

    Column(modifier = modifier.verticalScroll(scrollState)) {
        Text(
            text = "Coroutines",
            modifier = Modifier
                .clickable {
                    onCoroutinesClicked?.invoke()
                }
                .padding(24.dp)
        )

        Text(
            text = "Compose",
            modifier = Modifier
                .clickable {
                    onComposeClicked?.invoke()
                }
                .padding(24.dp)
        )
    }
}


@Preview
@Composable
fun ColumnPreview() {
    MyApplicationTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Color.White
                )
        )
        MainScreenContent()
    }
}


