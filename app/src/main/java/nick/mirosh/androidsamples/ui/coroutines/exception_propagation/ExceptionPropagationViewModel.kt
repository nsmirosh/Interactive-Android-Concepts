package nick.mirosh.androidsamples.ui.coroutines.exception_propagation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import nick.mirosh.androidsamples.ui.coroutines.runUpdatesIn

const val TAG = "ExceptionPropagationViewModel"

class ExceptionPropagationViewModel : ViewModel() {


    private val _childProgressUpdate = MutableStateFlow(0f)
    val childProgressUpdate = _childProgressUpdate

    private val _parentProgressUpdate = MutableStateFlow(0f)
    val parentProgressUpdate = _parentProgressUpdate

    private val _grandParentProgressUpdate = MutableStateFlow(0f)
    val grandParentProgressUpdate = _grandParentProgressUpdate

    fun start() {
        // https://medium.com/@manuaravindpta/exception-handling-in-kotlin-coroutine-34ef9bee3f8c#:~:text=The%20output%20is%20as%20follows.&text=We%20found%20that%20the%20exception,and%20can%20continue%20to%20execute.
        val handler = CoroutineExceptionHandler { _, exception ->
            Log.d(
                TAG,
                "catching final exception in GRAND GRAND parent coroutine with message: ${exception.message}"
            )
        }
        viewModelScope
            .launch(
                Dispatchers.IO + handler
            ) {
                val grandParentJob = launch {
                    try {
                        val parentJob = launch {
                            try {
                                val childJob = launchChild() 
                                Log.d(TAG, "Waiting for the child coroutine to finish")
                                childJob.join()
                            } catch (e: Exception) {
                                Log.d(
                                    TAG,
                                    "catching exception in parent coroutine with message: ${e.message}"
                                )
                            }
                            runUpdatesIn(_parentProgressUpdate)
                        }
                        parentJob.join()
                    } catch (e: Exception) {
                        Log.d(
                            TAG,
                            "catching exception in GRAND parent coroutine with message: ${e.message}"
                        )
                    }
                    runUpdatesIn(_grandParentProgressUpdate)
                }
            }
    }
    
    
    private fun CoroutineScope.launchChild() = launch {
            delay(2000)
            Log.d(TAG, "throwing exception in child coroutine")
            throw UnsupportedOperationException("Exception in child coroutine")
    }


    fun main() {
        runBlocking {
            val job = GlobalScope.launch {
                var counter = 0
                while (true) {
                    println("Counter: $counter")
                    counter++
                    Thread.sleep(100)
                }
            }
            delay(1000)
            println("Requesting cancellation")
            job.cancel()
            job.join()
            println("Cancellation requested")
        }
    }
}
