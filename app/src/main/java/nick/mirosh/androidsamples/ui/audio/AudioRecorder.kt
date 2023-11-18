package nick.mirosh.androidsamples.ui.audio

import android.media.MediaRecorder
import android.os.Build
import android.os.Environment
import androidx.annotation.RequiresApi
import nick.mirosh.androidsamples.utils.logStackTrace
import java.io.IOException
import javax.inject.Inject


class AudioRecorder @Inject constructor(
    private val mediaRecorder: MediaRecorder
) {
    private var fileName: String

    init {
        fileName = Environment.getExternalStorageDirectory().absolutePath
        fileName += "/recorded_audio.3gp"
    }

    @RequiresApi(Build.VERSION_CODES.S)
    fun startRecording() {
        with(mediaRecorder) {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setOutputFile(fileName)
            try {
                prepare()
            } catch (e: IOException) {
                e.logStackTrace("AudioRecorder")
            }
            start()
        }
    }

    fun stopRecording() {
        mediaRecorder.stop()
        mediaRecorder.release()
    }
}