
package nick.mirosh.androidsamples.ui.navigation

interface MainDestination {
    val route: String
}

object MainScreen : MainDestination {
    override val route = "main_screen"
}

object Coroutines : MainDestination {
    override val route = "remember_coroutine_scope"
}

object Compose : MainDestination {
    override val route = "compose"
}

object AndroidApis : MainDestination {
    override val route = "android_apis"
}


