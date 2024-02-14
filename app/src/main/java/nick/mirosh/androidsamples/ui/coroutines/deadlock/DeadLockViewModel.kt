package nick.mirosh.androidsamples.ui.coroutines.deadlock

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout

private const val TAG = "DeadLockViewModel"

class DeadLockViewModel : ViewModel() {

    fun runLogicalDeadlock() {
        viewModelScope.launch {
            try {
                withTimeout(5000L) {
                    deadLock()
                }
            } catch (e: Exception) {
                Log.e(TAG, "Caught exception: ${e.message}")
            }
        }
    }

    private suspend fun deadLock() {
        coroutineScope {
            var deferred2: Deferred<String>? = null
            val deferred1 = async {
                Log.d(TAG, "runDeadlock: awaiting task 2 to complete")
                val result2 = deferred2?.await()
                Log.d(TAG, "task 1 completed")
                "Result of task 1 depends on task 2: $result2"
            }
            deferred2 = async {

                Log.d(TAG, "runDeadlock: awaiting task 1 to complete")
                val result1 = deferred1.await() // This creates a circular dependency
                Log.d(TAG, "task 2 completed")
                "Result of task 2 depends on task 1: $result1"
            }
            Log.d(TAG, "runDeadlock: waiting for tasks to complete")
            deferred1.await()
            deferred2.await()
        }
    }

    fun runActualDeadlock() {

        runBlocking {

            var deferred2: Deferred<String>? = null

            val deferred1 = async {
                Log.d(TAG, "runDeadlock: awaiting task 2 to complete")
                val result2 = deferred2?.await()
                Log.d(TAG, "task 1 completed")
                "Result of task 1 depends on task 2: $result2"
            }

            deferred2 = async {
                Log.d(TAG, "awaiting task 1 to complete")
                val result1 = deferred1.await()
                Log.d(TAG, "task 2 completed")
                "Result of task 2 depends on task 1: $result1"
            }
            try {
                Log.d(TAG, "runDeadlock: waiting for tasks to complete")
                deferred1.await()
                deferred2.await()
            } catch (e: Exception) {
                Log.e(TAG, "Caught exception: ${e.message}")
            }
        }
    }
}