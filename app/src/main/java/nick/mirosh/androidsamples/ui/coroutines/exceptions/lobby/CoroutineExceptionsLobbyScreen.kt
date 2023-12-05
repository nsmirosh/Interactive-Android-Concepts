package nick.mirosh.androidsamples.ui.coroutines.exceptions.lobby

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
import nick.mirosh.androidsamples.ui.CoroutineExceptionsLobbyDestination
import nick.mirosh.androidsamples.ui.DifferentExceptionsChallengeDestination
import nick.mirosh.androidsamples.ui.ExceptionPropagationDestination
import nick.mirosh.androidsamples.ui.coroutines.async.AsyncComparisonScreen
import nick.mirosh.androidsamples.ui.coroutines.exceptions.different_exceptions.DifferentExceptionsScreen

@Composable
fun CoroutineExceptionsLobbyScreen() {
    val navController = rememberNavController()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        NavHost(
            navController = navController,
            startDestination = CoroutineExceptionsLobbyDestination.route,
            modifier = Modifier
        ) {
            setUpCoroutineExceptionsNavigation(navController)
        }
    }
}

@Composable
fun ExceptionsLobbyContent(
    onDifferentExceptionClicked: (() -> Unit)? = null,
    onExceptionPropagationClicked: (() -> Unit)? = null,
) {
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.verticalScroll(scrollState)) {
        Text(
            text = "Different exceptions",
            modifier = Modifier
                .clickable {
                    onDifferentExceptionClicked?.invoke()
                }
                .padding(24.dp)
        )
        Text(
            text = "Exception Propagation",
            modifier = Modifier
                .clickable {
                    onExceptionPropagationClicked?.invoke()
                }
                .padding(24.dp)
        )
    }
}

fun NavGraphBuilder.setUpCoroutineExceptionsNavigation(navController: NavHostController) {
    composable(route = CoroutineExceptionsLobbyDestination.route) {
        ExceptionsLobbyContent(
            onDifferentExceptionClicked = {
                navController.navigateSingleTopTo(
                    DifferentExceptionsChallengeDestination.route
                )
            },
            onExceptionPropagationClicked = {
                navController.navigateSingleTopTo(
                    ExceptionPropagationDestination.route
                )
            }
        )
    }

    composable(route = ExceptionPropagationDestination.route) {
        AsyncComparisonScreen()
    }
    composable(route = DifferentExceptionsChallengeDestination.route) {
         DifferentExceptionsScreen()
    }
}
