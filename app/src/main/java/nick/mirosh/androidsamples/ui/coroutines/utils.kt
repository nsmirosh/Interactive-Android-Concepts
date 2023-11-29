package nick.mirosh.androidsamples.ui.coroutines

import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import java.io.PrintWriter
import java.io.StringWriter

suspend fun runUpdatesIn(
    flow: MutableStateFlow<Float>,
    delayMs: Long = 500L,
) {
    var counter = 0
    while (counter <= 100 ) {
        delay(delayMs)
        flow.value = counter / 100f
        counter += 10
    }
}

fun Throwable.logStackTrace(tag: String) {
    val sw = StringWriter()
    this.printStackTrace(PrintWriter(sw))
    val exceptionAsString = sw.toString()
    Log.e(tag, exceptionAsString)
}
