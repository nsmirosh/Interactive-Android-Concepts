package nick.mirosh.androidsamples.ui.coroutines.lobby

import CooperativeCancellationScreen
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import nick.mirosh.androidsamples.navigateSingleTopTo
import nick.mirosh.androidsamples.ui.AsyncComparisonDestination
import nick.mirosh.androidsamples.ui.CooperativeCancellationDestination
import nick.mirosh.androidsamples.ui.CoroutineLobbyScreenDestination
import nick.mirosh.androidsamples.ui.CoroutineScopeDestination
import nick.mirosh.androidsamples.ui.ExceptionsLobbyDestination
import nick.mirosh.androidsamples.ui.RememberCoroutineScopeDestination
import nick.mirosh.androidsamples.ui.coroutines.async.AsyncComparisonScreen
import nick.mirosh.androidsamples.ui.coroutines.coroutine_scope.CoroutineScopeScreen
import nick.mirosh.androidsamples.ui.coroutines.exceptions.lobby.CoroutineExceptionsLobbyScreen
import nick.mirosh.androidsamples.ui.coroutines.remember_coroutine_scope.RememberCoroutineScopeScreen

@Composable
fun CoroutineLobbyScreen() {
    val navController = rememberNavController()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        NavHost(
            navController = navController,
            startDestination = CoroutineLobbyScreenDestination.route,
            modifier = Modifier
        ) {
            setUpNavigation(navController)
        }
    }
}

@Composable
fun LobbyContent(
    onRememberCoroutineScopeClicked: (() -> Unit)? = null,
    onAsyncComparisonClicked: (() -> Unit)? = null,
    onCoroutineScopeClicked: (() -> Unit)? = null,
    onExceptionsClicked: (() -> Unit)? = null,
    onCooperativeCancellationClicked: (() -> Unit)? = null,
) {

    val scrollState = rememberScrollState()

    Column(modifier = Modifier.verticalScroll(scrollState)) {
        Text(
            text = "Remember coroutine scope",
            modifier = Modifier
                .clickable {
                    onRememberCoroutineScopeClicked?.invoke()
                }
                .padding(24.dp)
        )
        Text(
            text = "Async comparison",
            modifier = Modifier
                .clickable {
                    onAsyncComparisonClicked?.invoke()
                }
                .padding(24.dp)
        )
        Text(
            text = "Coroutine scope",
            modifier = Modifier
                .clickable {
                    onCoroutineScopeClicked?.invoke()
                }
                .padding(24.dp)
        )
        Text(
            text = "Cooperative cancellation",
            modifier = Modifier
                .clickable {
                    onCooperativeCancellationClicked?.invoke()
                }
                .padding(24.dp)
        )
        Text(
            text = "Exceptions",
            modifier = Modifier
                .clickable {
                    onExceptionsClicked?.invoke()
                }
                .padding(24.dp)
        )
    }
}

fun NavGraphBuilder.setUpNavigation(navController: NavHostController) {
    composable(route = CoroutineLobbyScreenDestination.route) {
        LobbyContent(
            onAsyncComparisonClicked = {
                navController.navigateSingleTopTo(
                    AsyncComparisonDestination.route
                )
            },
            onCoroutineScopeClicked = {
                navController.navigateSingleTopTo(
                    CoroutineScopeDestination.route
                )
            },
            onExceptionsClicked = {
                navController.navigateSingleTopTo(
                    ExceptionsLobbyDestination.route
                )
            },
            onCooperativeCancellationClicked = {
                navController.navigateSingleTopTo(
                    CooperativeCancellationDestination.route
                )
            },
            onRememberCoroutineScopeClicked = {
                navController.navigateSingleTopTo(
                    RememberCoroutineScopeDestination.route
                )
            }
        )
    }

    composable(route = AsyncComparisonDestination.route) {
        AsyncComparisonScreen()
    }
    composable(route = ExceptionsLobbyDestination.route) {
         CoroutineExceptionsLobbyScreen()
    }
    composable(route = CooperativeCancellationDestination.route) {
        CooperativeCancellationScreen()
    }
    composable(route = CoroutineScopeDestination.route) {
        CoroutineScopeScreen()
    }
    composable(route = RememberCoroutineScopeDestination.route) {
        RememberCoroutineScopeScreen()
    }
}
