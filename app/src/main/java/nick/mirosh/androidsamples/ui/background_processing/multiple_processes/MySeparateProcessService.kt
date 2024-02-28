package nick.mirosh.androidsamples.ui.background_processing.multiple_processes

import android.app.Service
import android.content.Intent
import android.os.IBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MySeparateProcessService : Service() {
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        // Intentionally causing a crash
        CoroutineScope(Dispatchers.IO).launch {
            delay(5000)
            throw RuntimeException("Crash in separate process service")
        }
        return START_NOT_STICKY
    }
}