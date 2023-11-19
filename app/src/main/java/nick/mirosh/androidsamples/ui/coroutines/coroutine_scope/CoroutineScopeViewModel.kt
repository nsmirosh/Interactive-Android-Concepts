package nick.mirosh.androidsamples.ui.coroutines.coroutine_scope

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nick.mirosh.androidsamples.ui.coroutines.runUpdatesIn

const val updateSpeed = 300L

class CoroutineScopeViewModel : ViewModel() {


    //TODO refactor to use 1 flow
    private val _task1Flow = MutableStateFlow(0f)
    val task1Flow = _task1Flow.asStateFlow()

    private val _task2Flow = MutableStateFlow(0f)
    val task2Flow = _task2Flow.asStateFlow()

    private val _task3Flow = MutableStateFlow(0f)
    val task3Flow = _task3Flow.asStateFlow()

    private val _task4Flow = MutableStateFlow(0f)
    val task4Flow = _task4Flow.asStateFlow()

    fun onCoroutineScopeClicked() {
        viewModelScope.launch {
            launch {
                delay(200L)
                println("Task 1")
                runUpdatesIn(_task1Flow, updateSpeed)
            }

            coroutineScope {
                launch {
                    delay(500L)
                    println("Task 2")
                    runUpdatesIn(_task2Flow, updateSpeed)
                }
                delay(100L)
                println("Task 3")
                runUpdatesIn(_task3Flow, updateSpeed)
            }

            println("Task 4")
            runUpdatesIn(_task4Flow, updateSpeed)
        }
    }
}