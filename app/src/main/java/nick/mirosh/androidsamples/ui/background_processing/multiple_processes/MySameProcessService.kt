package nick.mirosh.androidsamples.ui.background_processing.multiple_processes

import android.app.Service
import android.content.Intent
import android.os.IBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MySameProcessService : Service() {
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val causeCrash = intent.getBooleanExtra("cause_crash", false)
        Thread.setDefaultUncaughtExceptionHandler{
            _, throwable -> throwable.printStackTrace()
        }
        if (causeCrash) {
            throw RuntimeException("Crash in same process service")
        }
        return START_NOT_STICKY
    }
}