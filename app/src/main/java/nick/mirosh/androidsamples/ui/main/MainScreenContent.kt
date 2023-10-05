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
    onBottomNavClick: () -> Unit,
    onTodoClick: () -> Unit,
    onAnimationClick: () -> Unit,
    onSideEffectsClicked: () -> Unit,
    onRecompositionClicked: () -> Unit,
    onParallaxScreenClicked: () -> Unit,
    onCoroutinesClicked: () -> Unit,

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
        Text(
            text = "Bottom Nav",
            modifier = Modifier
                .clickable {
                    onBottomNavClick()
                }
                .padding(24.dp)
        )
        Text(
            text = "Todo list",
            modifier = Modifier
                .clickable {
                    onTodoClick()
                }
                .padding(24.dp)
        )
        Text(
            text = "Animation",
            modifier = Modifier
                .clickable {
                    onAnimationClick()
                }
                .padding(24.dp)
        )
        Text(
            text = "Side effects",
            modifier = Modifier
                .clickable {
                    onSideEffectsClicked()
                }
                .padding(24.dp)
        )
        Text(
            text = "Recomposition",
            modifier = Modifier
                .clickable {
                    onRecompositionClicked()
                }
                .padding(24.dp)
        )
        Text(
            text = "Parallax",
            modifier = Modifier
                .clickable {
                    onParallaxScreenClicked()
                }
                .padding(24.dp)
        )
        Text(
            text = "Coroutines",
            modifier = Modifier
                .clickable {
                    onCoroutinesClicked()
                }
                .padding(24.dp)
        )
    }
}
