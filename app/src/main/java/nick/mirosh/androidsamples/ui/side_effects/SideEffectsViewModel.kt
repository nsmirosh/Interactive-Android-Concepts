package nick.mirosh.androidsamples.ui.side_effects

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

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
        initialMessage = message
    }

    fun scheduleUpdate(message: String) {
        newMessage = message
        start()
    }

    fun reset() {
        _timerValue.value = "0"
    }

    private fun start() {
        Log.d("SideEffectsViewModel", "start: ")
        _progressMessage.value =
            "Message $initialMessage is scheduled to be displayed in 5 seconds "
        _messageToDisplay.value = { initialMessage }
        viewModelScope.launch {
            _messageToDisplay.value = { initialMessage }
            for (i in 70 downTo 0) {
                _timerValue.value = " ${i / 10},${i % 10}s"
                if (i == 40) {
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