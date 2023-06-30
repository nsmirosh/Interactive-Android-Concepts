package nick.mirosh.androidsamples.ui.bottom_nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(var title: String, var icon: ImageVector, var route: String) {

    object Home : BottomBarScreen("Home", Icons.Default.Home, "home")
    object Favorites : BottomBarScreen("Favorites", Icons.Default.Favorite, "favorites")
}