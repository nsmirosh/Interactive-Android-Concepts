package nick.mirosh.androidsamples.ui.coroutines.exceptions.different_exceptions

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import nick.mirosh.androidsamples.ui.coroutines.logStackTrace
import nick.mirosh.androidsamples.ui.coroutines.runElapsingUpdates

const val TAG = "DifferentExceptionsViewModel"

class DifferentExceptionsViewModel : ViewModel() {

    private val _task1Flow = MutableStateFlow(ProgressUpdate(label = "12.0s"))
    val task1Flow = _task1Flow.asStateFlow()

    private val _task2Flow = MutableStateFlow(ProgressUpdate(label = "18.0s"))
    val task2Flow = _task2Flow.asStateFlow()

    private val _task3Flow = MutableStateFlow(ProgressUpdate(label = "5.0s"))
    val task3Flow = _task3Flow.asStateFlow()

    private val _firstCoroutineCancelled = MutableStateFlow(false)
    val firstCoroutineCancelled = _firstCoroutineCancelled.asStateFlow()

    private val _thirdCoroutineCancelled = MutableStateFlow(false)
    val thirdCoroutineCancelled = _thirdCoroutineCancelled.asStateFlow()

    fun simpleChallenge() {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            throwable.logStackTrace(TAG)
        }
        //An exception handler has to be added if we don't want the application to crash
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            try {
                val job1 = launch {
                    Log.d(TAG, "child1")
                    runElapsingUpdates(_task1Flow, 12000L)
                    Log.d(TAG, "child1 working")
                    throw RuntimeException()
                }

                val job2 = launch {
                    Log.d(TAG, "child2")
                    runElapsingUpdates(_task2Flow, 18000L)
                    Log.d(TAG, "child2 finishing")
                }
                val job3 = launch {
                    runElapsingUpdates(_task3Flow, 5000L)
                    _thirdCoroutineCancelled.emit(true)
                    throw CancellationException()
                }
                joinAll(job1, job2, job3)
            } catch (e: Exception) {
                _firstCoroutineCancelled.emit(true)
                Log.d(TAG, "exception caught")
            }
        }
    }
}


data class ProgressUpdate(
    val progress: Float = 1f,
    val label: String = "",
    val failed: Boolean = false,
)