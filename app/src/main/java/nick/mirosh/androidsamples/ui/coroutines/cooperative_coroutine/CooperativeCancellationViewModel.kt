package nick.mirosh.androidsamples.ui.coroutines.cooperative_coroutine

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

const val TAG = "CooperativeCancellationViewModel"

class CooperativeCancellationViewModel: ViewModel() {
    fun main() {
        runBlocking {
            val job = GlobalScope.launch {
                var counter = 0
                while (true) {
                    Log.d(TAG, "Counter: $counter")
                    counter++
                    Thread.sleep(100)
                }
            }
            delay(1000)
            Log.d(TAG, "Requesting cancellation")
            job.cancel()
            job.join()
            Log.d(TAG, "Cancellation requested")
        }
    }
}
