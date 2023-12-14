/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nick.mirosh.androidsamples.ui

//Main screen destinations
interface MyAppDestinations {
    val route: String
}

object MainScreen : MyAppDestinations {
    override val route = "main_screen"
}

object SimpleList : MyAppDestinations {
    override val route = "simple_list"
}

object ProgressBar : MyAppDestinations {
    override val route = "progress_bar"
}

object BottomNavigation : MyAppDestinations {
    override val route = "bottom_navigation"
}

object TodoList : MyAppDestinations {
    override val route = "todo_list"
}

object TodoDetails : MyAppDestinations {
    override val route = "todo_details"
}

object Animation : MyAppDestinations {
    override val route = "animation"
}

object LaunchedEffect : MyAppDestinations {
    override val route = "launched_effect"
}

object DisposableEffect : MyAppDestinations {
    override val route = "disposable_effect"
}

object Recomposition : MyAppDestinations {
    override val route = "recomposition"
}

object Parallax : MyAppDestinations {
    override val route = "parallax"
}

object Coroutines : MyAppDestinations {
    override val route = "remember_coroutine_scope"
}

object ModifiersDestination : MyAppDestinations {
    override val route = "modifiers"
}


//Coroutines destinations

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


//Coroutines exception handling destinations

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
