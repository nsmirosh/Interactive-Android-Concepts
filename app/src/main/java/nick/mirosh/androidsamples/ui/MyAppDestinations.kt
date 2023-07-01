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

/**
 * Contract for information needed on every Rally navigation destination
 */
interface MyAppDestinations {
    val route: String
}

object MainScreen: MyAppDestinations {
    override val route = "main_screen"
}
/**
 * Rally app navigation destinations
 */
object SimpleList : MyAppDestinations {
    override val route = "simple_list"
}
object SimpleListWithDeletion : MyAppDestinations {
    override val route = "simple_list_with_deletion"
}
object ProgressBar: MyAppDestinations {
    override val route = "progres_bar"
}
object BottomNavigation: MyAppDestinations {
    override val route = "bottom_navigation"
}