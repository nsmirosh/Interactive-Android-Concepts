package nick.mirosh.androidsamples.ui.coroutines.flows

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.launch

private const val TAG = "FlowViewModel"

class FlowViewModel : ViewModel() {

    fun runFlow() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d(TAG, "right before executing a flow: ${Thread.currentThread().name}")
            myFlow()
                .onEach {
                    delay(500)
                    Log.d(TAG, "before applying Dispatcher.IO: ${Thread.currentThread().name}")
                }
                .flowOn(Dispatchers.Main)
                .onEach {
                    delay(500)
                    Log.d(TAG, "after applying Dispatcher.IO: ${Thread.currentThread().name}")
                }
                .map {
                    "$it"
                }
                .flowOn(Dispatchers.IO)
                .onEach {
                    delay(500)
                    Log.d(TAG, "after applying Dispatcher.Default: ${Thread.currentThread().name}")
                }
                .collect {
                    Log.d(TAG, "collect: ${Thread.currentThread().name}")
                }
        }
    }


    private fun myFlow() = flow {
//        for (i in 1..3) {
//            delay(100)
//            emit(i)
//        }
        emit(1)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun transformLatest() {
        val stuff = flow {
            emit("a")
            delay(100)
            emit("b")
        }.transformLatest { value ->
            emit(value)
            delay(150)
            emit(value + "_last")
        }
        viewModelScope.launch {
            stuff.collect {
                Log.d(TAG, "transformLatest: $it")
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun flatMapLatest() {
        viewModelScope.launch {
            myFlow().flatMapLatest {
                flow {
                    for (i in 1..3) {
                        delay(100)
                        emit(i)
                    }
                }
            }.collect {
                Log.d(TAG, "flatMapLatest: $it")
            }
        }
    }
}
