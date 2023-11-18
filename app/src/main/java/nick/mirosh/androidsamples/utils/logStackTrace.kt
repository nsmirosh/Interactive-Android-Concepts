package nick.mirosh.androidsamples.utils

import android.util.Log
import java.io.PrintWriter
import java.io.StringWriter

fun Throwable.logStackTrace(tag: String) {
    val sw = StringWriter()
    this.printStackTrace(PrintWriter(sw))
    val exceptionAsString = sw.toString()
    Log.e(tag, exceptionAsString)
}