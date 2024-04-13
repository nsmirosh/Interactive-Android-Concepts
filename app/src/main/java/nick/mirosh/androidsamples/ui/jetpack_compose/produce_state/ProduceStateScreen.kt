package nick.mirosh.androidsamples.ui.jetpack_compose.produce_state

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import nick.mirosh.androidsamples.ui.jetpack_compose.side_effects.disposable_effect.UserProfile

private const val TAG = "ProduceStateScreen"

@Composable
fun ProduceStateScreen() {
    example2Runner()
}

@Composable
fun UserProfileUsingProduceState() {
    val userProfile by produceState<UserProfile?>(
        initialValue = null
    ) {
        value = getUserProfile()
    }
    Box {
        when (userProfile) {
            null -> CircularProgressIndicator()
            else -> Text(userProfile!!.name)
        }
    }
}


@Composable
fun UserProfileUsingLaunchedEffect(userId: String) {
    var userProfile by remember {
        mutableStateOf<UserProfile?>(null)
    }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(userId) {
        val profile = getUserProfile()
        isLoading = false
        userProfile = profile
    }

    when {
        isLoading -> CircularProgressIndicator()
        userProfile != null -> Text(userProfile!!.name)
    }
}


@Composable
fun UserProfileUsingViewModel(
    viewModel: UserProfileViewModel = hiltViewModel()
) {
    val userProfile by viewModel.userProfile
        .collectAsState(initial = null)

    when (userProfile) {
        null -> CircularProgressIndicator()
        else -> Text(userProfile!!.name)
    }
}

@Composable
fun UserProfileUsingProduceState2() {
    val userProfile by produceState<UserProfile?>(initialValue = null) {
        value = getUserProfile()
    }
    Box {
        when (val profile = userProfile) {
            null -> CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )

            else -> Text(
                modifier = Modifier.align(Alignment.Center),
                text = profile.name
            )
        }
    }
}

@Composable
fun UserProfileUsingLaunchedEffect2(userId: String) {
    var userProfile by remember { mutableStateOf<UserProfile?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(userId) {
        val profile = getUserProfile()
        isLoading = false
        userProfile = profile
    }

    Box {
        when {
            isLoading -> CircularProgressIndicator()
            userProfile != null -> Text(
                modifier = Modifier.align(Alignment.Center),
                text = userProfile!!.name
            )
        }
    }
}

@Composable
fun UserProfileUsingViewModel2(viewModel: UserProfileViewModel = hiltViewModel()) {
    val userProfile by viewModel.userProfile.collectAsState(initial = null)
    Box {
        when (userProfile) {
            null -> CircularProgressIndicator()
            else -> Text(
                modifier = Modifier.align(Alignment.Center),
                text = userProfile!!.name
            )
        }
    }
}

private suspend fun getUserProfile(): UserProfile {
    delay(1000)
    return UserProfile("Nick Miroshnychenko")
}


/////////////// /////////////// /////////////// ///////////////
fun fetchDataPeriodically(tag: String): Flow<String> = flow {
    for (i in 1..5) {
        // Simulate network request

        delay(2000)  // Delay of 2 seconds
        emit("Data fetch $i at ${System.currentTimeMillis()}")
        Log.d(TAG, "$tag Data fetch $i at ${System.currentTimeMillis()}")
    }
}


@Composable
fun example2Runner() {

    var count by remember { mutableIntStateOf(0) }

    Box {

        Button(
            modifier = Modifier.align(Alignment.Center),
            onClick = {
                count++
            }
        ) {
            Text(text = "Click me: $count")
        }
        Column {
            if (count > 0) {
                DataDisplayUsingProduceState(count)
//                DataDisplayUsingLaunchedEffect(count)
            }
//            if (count == 1) {
//                DataDisplayUsingProduceState("2")
//                DataDisplayUsingLaunchedEffect("2")
//            }
        }
    }
}

@Composable
fun DataDisplayUsingProduceState(tag: Int) {
    Log.d(TAG, "DataDisplayUsingProduceState: ")
    val data by produceState<String?>(initialValue = null) {
        fetchDataPeriodically("Produce state $tag").collect { newData ->
            value = newData
        }
    }

    Text(data ?: "Loading...")
}

@Composable
fun DataDisplayUsingLaunchedEffect(tag: Int) {

    Log.d(TAG, " DataDisplayUsingLaunchedEffect: ")
    var data by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        fetchDataPeriodically("Launched Effect $tag").collect { newData ->
            data = newData
        }
    }

    Text(data ?: "Loading...")
}