package nick.mirosh.androidsamples.ui.coroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow

suspend fun runUpdatesIn(flow: MutableStateFlow<Float>, delayMs: Long = 500L) {
    var counter = 0
    while (counter <= 100) {
        delay(delayMs)
        flow.value = counter / 100f
        counter += 10
    }
}