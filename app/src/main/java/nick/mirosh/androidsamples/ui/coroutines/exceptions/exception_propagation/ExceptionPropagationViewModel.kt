package nick.mirosh.androidsamples.ui.coroutines.exceptions.exception_propagation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import nick.mirosh.androidsamples.ui.coroutines.runUpdatesIn
import kotlin.coroutines.cancellation.CancellationException

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
                    Log.d(TAG, "Counter: $counter")
                    counter++
                    Thread.sleep(100)
                }
            }
            delay(1000)
            Log.d(TAG, "Requesting cancellation")
            job.cancel()
            job.join()
            Log.d(TAG, "Cancellation requested")
        }
    }

    fun challenge() {
        try {
            runBlocking {
                val job = launch {
                    launch {
                        throw ArithmeticException("Division by zero")
                    }
                    launch {
                        delay(100L)
                        throw IndexOutOfBoundsException("Index error")
                    }
                }

//                job.join()
                Log.d(TAG, "Completed runBlocking scope")
            }
        } catch (e: Exception) {
            Log.d(TAG, "outer catch with ${e.message}")
        }
    }


    fun simpleChallenge() {
        viewModelScope.launch {
            launch {
                Log.d(TAG, "child1")
                delay(200)
                Log.d(TAG, "child1 working")
                throw RuntimeException()
            }

            launch {
                Log.d(TAG, "child2")
                delay(300)
                Log.d(TAG, "child2 finishing")
            }

            launch {
                delay(100)
                throw CancellationException()
            }
        }
    }

    fun challenge2() {
        try {
            runBlocking {
                val grandparentJob = launch {
                    Log.d(TAG, "Grandparent is started")

                    val parentJob = launch {
                        val childJob = launch {
                            Log.d(TAG, "child is started")
                            delay(100)
                            throw CancellationException()
                        }

                        val childJob2 = launch {
                            Log.d(TAG, "child2 is started")
                            delay(200)
                            throw RuntimeException()
                        }

                        val childJob3 = launch {
                            delay(300)
                            Log.d(TAG, "child3 is started")
                        }

                        try {
                            joinAll(childJob, childJob2, childJob3)
                        } catch (e: Exception) {
                            Log.d(TAG, "Caught in parent: $e with ${e.message}")
                        }
                    }
                    try {
                        parentJob.join()
                    } catch (e: Exception) {
                        Log.d(TAG, "Caught in grandparent: $e")
                    }

                    Log.d(TAG, "Grandparent is completing")
                }

                grandparentJob.join()
                Log.d(TAG, "Completed runBlocking scope")
            }
        } catch (e: Exception) {

            Log.d(TAG, "caught outside of runBlocking")
        }
    }
}