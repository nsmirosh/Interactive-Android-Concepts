package nick.mirosh.androidsamples.ui.jetpack_compose.side_effects.disposable_effect

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TAG = "DisposableEffectScreen"
@Composable
fun DisposableEffectScreen() {
    var userId by remember { mutableIntStateOf(0) }
    Column {
        Button(onClick = {
            userId++
        }) {
            Text("change user id")
        }
        if (userId > 0) {
              UserProfileWithDisposableEffectBug(userId)
        }
    }
}

@Composable
fun UserProfileWithDisposableEffectBug(userId: Int) {
    var profile by remember { mutableStateOf<UserProfile?>(null) }
    var isError by remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        val job = CoroutineScope(Dispatchers.IO)
            .launch {
                profile = fetchUserProfile(userId)
            }

        onDispose {
            job.cancel()
        }
    }

    LaunchedEffect(profile) {
        if (profile != null) {
            //do something with profile
        } else {
            isError = true
        }
    }

    if (isError) {
        Text("Error Loading Profile")
    } else {
        Text("Success")
    }
}


@Composable
fun DisposableEffectCancel(userId: Int) {
    DisposableEffect(userId) {
        Log.d(TAG, "DisposableEffect: running")
        val job = CoroutineScope(Dispatchers.IO)
            .launch {
                delay(5000)
                Log.d(TAG, "DisposableEffect coroutine finished $userId")
            }

        onDispose {
            Log.d(TAG, "onDispose running")
            job.cancel()
        }
    }
}

data class UserProfile(val name: String)

suspend fun fetchUserProfile(userId: Int): UserProfile {
    delay(5000)
    return UserProfile("Nick")
}
