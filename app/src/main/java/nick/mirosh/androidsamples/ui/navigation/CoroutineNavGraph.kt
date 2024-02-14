package nick.mirosh.androidsamples.ui.navigation

interface CoroutinesDestinations {
    val route: String
}

object CoroutineLobbyScreenDestination : CoroutinesDestinations {
    override val route = "coroutine_lobby_screen"
}

object ExceptionsLobbyDestination : CoroutinesDestinations {
    override val route = "exception_lobby"
}

object CoroutineScopeDestination : CoroutinesDestinations {
    override val route = "coroutine_scope"
}

object CooperativeCancellationDestination : CoroutinesDestinations {
    override val route = "cooperative_cancellation"
}

object AsyncComparisonDestination : CoroutinesDestinations {
    override val route = "async"
}

object RememberCoroutineScopeDestination : CoroutinesDestinations {
    override val route = "remember_coroutine_scope"
}

object DeadLockDestination : CoroutinesDestinations {
    override val route = "deadlock"
}
