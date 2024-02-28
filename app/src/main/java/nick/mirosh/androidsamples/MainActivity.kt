package nick.mirosh.androidsamples

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import nick.mirosh.androidsamples.ui.jetpack_compose.lobby.ComposeLobbyScreen
import nick.mirosh.androidsamples.ui.coroutines.lobby.CoroutineLobbyScreen
import nick.mirosh.androidsamples.ui.main.MainScreenContent
import nick.mirosh.androidsamples.ui.navigation.Compose
import nick.mirosh.androidsamples.ui.navigation.Coroutines
import nick.mirosh.androidsamples.ui.navigation.MainScreen
import nick.mirosh.androidsamples.ui.theme.MyApplicationTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = MainScreen.route,
                        modifier = Modifier
                    ) {
                        setUpNavigation(navController)
                    }
                }
            }
        }
    }
}

fun NavGraphBuilder.setUpNavigation(navController: NavHostController) {

    composable(route = MainScreen.route) {
        MainScreenContent(
            onCoroutinesClicked = {
                navController.navigateSingleTopTo(
                    Coroutines.route
                )
            },
            onComposeClicked = {
                navController.navigateSingleTopTo(
                    Compose.route
                )
            }
        )
    }

    composable(route = Coroutines.route) {
        CoroutineLobbyScreen()
    }
    composable(route = Compose.route) {
        ComposeLobbyScreen()
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) { launchSingleTop = true }
