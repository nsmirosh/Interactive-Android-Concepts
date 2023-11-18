package nick.mirosh.androidsamples.ui.audio

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FetchAudioViewModel @Inject constructor(
    private val audioRecorder: AudioRecorder
) : ViewModel() {

    @RequiresApi(Build.VERSION_CODES.S)
    fun recordAudio() {
        audioRecorder.startRecording()
    }
}