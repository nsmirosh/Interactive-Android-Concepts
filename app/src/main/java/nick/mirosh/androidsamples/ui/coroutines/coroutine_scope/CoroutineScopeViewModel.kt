package nick.mirosh.androidsamples.ui.coroutines.coroutine_scope

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class CoroutineScopeViewModel : ViewModel() {

    private val _task1Flow = MutableStateFlow(Pair(1f, "Task 1 : 5.0s"))
    val task1Flow = _task1Flow.asStateFlow()

    private val _task2Flow = MutableStateFlow(Pair(1f, "Task 2 : 7.0s"))
    val task2Flow = _task2Flow.asStateFlow()

    private val _task3Flow = MutableStateFlow(Pair(1f, "Task 3 : 3.0s"))
    val task3Flow = _task3Flow.asStateFlow()

    private val _task4Flow = MutableStateFlow(Pair(1f, "Task 4: 1.0s"))
    val task4Flow = _task4Flow.asStateFlow()

    fun onCoroutineScopeClicked() {
        viewModelScope.launch {
            launch {
                sendTimeUpdatesInto(_task1Flow, 5000L, 1)
                println("Task 1")
            }

            coroutineScope {
                launch {
                    sendTimeUpdatesInto(_task2Flow, 7000L, 2)
                    println("Task 2")
                }
                sendTimeUpdatesInto(_task3Flow, 3000L, 3)
                println("Task 3")
            }

            sendTimeUpdatesInto(_task4Flow, 2000L, 4)
            println("Task 4")
        }
    }

    private suspend fun sendTimeUpdatesInto(
        flow: MutableStateFlow<Pair<Float, String>>,
        delay: Long,
        taskNo: Int = 0
    ) {
        for (i in (delay / 100).toInt() downTo 0) {
            Log.d(
                "CoroutineScopeViewModel",
                "sendTimeUpdatesInto: $${i.toFloat() / (delay / 100)} i = $i"
            )
            flow.value = Pair(i.toFloat() / (delay / 100), "Task $taskNo : ${i / 10},${i % 10}s")
            delay(100)
        }
    }
}