package nick.mirosh.androidsamples.ui.coroutines.async

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import nick.mirosh.androidsamples.ui.coroutines.runUpdatesIn

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
            deferred1 = async {
                runUpdatesIn(_deferred1Flow)
            }
            deferred2 = async {
                runUpdatesIn(_deferred2Flow)
            }
            awaitAll(deferred1!!, deferred2!!)
        }
    }


    fun cancelAsync1() {
        deferred1?.cancel()
    }

    fun cancelAsync2() {
        deferred2?.cancel()
    }

    fun cancelCoroutine1() {
        Log.d("AsyncComparison", "cancelling coroutine 1")
        job1?.cancel()
    }

    fun cancelCoroutine2() {
        Log.d("AsyncComparison", "cancelling coroutine 2")
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
            job1 = launch {
                runUpdatesIn(_job1flow)
            }

            job2 = launch {
                runUpdatesIn(_job2flow)
            }
            joinAll(job1!!, job2!!)
        }
    }
}
