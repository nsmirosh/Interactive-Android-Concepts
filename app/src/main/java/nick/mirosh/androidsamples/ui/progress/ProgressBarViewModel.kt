package nick.mirosh.androidsamples.ui.progress

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProgressBarViewModel @Inject constructor() :
    ViewModel() {

    private val _progress = MutableStateFlow(0)
    val progress = _progress.asStateFlow()

    fun onStartPressed() {
        viewModelScope.launch {
            var counter = 0
            while (counter < 100) {
                val randomNo = (0..10).random()
                if (counter + randomNo > 100) continue
                counter += randomNo
                _progress.value = counter
                delay(500)
            }
        }
    }
}
