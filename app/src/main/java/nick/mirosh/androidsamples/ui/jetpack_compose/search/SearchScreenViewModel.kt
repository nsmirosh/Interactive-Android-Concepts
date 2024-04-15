package nick.mirosh.androidsamples.ui.jetpack_compose.search

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.merge
import nick.mirosh.androidsamples.ui.coroutines.cooperative_coroutine.TAG

class SearchScreenViewModel() : ViewModel() {
    var _searchQuery = MutableStateFlow("Initial query")

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    var data2: Flow<String> = merge(
        backendFlow("Initial flow"),
        _searchQuery.drop(1)
            .debounce(2000)
            .flatMapLatest { query ->
                backendFlow(query)
            })

    var data: Flow<String> = _searchQuery
        .debounceWithInitialCall(
            defaultValue = "Initial query",
            requestFunction = {
                backendFlow(it)
            }
        )

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    private fun backendFlow(params: String) = flow {
        emit(params)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    var data3: Flow<String> = _searchQuery
        .throttleFirst(2000)
        .flatMapLatest { query ->
            backendFlow(query)
        }


}

//fun <T> Flow<T>.throttleFirst(windowDuration: Long): Flow<T> {
//    var job: Job = Job().apply { complete() }
//
//    return onCompletion { job.cancel() }.run {
//        flow {
//            coroutineScope {
//                collect { value ->
//                    if (!job.isActive) {
//                        emit(value)
//                        job = launch { delay(windowDuration) }
//                    }
//                }
//            }
//        }
//    }
//}

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
fun <T, R> Flow<T>.debounceWithInitialCall(
    defaultValue: T,
    requestFunction: (T) -> Flow<R>,
    debounceTime: Long = 2000L
): Flow<R> =
    merge(
        requestFunction(defaultValue),
        drop(1)
            .debounce(debounceTime)
            .flatMapLatest {
                requestFunction(it)
            }
    )


fun <T> Flow<T>.throttleFirst(windowDuration: Long): Flow<T> = flow {
    var windowStartTime = System.currentTimeMillis()
    var emitted = false

    var lastEmittedValue: T? = null
    collect { value ->
        val currentTime = System.currentTimeMillis()
        val delta = currentTime - windowStartTime
        if (delta >= windowDuration) {
            windowStartTime += delta / windowDuration * windowDuration
            emitted = false
            if (lastEmittedValue != null && lastEmittedValue != value) {
                Log.d(TAG, "throttleFirst: emitting value because we're idle")
                emit(value)
                lastEmittedValue = value
                emitted = true
            }
        }
        if (!emitted) {
            emit(value)
            lastEmittedValue = value
            emitted = true
        }
    }
}