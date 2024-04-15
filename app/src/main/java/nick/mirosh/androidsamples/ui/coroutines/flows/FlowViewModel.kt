package nick.mirosh.androidsamples.ui.coroutines.flows

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
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


    private fun myFlow2() = flow {
        for (i in 0..10) {
            emit(i)
            Log.d(TAG, "myFlow: emitting $i")
            delay(100)
        }
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

    data class Message2(val text: String)
//        override fun equals(other: Any?): Boolean {
//            return other is Message2 && other === this
//        }

//        override fun hashCode(): Int {
//            return 34
//        }

//    class Message(val text: String) {
//        override fun equals(other: Any?): Boolean {
//            Log.d(TAG, "equals called")
//            return other is Message
//        }

//        override fun hashCode(): Int {
//            return 34
//        }

    //    }
    private fun myFlow() = flow {

        emit(Message2("hello"))
        delay(100)
        emit(Message2("hello"))
    }

    data class Message(val text: String) {
        var time = System.currentTimeMillis()
    }

    fun distinctUntilChanged() {
        val message1 = Message("Fun")
        //100 ms delay
        val message2 = Message("Fun")
        println("${message1.hashCode() == message2.hashCode()}")
        println("${message1 == message2}")
        println("${message1.time == message2.time}")

//        val message = Message2("hello")
//        val message2 = Message2("hello")
//        Log.d(TAG, "myFlow: ${message.hashCode()}")
//        Log.d(TAG, "myFlow: ${message2.hashCode()}")
//        Log.d(TAG, "myFlow: ${message == message2}")
//        Log.d(TAG, "myFlow: ${message === message2}")
//
//        val message3 = Message("hello")
//        val message4 = Message("hello")
//        Log.d(TAG, "myFlow: ${message3.hashCode()}")
//        Log.d(TAG, "myFlow: ${message4.hashCode()}")
//        Log.d(TAG, "myFlow: ${message3 == message4}")
//        Log.d(TAG, "myFlow: ${message3 === message4}")
        //https://stackoverflow.com/questions/4178997/how-default-equals-and-hashcode-will-work-for-my-classes

//        viewModelScope.launch {
//            myFlow()
//                .distinctUntilChanged()
//                .onCompletion { Log.d(TAG, "Completed") }
//                .collect { Log.d(TAG, "A: $it") }
//        }
        viewModelScope.launch {
            myFlow()
                .distinctUntilChanged { new, old ->
                    new == old && new.hashCode() == old.hashCode()
                }
                .collect { Log.d(TAG, "B : $it") }
        }
//        viewModelScope.launch {
//            myFlow()
//                .stateIn(scope = viewModelScope)
//                .onCompletion { Log.d(TAG, "Completed") }
//                .collect { Log.d(TAG, "C: $it") }
//        }
    }


//    @OptIn(FlowPreview::class)
//    fun debounce() {
//        viewModelScope.launch {
//
//            val startTime = System.currentTimeMillis()
//            Log.d(TAG, "debounce: start = $startTime")
//
//
//            var firstEmission = true
//
//            myFlow()
//                .debounce {
//                    if (firstEmission) {
//                        firstEmission = false
//                        0L
//                    } else 150L
//                }.flatMapMerge{
//
//                }
//                .collect {
//                    Log.d(
//                        TAG,
//                        "debounce: emission with value $it =  ${System.currentTimeMillis() - startTime}"
//                    )
//                }
//        }
//
//    }
}


