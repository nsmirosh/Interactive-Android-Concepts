package nick.mirosh.androidsamples.ui.side_effects

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SideEffectsViewModel : ViewModel() {

    private val _progress = MutableStateFlow<() -> Unit> {
        Log.d(
            "SideEffects",
            "Side effect with $0"
        )
    }
    val progress = _progress.asStateFlow()

    private val _updatedValue = MutableStateFlow(0)
    val updatedValue = _updatedValue.asStateFlow()

    fun increaseTimer() {
        _updatedValue.value += 10
    }

    fun decreaseTimer() {
        _updatedValue.value -= 10
    }

    fun reset() {
        _updatedValue.value = 0
    }

    //function that posts updates every second to a flow
    fun startTimer() {
        viewModelScope.launch {
            for (i in updatedValue.value downTo 0) {
                _updatedValue.value = i
                Log.d(
                    "SideEffects",
                    "Side effect with $i"
                )
                delay(100)
            }
        }
    }
}