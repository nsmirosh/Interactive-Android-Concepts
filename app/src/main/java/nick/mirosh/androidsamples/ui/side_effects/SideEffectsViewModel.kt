package nick.mirosh.androidsamples.ui.side_effects

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

const val TAG = "SideEffectsViewModel"

class SideEffectsViewModel : ViewModel() {

    private val _progressMessage = MutableStateFlow("")
    val progressMessage = _progressMessage.asStateFlow()

    private val _messageToDisplay = MutableStateFlow<(() -> String)?>(null)
    val messageToDisplay = _messageToDisplay.asStateFlow()

    private val _timerValue = MutableStateFlow("")
    val timerValue = _timerValue.asStateFlow()

    private var initialMessage = ""
    private var newMessage = ""

    fun scheduleMessage(message: String) {
        Log.d(TAG, "scheduleMessage() called with: message = $message")
        initialMessage = message
    }

    fun scheduleUpdate(message: String) {
        Log.d(TAG, "scheduleUpdate() called with: message = $message")
        newMessage = message
        start()
    }

    fun reset() {
        _timerValue.value = "0"
    }

    private fun start() {
        Log.d("SideEffectsViewModel", "start: ")
        _progressMessage.value =
            "Message $initialMessage is scheduled"
        _messageToDisplay.value = { initialMessage }
        viewModelScope.launch {
            for (i in (MESSAGE_DELAY / 100).toInt() downTo 0) {
                _timerValue.value = " ${i / 10},${i % 10}s"
                if (i == 50) {
                    Log.d("SideEffectsViewModel", "updating message")
                    _progressMessage.value =
                        "Updating initial message with new message -  $newMessage "
                    _messageToDisplay.value = { newMessage }
                }
                delay(100)
            }
        }
    }
}