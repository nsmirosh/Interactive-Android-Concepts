package nick.mirosh.androidsamples.ui.coroutines.cancellation

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class CancellationViewModel: ViewModel() {

    fun launchWithSupervisor() {
        CoroutineScope(Dispatchers.Main).launch {
            supervisorScope {
                try {
                    launch {

                    }
                } catch (e: Exception) {
                    Log.d("AsyncComparison", "Exception caught: $e")
                }
            }
        }
    }
}