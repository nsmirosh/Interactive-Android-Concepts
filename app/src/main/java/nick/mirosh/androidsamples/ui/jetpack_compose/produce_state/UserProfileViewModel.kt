package nick.mirosh.androidsamples.ui.jetpack_compose.produce_state

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import nick.mirosh.androidsamples.ui.jetpack_compose.side_effects.disposable_effect.UserProfile

class UserProfileViewModel : ViewModel() {
    val userProfile: Flow<UserProfile?> = flow {
        emit(getUserProfile())
    }.flowOn(Dispatchers.IO)
}

private suspend fun getUserProfile(): UserProfile {
    delay(1000)
    return UserProfile("Nick Miroshnychenko")
}
