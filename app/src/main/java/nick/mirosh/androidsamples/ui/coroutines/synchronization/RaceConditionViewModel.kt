package nick.mirosh.androidsamples.ui.coroutines.synchronization

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import nick.mirosh.androidsamples.ui.coroutines.cooperative_coroutine.TAG

class RaceConditionViewModel: ViewModel() {

    // Shared mutable state
    //https://jeremymanson.blogspot.com/2008/11/what-volatile-means-in-java.html
   //threads can cache values they work with in their own local memory, such as CPU registers
    // or caches, for performance reasons. This caching mechanism can lead to a situation where the
    // value of a variable updated by one thread is not immediately visible to other threads,
    // because each thread may be working with a cached copy of the variable stored in its own local
    // memory. The result is a lack of consistency in the observed value of the variable
    // across different threads.

    // Accessing a volatile variable is more expensive than accessing a non-volatile variable
    // but less expensive than executing a synchronized block. Therefore,
    // it can be a good option when reading and writing a variable atomically without locking.
    @Volatile
    var counter = 0

    //    var counter = AtomicInteger()
    @OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
    fun runRaceCondition() {
        viewModelScope.launch {
            val listOfJobs = mutableListOf<Deferred<Unit>>()
            repeat(10) {
                listOfJobs.add(async(newSingleThreadContext("CounterThread$it")) {
                    incrementCounter()
                })
            }
            listOfJobs.awaitAll()

            Log.d(TAG, "Expected value: 10000")
            Log.d(TAG, "Actual value: $counter")
        }
    }

    private fun incrementCounter() {
        Log.d(TAG, "thread: ${Thread.currentThread().name}")
        repeat(1000) {
            counter += 1
        }
    }

    val mutex = Mutex()
    private suspend fun incrementCounterMutex() {
        Log.d(TAG, "thread: ${Thread.currentThread().name}")
        repeat(1000) {
            mutex.withLock {
                counter += 1
            }
        }
    }

    val lock = Any()
    private suspend fun incrementCounterSynchronizedOnObject() {
        Log.d(TAG, "thread: ${Thread.currentThread().name}")
        repeat(1000) {
            synchronized(lock) {
                counter += 1
            }
        }
    }

    private fun incrementCounterSynchronizedOnThis() {
        Log.d(TAG, "thread: ${Thread.currentThread().name}")
        repeat(1000) {
            synchronized(this) {
                counter += 1
            }
        }
    }

    //TODO leaern what is actor
    fun CoroutineScope.counterActor() = actor<Int> {
        var count = 0
        for (msg in channel) {
            count += msg
        }
    }

    //TODO learn channel
    val channel = Channel<Int>()

    fun learnChannel() = runBlocking {
// Coroutine 1
        launch {
            for (x in 1..5) channel.send(x)
        }

// Coroutine 2
        launch {
            repeat(5) { println(channel.receive()) }
        }
    }
}