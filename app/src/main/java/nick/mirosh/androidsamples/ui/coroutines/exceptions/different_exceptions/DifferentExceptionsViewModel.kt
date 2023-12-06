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

    private val _task1Flow = MutableStateFlow(ProgressUpdate(label = "4.0s"))
    val task1Flow = _task1Flow.asStateFlow()

    private val _task2Flow = MutableStateFlow(ProgressUpdate(label = "6.0s"))
    val task2Flow = _task2Flow.asStateFlow()

    private val _task3Flow = MutableStateFlow(ProgressUpdate(label = "2.0s"))
    val task3Flow = _task3Flow.asStateFlow()

    private val _firstCoroutineCancelled = MutableStateFlow(false)
    val firstCoroutineCancelled = _firstCoroutineCancelled.asStateFlow()

    private val _secondCoroutineCancelled = MutableStateFlow(false)
    val secondCoroutineCancelled = _secondCoroutineCancelled.asStateFlow()

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
                    runElapsingUpdates(_task1Flow, 6000L)
                    Log.d(TAG, "child1 working")
                    throw RuntimeException()
                }

                val job2 = launch {
                    Log.d(TAG, "child2")
                    runElapsingUpdates(_task2Flow, 9000L)
                    Log.d(TAG, "child2 finishing")
                }
                val job3 = launch {
                    runElapsingUpdates(_task3Flow, 3000L)
                    _thirdCoroutineCancelled.value = true
                    throw CancellationException()
                }
                joinAll(job1, job2, job3)
            } catch (e: Exception) {
                _firstCoroutineCancelled.value = true
                _secondCoroutineCancelled.value = true
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