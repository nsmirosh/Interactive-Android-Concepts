package nick.mirosh.androidsamples.ui.coroutines.cancellation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext


const val TAG = "CancellationViewModel"

class CancellationViewModel : ViewModel() {

    fun launchWithSupervisor() {
        CoroutineScope(Dispatchers.Main).launch {
            supervisorScope {
                try {
                    launch {

                    }
                } catch (e: Exception) {
                    Log.d("AsyncComparison", "Exception caught: $e")
                }
            }
        }
    }

    fun unCooperativeCancellation() {
        val startTime = System.currentTimeMillis()
        CoroutineScope(Dispatchers.Main).launch {
            val job = launch(Dispatchers.Default) {
                var nextPrintTime = startTime
                var i = 0
                while (i < 10) { // computation loop, just wastes CPU
                    // print a message twice a second
                    if (System.currentTimeMillis() >= nextPrintTime) {
                        println("job: I'm sleeping ${i++} ...")
                        nextPrintTime += 500L
                    }
                }
            }
            delay(1300L) // delay a bit
            println("main: I'm tired of waiting!")
            job.cancelAndJoin() // cancels the job and waits for its completion
            println("main: Now I can quit.")
        }
    }


    fun catchingAndNotReThrowingException() {
        CoroutineScope(Dispatchers.Main).launch {
            val job = launch(Dispatchers.Default) {
                repeat(10) { i ->
                    try {
                        // print a message twice a second
                        println("job: I'm sleeping $i ...")
                        delay(500)
                    } catch (e: Exception) {
                        println(e.message)
                    }
                }
            }
            delay(1300L) // delay a bit
            println("main: I'm tired of waiting!")
            job.cancelAndJoin() // cancels the job and waits for its completion
            println("main: Now I can quit.")
        }
    }

    fun checkCancellationWithIsActive() {
        CoroutineScope(Dispatchers.Main).launch {
            val startTime = System.currentTimeMillis()
            val job = launch(Dispatchers.Default) {
                var nextPrintTime = startTime
                var i = 0
                while (isActive) { // cancellable computation loop
                    // print a message twice a second
                    if (System.currentTimeMillis() >= nextPrintTime) {
                        println("job: I'm sleeping ${i++} ...")
                        nextPrintTime += 500L
                    }
                }
            }
            delay(2500L) // delay a bit
            println("main: I'm tired of waiting!")
            job.cancelAndJoin() // cancels the job and waits for its completion
            println("main: Now I can quit.")
        }
    }


    fun closingResourcesWithFinally() {
        CoroutineScope(Dispatchers.Main).launch {
            val job = launch {
                try {
                    repeat(1000) { i ->
                        println("job: I'm sleeping $i ...")
                        delay(500L)
                    }
                } finally {
                    println("job: I'm running finally")
                }
            }
            delay(1300L) // delay a bit
            println("main: I'm tired of waiting!")
            job.cancelAndJoin() // cancels the job and waits for its completion
            println("main: Now I can quit.")
        }
    }

    fun uncooperativeSeparateCancelAndJoin() {
        CoroutineScope(Dispatchers.IO).launch {
            val job = launch {
                repeat(5) { i ->
                    println("job: I'm sleeping $i ...")
                    Thread.sleep(500L)
                }
            }
            delay(1100L)
            println("canceling job!")
            job.cancel()
            println("job cancelled")
            job.join()
            println("finally quitting!")
        }
    }

    fun cancelAndJoinCooperativeWithIsActive() {
        CoroutineScope(Dispatchers.IO).launch {
            val job = launch {
                while (isActive) {
                    repeat(5) { i ->
                        println("job: I'm sleeping $i ...")
                        Thread.sleep(500L)
                    }
                }
            }
            delay(1100L)
            println("canceling job!")
            job.cancel()
            println("job cancelled")
            job.join()
            println("finally quitting!")
        }
    }


    //Exception propagation

    private suspend fun riskyOperationWithLaunch() {
        withContext(Dispatchers.IO) {
            // Simulate a task that might fail
            throw Exception("Something went wrong in launch")
        }
    }

    fun propagationOfExceptionsInLaunch() {
        viewModelScope.launch {
            try {
                riskyOperationWithLaunch()
            } catch (e: Exception) {
                Log.d(TAG, "Launch error: ${e.message}")
            }
        }
    }

    private fun riskyOperationWithAsync(): Deferred<String> {
        return viewModelScope.async(Dispatchers.IO) {
            Log.d(TAG, "running async")
            throw Exception("Something went wrong in async")
        }
    }

    fun propagationOfExceptionsInAsync() {
        viewModelScope.launch {
            try {
                val result = riskyOperationWithAsync()
                Log.d(TAG, "Delaying to see what will happen to the exception")
                delay(2000L)
                result.await()
                // Update UI or state with the result
                Log.d(TAG, "Result from async: $result")
            } catch (e: Exception) {
                // Handle the exception
                Log.d(TAG, "Async error: ${e.message}")
            }
        }
    }

    fun separateJob() {
        CoroutineScope(Dispatchers.IO).launch {
            val request = launch {
                // it spawns two other jobs
                launch(Job()) {
                    Log.d(TAG, "job1: I run in my own Job and execute independently!")
                    delay(1000)
                    Log.d(TAG, "job1: I am not affected by cancellation of the request")
                }
                // and the other inherits the parent context
                launch {
                    delay(100)
                    Log.d(TAG, "job2: I am a child of the request coroutine")
                    delay(1000)
                    Log.d(
                        TAG,
                        "job2: I will not execute this line if my parent request is cancelled"
                    )
                }
            }
            delay(500)
            request.cancel() // cancel processing of the request
            Log.d(TAG, "main: Who has survived request cancellation?")
            delay(1000) // delay the main thread for a second to see what happens
        }
    }

    fun separateJobViewModelScopes() {
        viewModelScope.launch(Dispatchers.IO) {
            val request = launch {
                // it spawns two other jobs
                viewModelScope.launch {
                    Log.d(TAG, "job1: I run in my own Job and execute independently!")
                    delay(1000)
                    Log.d(TAG, "job1: I am not affected by cancellation of the request")
                }
                // and the other inherits the parent context
                launch {
                    delay(100)
                    Log.d(TAG, "job2: I am a child of the request coroutine")
                    delay(1000)
                    Log.d(
                        TAG,
                        "job2: I will not execute this line if my parent request is cancelled"
                    )
                }
            }
            delay(500)
            request.cancel() // cancel processing of the request
            Log.d(TAG, "main: Who has survived request cancellation?")
            delay(1000) // delay the main thread for a second to see what happens
        }
    }

    fun coroutineExceptionHandler() {
        val handler = CoroutineExceptionHandler { _, exception ->
            Log.d(TAG, "CoroutineExceptionHandler got $exception")
        }
        val job = GlobalScope.launch(handler) { // root coroutine, running in GlobalScope
            throw AssertionError()
        }
        val deferred = GlobalScope.async(handler) { // also root, but async instead of launch
            throw ArithmeticException() // Nothing will be printed, relying on user to call deferred.await()
        }

        viewModelScope.launch(handler) { // coroutineExceptionHandler is not propagated to viewModelScope
            joinAll(job, deferred)
//            deferred.await()
        }
    }

    fun cancellingWithCancelAndParentRunning() {
        CoroutineScope(Dispatchers.IO).launch {
            val job = launch {
                val child = launch {
                    try {
                        delay(Long.MAX_VALUE)
                    } finally {
                        Log.d(TAG, "Child is cancelled")
                    }
                }
                delay(2000L)
                Log.d(TAG, "Cancelling child")
                child.cancel()
                child.join()
                delay(2000L)
                Log.d(TAG, "Parent is not cancelled")
            }
            job.join()
        }
    }

    fun cancellingWithCancellationExceptionAndParentRunning() {
        CoroutineScope(Dispatchers.IO).launch {
            val job = launch {
                val child = launch {
                    Log.d(TAG, "Running child coroutine")
                    delay(2000L)
                    Log.d(TAG, "Cancelling child")
                    throw CancellationException()
                }
                child.join()
                if (!child.isActive) {
                    Log.d(TAG, "Child is cancelled")
                }
                delay(2000L)
                Log.d(TAG, "Parent is not cancelled")
            }
            job.join()
        }
    }
}