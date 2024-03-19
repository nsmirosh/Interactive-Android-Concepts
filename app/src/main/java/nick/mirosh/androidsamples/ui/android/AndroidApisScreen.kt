package nick.mirosh.androidsamples.ui.android

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.SystemClock
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext


@Composable
fun AndroidApisScreen() {

    val context = LocalContext.current.applicationContext
    scheduleElapsedRealtimeTask(context)
}

class YourElapsedReceiver(
) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        Log.d("YourElapsedReceiver", "onReceive: ")
        if (intent?.action == "Balls") {
            Log.d("YourElapsedReceiver", "onReceive balls: ")
//                repo.getUserInfo()
        }
    }
}

private fun scheduleElapsedRealtimeTask(context: Context) {

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, YourElapsedReceiver::class.java)
    intent.action = "Balls"
    val requestCode = 0

    // If task is already scheduled, return
    if (PendingIntent.getBroadcast(
            context, requestCode, intent,
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        ) != null
    ) {
        return
    }

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        requestCode,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

//        val triggerAtMillis = SystemClock.elapsedRealtime() + timeLeftInSeconds * 1000
    val triggerAtMillis = SystemClock.elapsedRealtime() + 5 * 1000

    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                triggerAtMillis,
                pendingIntent
            )
        } else {
            alarmManager.setExact(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                triggerAtMillis,
                pendingIntent
            )
        }
    } catch (e: SecurityException) {
        e.printStackTrace()
    }
}
