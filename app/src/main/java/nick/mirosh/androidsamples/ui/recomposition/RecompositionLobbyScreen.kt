package nick.mirosh.androidsamples.ui.recomposition

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import nick.mirosh.androidsamples.navigateSingleTopTo
import nick.mirosh.androidsamples.ui.RecompositionList
import nick.mirosh.androidsamples.ui.RecompositionLobbyScreen
import nick.mirosh.androidsamples.ui.SimpleRecomposition

@Composable
fun RecompositionLobbyScreen() {
    val navController = rememberNavController()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        NavHost(
            navController = navController,
            startDestination = RecompositionLobbyScreen.route,
            modifier = Modifier
        ) {
            setUpRecompositionNavigation(navController)
        }
    }
}


@Composable
fun RecompositionLobbyContent(
    onSimpleRecompositionClicked: () -> Unit,
    onListRecompositionClicked: () -> Unit
) {

    Column {
        Text(
            text = "Simple Recomposition",
            modifier = Modifier
                .clickable {
                    onSimpleRecompositionClicked()
                }
                .padding(24.dp)
        )
        Text(
            text = "List Recomposition",
            modifier = Modifier
                .clickable {
                    onListRecompositionClicked()
                }
                .padding(24.dp)
        )
    }
}

fun NavGraphBuilder.setUpRecompositionNavigation(navController: NavHostController) {

    composable(route = RecompositionLobbyScreen.route) {
        RecompositionLobbyContent(
            onSimpleRecompositionClicked = {
                navController.navigateSingleTopTo(
                    SimpleRecomposition.route
                )
            },
            onListRecompositionClicked = {
                navController.navigateSingleTopTo(
                    RecompositionList.route
                )
            }
        )
    }
    composable(route = RecompositionList.route) {
        RecompositionListScreen()
    }
    composable(
        route = SimpleRecomposition.route
    ) {
        RecompositionScreenRunner()
    }
}
