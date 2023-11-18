package nick.mirosh.androidsamples.ui.coroutines.async

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class AsyncComparisonViewModel : ViewModel() {

    private val _deferred1Flow = MutableStateFlow(0f)
    val deferred1Flow = _deferred1Flow.asStateFlow()

    private val _deferred2Flow = MutableStateFlow(0f)
    val deferred2Flow = _deferred2Flow.asStateFlow()

    private val _job1flow = MutableStateFlow(0f)
    val job1flow = _job1flow.asStateFlow()

    private val _job2flow = MutableStateFlow(0f)
    val job2flow = _job2flow.asStateFlow()

    private var deferred1: Deferred<Unit>? = null
    private var deferred2: Deferred<Unit>? = null
    private var job1: Job? = null
    private var job2: Job? = null

    fun launchAsyncs() {
        viewModelScope.launch {
            try {
                deferred1 = async {
                    runUpdatesIn(_deferred1Flow)
                }
            } catch (e: Exception) {
                Log.e("AsyncComparison", "Exception in one of the async jobs: ${e.message}")
            }
            deferred2 = async {
                runUpdatesIn(_deferred2Flow)
            }
            awaitAll(deferred1!!, deferred2!!)
        }
    }

    fun launchAsyncsWithSupervisor() {
        viewModelScope.launch {
            supervisorScope {
                deferred1 = async {
                    runUpdatesIn(_deferred1Flow)
                }
                deferred2 = async {
                    runUpdatesIn(_deferred2Flow)
                }
                val result1 = deferred1?.await()
                val result2 = deferred2?.await()
                Log.d("AsyncComparison", "launchAsyncsWithSupervisor: deffered1 result: $result1")
                Log.d("AsyncComparison", "launchAsyncsWithSupervisor: deffered2 result: $result2")
//                awaitAll(deferred1!!, deferred2!!)
            }
        }
    }

    private suspend fun runAsyncs() {
        //TODO watch https://www.youtube.com/watch?v=w0kfnydnFWI&ab_channel=JetBrains
        coroutineScope {
            try {
                deferred1 = async {
                    runUpdatesIn(_deferred1Flow)
                }
                deferred2 = async {
                    runUpdatesIn(_deferred2Flow)
                }
                awaitAll(deferred1!!, deferred2!!)
            } catch (e: Exception) {
                Log.e("AsyncComparison", "Exception in one of the async jobs: ${e.message}")
            }
        }
    }

    fun cancelAsync1() {
        deferred1?.cancel()
    }

    fun cancelAsync2() {
        deferred2?.cancel()
    }

    fun cancelCoroutine1() {
        job1?.cancel()
    }

    fun cancelCoroutine2() {
        job2?.cancel()
    }

    fun clear() {
        job1?.cancel()
        job2?.cancel()
        deferred1?.cancel()
        deferred2?.cancel()
        _deferred1Flow.value = 0f
        _deferred2Flow.value = 0f
        _job1flow.value = 0f
        _job2flow.value = 0f
    }

    fun launchCoroutines() {
        viewModelScope.launch {
            job1 = viewModelScope.launch {
                runUpdatesIn(_job1flow)
            }
            job2 = viewModelScope.launch {
                runUpdatesIn(_job2flow)
            }
        }
    }

    private suspend fun runUpdatesIn(flow: MutableStateFlow<Float>) {
        var counter = 0
        while (counter <= 100) {
            delay(500)
            flow.value = counter / 100f
            counter += 10
        }
    }

    fun runMyChallenge() {
        viewModelScope.launch {
            launch {
                delay(200L)
                println("Task 1")
            }

            coroutineScope {
                launch {
                    delay(500L)
                    println("Task 2")
                }
                delay(100L)
                println("Task 3")
            }

            println("Task 4")
        }
    }
}