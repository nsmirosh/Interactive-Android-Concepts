package nick.mirosh.androidsamples.ui.coroutines.exceptions.different_exceptions

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

const val TAG = "ExceptionPropagationViewModel"

class ExceptionPropagationViewModel : ViewModel() {

    fun simpleChallenge() {
        CoroutineScope(Dispatchers.IO).launch {
            launch {
                Log.d(TAG, "child1")
                delay(200)
                Log.d(TAG, "child1 working")
                throw RuntimeException()
            }

            launch {
                Log.d(TAG, "child2")
                delay(300)
                Log.d(TAG, "child2 finishing")
            }

            launch {
                delay(100)
                throw CancellationException()
            }
        }
    }

}