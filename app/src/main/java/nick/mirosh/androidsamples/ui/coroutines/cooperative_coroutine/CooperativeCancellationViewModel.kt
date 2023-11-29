package nick.mirosh.androidsamples.ui.coroutines.cooperative_coroutine

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

const val TAG = "CooperativeCancellationViewModel"

class CooperativeCancellationViewModel : ViewModel() {

    lateinit var job: Job

    var shouldRun = true

    private val _job1flow = MutableStateFlow(0f)
    val job1flow = _job1flow.asStateFlow()

    private val _coroutineStatus = MutableStateFlow("Not started")
    val coroutineStatus = _coroutineStatus.asStateFlow()

    init {
        setupUncooperativeCoroutine()
    }

    private fun setupUncooperativeCoroutine() {
        runBlocking {
            job = GlobalScope.launch(
                start = CoroutineStart.LAZY
            ) {
                _coroutineStatus.value = getCoroutineStatus(job)
                Log.d(TAG, "running uncooperative cooroutine")
                var counter = 0
                while (counter <= 100 && shouldRun) {
                    Thread.sleep(1000)
                    _job1flow.value = counter / 100f
                    counter += 5
                }
                Log.d(TAG, "Finished the loop")
            }
        }
    }


    fun setUpJobWithIsActiveCheck() {
        runBlocking {
            job = GlobalScope.launch(
                start = CoroutineStart.LAZY
            ) {
                _coroutineStatus.value = getCoroutineStatus(job)
                Log.d(TAG, "running cooperative cooroutine")
                var counter = 0
                while (counter <= 100 && isActive) {
                    Thread.sleep(500)
                    _job1flow.value = counter / 100f
                    counter += 10
                }
                Log.d(TAG, "Finished the loop")
            }
        }
    }

    fun setUpJobWithCancellationException() {
        runBlocking {
            job = GlobalScope.launch(
                start = CoroutineStart.LAZY
            ) {
                _coroutineStatus.value = getCoroutineStatus(job)
                try {
                    var counter = 0
                    while (counter <= 100) {
                        delay(500)
                        _job1flow.value = counter / 100f
                        counter += 10
                    }
                } catch (e: CancellationException) {
                    Log.d(TAG, "Caught cancellation exception")
                }
            }
        }
    }

    //https://lottiefiles.com/blog/working-with-lottie/getting-started-with-lottie-animations-in-android-app


    fun start() {
        job.start()
    }

    fun cancel() {
        viewModelScope.launch {
            job.cancel()
            _coroutineStatus.value = getCoroutineStatus(job)
            //give some time to see that the coroutine was cancelled
            delay(500)
            job.join()
            _coroutineStatus.value = getCoroutineStatus(job)
            Log.d(TAG, "Cancellation completed")
        }
    }

    private fun getCoroutineStatus(job: Job): String = with(job) {
        return when {
            isActive -> "Active"
            isCancelled && isCompleted -> "Completed"
            isCancelled -> "Cancelled"
            else -> "Not started"
        }
    }

    fun stopUncooperative() {
        shouldRun = false
    }

    fun clear() {
        _job1flow.value = 0f
        setupUncooperativeCoroutine()
    }
}
