package nick.mirosh.androidsamples.ui.coroutines.async

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

class AsyncComparisonViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AsyncComparisonUIState.DeferredUpdate(0f, 0))
    val uiState = _uiState.asStateFlow()

    fun launchAsyncs() {
        viewModelScope.launch {
            val timeSpent = measureTimeMillis {
                val deferred = async {
                    delay(100)
                    _uiState.value = AsyncComparisonUIState.DeferredUpdate(0.1f, 1)
                }
                val deffered2 = async {
                    delay(100)
                    _uiState.value = AsyncComparisonUIState.DeferredUpdate(0.1f, 2)
                }
                awaitAll(deferred, deffered2)
            }
            Log.d("AsyncComparison", "asyncs finished in $timeSpent")
        }
    }

    fun launchCoroutines() {
        viewModelScope.launch {
            val timeSpent = measureTimeMillis {
                val job = viewModelScope.launch {
                    delay(1000)
                }
                val job2 = viewModelScope.launch {
                    delay(1000)
                }
                job.join()
                job2.join()
            }
            Log.d("AsyncComparison", "coroutines finished in $timeSpent")
        }
    }
}

sealed class AsyncComparisonUIState {
//    data object Initial : AsyncComparisonUIState()
    data class DeferredUpdate(val progress: Float, val no: Int) : AsyncComparisonUIState()
    data class CoroutineUpdate(val progress: Float, val no: Int) : AsyncComparisonUIState()
}
