package nick.mirosh.androidsamples

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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import nick.mirosh.androidsamples.ui.MainScreen
import nick.mirosh.androidsamples.ui.SimpleList
import nick.mirosh.androidsamples.ui.main.MainScreenContent
import nick.mirosh.androidsamples.ui.main.MainViewModel
import nick.mirosh.androidsamples.ui.main.SimpleListScreenContent
import nick.mirosh.androidsamples.ui.theme.MyApplicationTheme
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

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
                        composable(route = MainScreen.route) {
                            MainScreenContent(
                                onSimpleListClick = {
                                    navController.navigateSingleTopTo(SimpleList.route)
                                }
                            )
                        }
                        composable(route = SimpleList.route) {
                            val viewModel = hiltViewModel<MainViewModel>()
                            SimpleListScreenContent(
                                viewModel = viewModel
                            )
                        }
                    }
                }
            }
        }
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