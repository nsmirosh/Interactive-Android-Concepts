package com.example.androidcomposeexample.ui.sideeffects.launchedeffect

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

const val UPDATE_DELAY_IN_DECISECONDS = 30

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
        startTimerAndUpdates()
    }

    /*
    In this function we're scheduling our initial message and an update message.
    We're also starting a timer that will count down from 8 seconds to 0 every decisecond.
    After UPDATE_DELAY_IN_DECISECONDS we're updating the message to display the new message.
      */
    private fun startTimerAndUpdates() {
        _progressMessage.value =
            "Message \"$initialMessage\" is scheduled"
        _messageToDisplay.value = { initialMessage }
        viewModelScope.launch {
            for (i in (MESSAGE_DELAY / 100).toInt() downTo 0) {
                _timerValue.value = " ${i / 10},${i % 10}s"
                if (i == UPDATE_DELAY_IN_DECISECONDS) {
                    _progressMessage.value =
                        "Message is now \"$newMessage\""
                    _messageToDisplay.value = { newMessage }
                }
                delay(100)
            }
        }
    }
}
