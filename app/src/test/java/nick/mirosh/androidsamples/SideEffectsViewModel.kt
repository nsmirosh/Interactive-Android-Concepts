package nick.mirosh.androidsamples

import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import nick.mirosh.androidsamples.ui.side_effects.SideEffectsViewModel
import org.junit.Rule
import org.junit.Test

const val MINIMUM_NEEDED_TIME_TO_UPDATE_MESSAGE = 3100L

class SideEffectsViewModel {


    @ExperimentalCoroutinesApi
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `initial message + scheduling of messagin with valid args produces correct messages in progressMessage`() =
        runTest {
            val viewModel = SideEffectsViewModel()
            viewModel.scheduleMessage("New message")
            viewModel.scheduleUpdate("Update message")
            TestCase.assertEquals(
                "Message New message is scheduled",
                viewModel.progressMessage.value
            )
            TestCase.assertEquals(
                "New message",
                viewModel.messageToDisplay.value?.invoke()
            )
            advanceTimeBy(MINIMUM_NEEDED_TIME_TO_UPDATE_MESSAGE)

            TestCase.assertEquals(
                "Updating initial message with new message -  Update message",
                viewModel.progressMessage.value
            )
            TestCase.assertEquals(
                "Update message",
                viewModel.messageToDisplay.value?.invoke()
            )
        }
}
