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

    private val _progress = MutableStateFlow(0.0f)
    val progress = _progress.asStateFlow()


    fun onStartPressed() {
        viewModelScope.launch {
            //iterate from 0 to 100 and post an update to _progress every 100ms
            var j = 0f
            for (i in 0..10) {
                j += 0.1f
                _progress.value = j
                delay(500)
            }
        }
    }
}
