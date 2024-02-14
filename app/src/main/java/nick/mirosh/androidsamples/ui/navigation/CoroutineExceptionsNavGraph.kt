package nick.mirosh.androidsamples.ui.navigation

interface CoroutineExceptionDestinations {
    val route: String
}

object CoroutineExceptionsLobbyDestination : CoroutineExceptionDestinations {
    override val route = "exceptions_lobby"
}

object DifferentExceptionsChallengeDestination : CoroutineExceptionDestinations {
    override val route = "different_exceptions"
}

object ExceptionPropagationDestination : CoroutineExceptionDestinations {
    override val route = "different_exceptions"
}
