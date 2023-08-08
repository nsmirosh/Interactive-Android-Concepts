package nick.mirosh.androidsamples

import TodoListScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.androidcomposeexample.ui.sideeffects.launchedeffect.LaunchedEffectScreen
import dagger.hilt.android.AndroidEntryPoint
import nick.mirosh.androidsamples.ui.Animation
import nick.mirosh.androidsamples.ui.BottomNavigation
import nick.mirosh.androidsamples.ui.MainScreen
import nick.mirosh.androidsamples.ui.Parallax
import nick.mirosh.androidsamples.ui.ProgressBar
import nick.mirosh.androidsamples.ui.Recomposition
import nick.mirosh.androidsamples.ui.SideEffects
import nick.mirosh.androidsamples.ui.SimpleList
import nick.mirosh.androidsamples.ui.TodoDetails
import nick.mirosh.androidsamples.ui.TodoList
import nick.mirosh.androidsamples.ui.animation.JumpingCircleRunner
import nick.mirosh.androidsamples.ui.bottom_nav.BottomNavigationScreen
import nick.mirosh.androidsamples.ui.main.MainScreenContent
import nick.mirosh.androidsamples.ui.main.MainViewModel
import nick.mirosh.androidsamples.ui.main.SimpleListScreenContent
import nick.mirosh.androidsamples.ui.parallax.ParallaxScreen
import nick.mirosh.androidsamples.ui.progress.ProgressBarContent2
import nick.mirosh.androidsamples.ui.progress.ProgressBarViewModel
import nick.mirosh.androidsamples.ui.recomposition.RecompositionLobbyScreen
import nick.mirosh.androidsamples.ui.theme.MyApplicationTheme
import nick.mirosh.androidsamples.ui.todo.TodoViewModel
import nick.mirosh.androidsamples.ui.todo.details.TodoDetailsScreen
import nick.mirosh.androidsamples.ui.todo.details.TodoDetailsViewModel

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
            onSimpleListClick = { navController.navigateSingleTopTo(SimpleList.route) },
            onProgressBarClick = { navController.navigateSingleTopTo(ProgressBar.route) },
            onBottomNavClick = {
                navController.navigateSingleTopTo(
                    BottomNavigation.route
                )
            },
            onTodoClick = { navController.navigateSingleTopTo(TodoList.route) },
            onSideEffectsClicked = {
                navController.navigateSingleTopTo(
                    SideEffects.route
                )
            },

            onAnimationClick = { navController.navigateSingleTopTo(Animation.route) },
            onRecompositionClicked = {
                navController.navigateSingleTopTo(
                    Recomposition.route
                )
            },
            onParallaxScreenClicked = {
                navController.navigateSingleTopTo(
                    Parallax.route
                )
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
    composable(route = TodoList.route) {
        val viewModel = hiltViewModel<TodoViewModel>()
        TodoListScreen(viewModel = viewModel, onNewTodoClicked = {
            navController.navigateSingleTopTo(TodoDetails.route)
        })
    }
    composable(route = TodoDetails.route) {
        val viewModel = hiltViewModel<TodoDetailsViewModel>()
        TodoDetailsScreen { titleText, descriptionText ->
            viewModel.insertTodo(
                title = titleText,
                description = descriptionText

            )
            navController.popBackStack()
        }
    }
    composable(route = Animation.route) {
        JumpingCircleRunner()
    }
    composable(route = SideEffects.route) {
        LaunchedEffectScreen()
    }
    composable(route = Recomposition.route) {
        RecompositionLobbyScreen()
    }
    composable(route = Parallax.route) {
        ParallaxScreen()
//        ComposableFunctionsDemo()
    }
}


fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) { launchSingleTop = true }

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}