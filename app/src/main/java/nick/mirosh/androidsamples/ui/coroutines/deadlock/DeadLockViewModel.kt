package nick.mirosh.androidsamples.ui.coroutines.deadlock

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withTimeout
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

private const val TAG = "DeadLockViewModel"

class DeadLockViewModel : ViewModel() {
    fun runLogicalDeadlock() {
        viewModelScope.launch {
            try {
                withTimeout(5000L) {
                    deadLock()
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exception: ${e.message}")
            }
        }
    }
    fun runActualDeadlock() {
        runBlocking {
            deadLock()
        }
    }

    private suspend fun deadLock() {
        coroutineScope {
            var deferred2: Deferred<String>? = null
            val deferred1 = async {
                deferred2?.await()
                "Result of task 1:"
            }
            deferred2 = async {
                deferred1.await()
                "Result of task 2"
            }
            deferred1.await()
            deferred2.await()
        }
    }
}