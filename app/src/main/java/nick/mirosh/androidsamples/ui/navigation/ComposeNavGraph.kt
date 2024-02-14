package nick.mirosh.androidsamples.ui.navigation


interface ComposeDestinations {
    val route: String
}
object ComposeLobbyScreenDestination : ComposeDestinations {
    override val route = "coroutine_lobby_screen"
}
object SimpleList : ComposeDestinations {
    override val route = "simple_list"
}

object ProgressBar : ComposeDestinations {
    override val route = "progress_bar"
}

object BottomNavigation : ComposeDestinations {
    override val route = "bottom_navigation"
}

object Animation : ComposeDestinations {
    override val route = "animation"
}

object LaunchedEffect : ComposeDestinations {
    override val route = "launched_effect"
}

object DisposableEffect : ComposeDestinations {
    override val route = "disposable_effect"
}
object ProduceState: ComposeDestinations {
    override val route = "produce_state"
}

object Parallax : ComposeDestinations {
    override val route = "parallax"
}

object ModifiersDestination : ComposeDestinations {
    override val route = "modifiers"
}
