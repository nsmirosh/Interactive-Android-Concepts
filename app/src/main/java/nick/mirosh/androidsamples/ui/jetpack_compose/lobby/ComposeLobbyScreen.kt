package nick.mirosh.androidsamples.ui.jetpack_compose.lobby

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.androidcomposeexample.ui.sideeffects.launchedeffect.LaunchedEffectScreen
import nick.mirosh.androidsamples.ui.jetpack_compose.animation.SmileyLoadingAnimation
import nick.mirosh.androidsamples.ui.jetpack_compose.bottom_nav.BottomNavigationScreen
import nick.mirosh.androidsamples.ui.jetpack_compose.drag_drop_modifier.DragDropModifierScreen
import nick.mirosh.androidsamples.ui.jetpack_compose.parallax.UriParallaxColumnRunner
import nick.mirosh.androidsamples.ui.jetpack_compose.produce_state.ProduceStateScreen
import nick.mirosh.androidsamples.ui.jetpack_compose.progress.ProgressBarContent2
import nick.mirosh.androidsamples.ui.jetpack_compose.progress.ProgressBarViewModel
import nick.mirosh.androidsamples.ui.jetpack_compose.side_effects.disposable_effect.DisposableEffectScreen
import nick.mirosh.androidsamples.ui.main.MainViewModel
import nick.mirosh.androidsamples.ui.main.SimpleListScreenContent
import nick.mirosh.androidsamples.ui.navigation.Animation
import nick.mirosh.androidsamples.ui.navigation.BottomNavigation
import nick.mirosh.androidsamples.ui.navigation.ComposeLobbyScreenDestination
import nick.mirosh.androidsamples.ui.navigation.DisposableEffect
import nick.mirosh.androidsamples.ui.navigation.LaunchedEffect
import nick.mirosh.androidsamples.ui.navigation.ModifiersDestination
import nick.mirosh.androidsamples.ui.navigation.Parallax
import nick.mirosh.androidsamples.ui.navigation.ProduceState
import nick.mirosh.androidsamples.ui.navigation.ProgressBar
import nick.mirosh.androidsamples.ui.navigation.SimpleList


@Composable
fun ComposeLobbyScreen() {
    val navController = rememberNavController()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        NavHost(
            navController = navController,
            startDestination = ComposeLobbyScreenDestination.route,
            modifier = Modifier
        ) {
            setUpComposeNavigation(navController)
        }
    }
}


@Composable
fun ComposeLobbyContent(
    onSimpleListClick: (() -> Unit)? = null,
    onProgressBarClick: (() -> Unit)? = null,
    onBottomNavClick: (() -> Unit)? = null,
    onAnimationClick: (() -> Unit)? = null,
    onLaunchedEffectClick: (() -> Unit)? = null,
    onDisposableEffectClick: (() -> Unit)? = null,
    onProduceStateClicked: (() -> Unit)? = null,
    onParallaxScreenClicked: (() -> Unit)? = null,
    onModifiersClicked: (() -> Unit)? = null,
) {

    val scrollState = rememberScrollState()
    Column(modifier = Modifier.verticalScroll(scrollState)) {
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

fun NavGraphBuilder.setUpComposeNavigation(navController: NavHostController) {

    composable(route = ComposeLobbyScreenDestination.route) {
        ComposeLobbyContent(
            onSimpleListClick = { navController.navigate(SimpleList.route) },
            onProgressBarClick = { navController.navigate(ProgressBar.route) },
            onBottomNavClick = { navController.navigate(BottomNavigation.route) },
            onLaunchedEffectClick = {
                navController.navigate(LaunchedEffect.route)
            },
            onDisposableEffectClick = {
                navController.navigate(DisposableEffect.route)
            },
            onProduceStateClicked = {
                navController.navigate(ProduceState.route)
            },
            onAnimationClick = {
                navController.navigate(Animation.route)
            },
            onParallaxScreenClicked = {
                navController.navigate(Parallax.route)
            },
            onModifiersClicked = {
                navController.navigate(ModifiersDestination.route)
            }
        )
    }

    composable(route = SimpleList.route) {
        val viewModel = hiltViewModel<MainViewModel>()
        SimpleListScreenContent(
            viewModel = viewModel,
            onDeleteItem = { viewModel.onDeleteItem(it) }
        )
    }
    composable(route = ProgressBar.route) {
        val viewModel = hiltViewModel<ProgressBarViewModel>()
        ProgressBarContent2(
            viewModel = viewModel
        )
    }
    composable(route = BottomNavigation.route) {
        BottomNavigationScreen()
    }
    composable(route = Animation.route) {
        SmileyLoadingAnimation()
    }
    composable(route = LaunchedEffect.route) {
        LaunchedEffectScreen()
    }
    composable(route = DisposableEffect.route) {
        DisposableEffectScreen()
    }
    composable(route = Parallax.route) {
        UriParallaxColumnRunner()
    }

    composable(route = ModifiersDestination.route) {
        DragDropModifierScreen()
    }
    composable(route = ProduceState.route) {
        ProduceStateScreen()
    }

}
