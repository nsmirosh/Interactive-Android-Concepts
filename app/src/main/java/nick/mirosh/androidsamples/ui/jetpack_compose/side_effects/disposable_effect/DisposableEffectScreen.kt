package nick.mirosh.androidsamples.ui.jetpack_compose.side_effects.disposable_effect

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import nick.mirosh.androidsamples.ui.DisposableEffect
import java.util.Timer
import java.util.TimerTask


private const val TAG = "DisposableEffectScreen"

@Composable
fun DisposableEffectScreen() {
//    DisposableEffectCoroutineChallenge()
    var userId by remember { mutableIntStateOf(0) }
    Column {
        Button(onClick = {
            userId++
        }) {
            Text("change user id")
        }
        if (userId > 0) {
            UserProfile2(userId)
        }
    }
}

@Composable
fun UserProfile2(userId: Int) {
    DisposableEffect(userId) {
        val job = CoroutineScope(Dispatchers.IO)
            .launch {
                Log.d(TAG, "UserProfile2: Launching the coroutine")
            }

        onDispose {
            Log.d(TAG, "UserProfile2: onDispose running")
            job.cancel()
        }
    }
}

@Composable
fun UserProfile(userId: Int) {
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

@Composable
fun LaunchedEffectCancel(userId: Int) {
    val scope = rememberCoroutineScope()
    LaunchedEffect(userId) {
        Log.d(TAG, "LaunchedEffect running ")
        scope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                delay(5000)
                Log.d(TAG, "LaunchedEffect: $userId")
            }
        }
    }
}

data class UserProfile(val name: String)

suspend fun fetchUserProfile(userId: Int): UserProfile {
    delay(5000)
    return UserProfile("Nick")
}


@Composable
fun DisposableEffectCoroutineChallenge() {
    var count by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        repeat(3) {
            delay(500) // 500ms delay
            count++
        }
    }

    DisposableEffect(count) {
        println("Effect start for count: $count")
        val job = CoroutineScope(Dispatchers.Default).launch {
            delay(1000) // 1-second delay
            println("Post-delay in effect for count: $count")
        }

        onDispose {
            println("Disposing effect for count: $count")
            job.cancel() // Cancel the coroutine
        }
    }
}