package nick.mirosh.androidsamples.ui.side_effects

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SideEffectsViewModel : ViewModel() {

    private val _messageToDisplay = MutableStateFlow<() -> Unit>({})
    val messageToDisplay = _messageToDisplay.asStateFlow()

    private val _initialTimer = MutableStateFlow(0)
    val initialTimer = _initialTimer.asStateFlow()

    private var initialMessage = ""
    private var newMessage = ""
    private var newMessageTimer = 0

    fun scheduleMessage(message: String, delay: Int) {
        _initialTimer.value = delay
        initialMessage = message
    }

    fun scheduleUpdate(message: String, delay: Int) {
        newMessage = message
        newMessageTimer = delay
    }

    fun reset() {
        _initialTimer.value = 0
    }

    fun start() {
        viewModelScope.launch {
            _messageToDisplay.value = { initialMessage }
            for (i in initialTimer.value downTo 0) {
                _initialTimer.value = i
                delay(100)
            }
        }
        viewModelScope.launch {
            delay(newMessageTimer * 100L)
            _messageToDisplay.value = { newMessage }
        }
    }
}