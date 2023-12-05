package nick.mirosh.androidsamples.ui.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nick.mirosh.androidsamples.ui.theme.MyApplicationTheme

@Composable
fun MainScreenContent(
    modifier: Modifier = Modifier,
    onSimpleListClick: (() -> Unit)? = null,
    onProgressBarClick: (() -> Unit)? = null,
    onBottomNavClick: (() -> Unit)? = null,
    onTodoClick: (() -> Unit)? = null,
    onAnimationClick: (() -> Unit)? = null,
    onSideEffectsClicked: (() -> Unit)? = null,
    onRecompositionClicked: (() -> Unit)? = null,
    onParallaxScreenClicked: (() -> Unit)? = null,
    onCoroutinesClicked: (() -> Unit)? = null,
    onDisposableEffectClicked: (() -> Unit)? = null,

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
            text = "Launched Effect",
            modifier = Modifier
                .clickable {
                    onSideEffectsClicked?.invoke()
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
//        Text(
//            text = "Todo list",
//            modifier = Modifier
//                .clickable {
//                    onTodoClick?.invoke()
//                }
//                .padding(24.dp)
//        )
        Text(
            text = "Animation",
            modifier = Modifier
                .clickable {
                    onAnimationClick?.invoke()
                }
                .padding(24.dp)
        )
//        Text(
//            text = "Disposable Effect",
//            modifier = Modifier
//                .clickable {
//                    onDisposableEffectClicked?.invoke()
//                }
//                .padding(24.dp)
//        )
//        Text(
//            text = "Recomposition",
//            modifier = Modifier
//                .clickable {
//                    onRecompositionClicked?.invoke()
//                }
//                .padding(24.dp)
//        )
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
        MainScreenContent()
    }
}


