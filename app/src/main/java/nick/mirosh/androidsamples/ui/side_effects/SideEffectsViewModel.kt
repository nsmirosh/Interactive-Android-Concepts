package nick.mirosh.androidsamples.ui.side_effects

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SideEffectsViewModel : ViewModel() {

    private val _progressMessage = MutableStateFlow("")
    val progressMessage = _progressMessage.asStateFlow()

    private val _messageToDisplay = MutableStateFlow<() -> Unit>({})
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
        _progressMessage.value =
            "Message $initialMessage is scheduled to be displayed in 5 seconds "
        _messageToDisplay.value = { initialMessage }
        viewModelScope.launch {
            _messageToDisplay.value = { initialMessage }
            for (i in 50 downTo 0) {
                _timerValue.value = " ${i / 10},${i % 10}s"
                if (i == 30) {
                    _progressMessage.value =
                        "Updating initial message with new message -  $newMessage "
                    _messageToDisplay.value = { newMessage }
                }
                delay(100)
            }
        }
    }
}