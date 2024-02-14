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
    onSimpleListClick: (() -> Unit)? = null,
    onProgressBarClick: (() -> Unit)? = null,
    onBottomNavClick: (() -> Unit)? = null,
    onAnimationClick: (() -> Unit)? = null,
    onLaunchedEffectClick: (() -> Unit)? = null,
    onDisposableEffectClick: (() -> Unit)? = null,
    onProduceStateClicked: (() -> Unit)? = null,
    onParallaxScreenClicked: (() -> Unit)? = null,
    onCoroutinesClicked: (() -> Unit)? = null,
    onModifiersClicked: (() -> Unit)? = null,
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
            text = "Drag Drop Modifiers",
            modifier = Modifier
                .clickable {
                    onModifiersClicked?.invoke()
                }
                .padding(24.dp)
        )
        Text(
            text = "Launched Effect",
            modifier = Modifier
                .clickable {
                    onLaunchedEffectClick?.invoke()
                }
                .padding(24.dp)
        )

        Text(
            text = "Disposable Effect",
            modifier = Modifier
                .clickable {
                    onDisposableEffectClick?.invoke()
                }
                .padding(24.dp)
        )
        Text(
            text = "ProduceState()",
            modifier = Modifier
                .clickable {
                    onProduceStateClicked?.invoke()
                }
                .padding(24.dp)
        )
        Text(
            text = "Simple List",
            modifier = Modifier
                .clickable {
                    onSimpleListClick?.invoke()
                }
                .padding(24.dp)
        )
        Text(
            text = "Progress Bar",
            modifier = Modifier
                .clickable {
                    onProgressBarClick?.invoke()
                }
                .padding(24.dp)
        )
        Text(
            text = "Bottom Nav",
            modifier = Modifier
                .clickable {
                    onBottomNavClick?.invoke()
                }
                .padding(24.dp)
        )
        Text(
            text = "Animation",
            modifier = Modifier
                .clickable {
                    onAnimationClick?.invoke()
                }
                .padding(24.dp)
        )
        Text(
            text = "Parallax",
            modifier = Modifier
                .clickable {
                    onParallaxScreenClicked?.invoke()
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


