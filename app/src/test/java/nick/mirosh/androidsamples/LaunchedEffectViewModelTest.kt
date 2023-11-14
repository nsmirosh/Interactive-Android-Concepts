package nick.mirosh.androidsamples

import com.example.androidcomposeexample.ui.sideeffects.launchedeffect.SideEffectsViewModel
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.take
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test


class LaunchedEffectViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun scheduleAndUpdate_withValidArgs_changesProgressAndMessageToDisplayValues() =
        mainCoroutineRule.runTest {
            // Arrange
            val viewModel = SideEffectsViewModel()
            val initialMessageToBeSent = "New message"
            val updateMessageToBeSent = "Update message"

            // Act
            viewModel.scheduleMessage(initialMessageToBeSent)
            viewModel.scheduleUpdate(updateMessageToBeSent)

            // Assert
            assertEquals(
                "Message $initialMessageToBeSent is scheduled",
                viewModel.progressMessage.value
            )

            assertEquals(
                initialMessageToBeSent,
                viewModel.messageToDisplay.value?.invoke()
            )
            assertEquals(
                "Updating initial message with new message -  $updateMessageToBeSent",
                viewModel.progressMessage.take(2).last()
            )
            assertEquals(
                updateMessageToBeSent,
                viewModel.messageToDisplay.value?.invoke()
            )
        }
}
