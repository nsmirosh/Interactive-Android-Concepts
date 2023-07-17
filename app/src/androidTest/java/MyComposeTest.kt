import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import nick.mirosh.androidsamples.ui.side_effects.CHECKBOX_TAG
import nick.mirosh.androidsamples.ui.side_effects.MESSAGE_INPUT_TAG
import nick.mirosh.androidsamples.ui.side_effects.SideEffectsScreen
import nick.mirosh.androidsamples.ui.side_effects.TIMER_UPDATE_TAG
import nick.mirosh.androidsamples.ui.theme.MyApplicationTheme
import org.junit.Rule
import org.junit.Test

class MyComposeTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun clickingThroughTheFlow_withValidInputs_showsTimer() {
        // Start the app
        composeTestRule.setContent {
            MyApplicationTheme {
                SideEffectsScreen()
            }
        }
        composeTestRule.onNodeWithTag(MESSAGE_INPUT_TAG)
            .performTextInput("First message")
        composeTestRule.onNodeWithText("Schedule message").performClick()
        composeTestRule.onNodeWithTag(MESSAGE_INPUT_TAG)
            .performTextInput("Update message")
        composeTestRule.onNodeWithText("Schedule message").performClick()
        composeTestRule.onNodeWithTag(TIMER_UPDATE_TAG).assertIsDisplayed()
    }

    @Test
    fun clickingThroughTheFlow_withValidInputsAndCheckBox_showsTimer() {
        // Start the app
        composeTestRule.setContent {
            MyApplicationTheme {
                SideEffectsScreen()
            }
        }
        composeTestRule.onNodeWithTag(MESSAGE_INPUT_TAG)
            .performTextInput("First message")
        composeTestRule.onNodeWithText("Schedule message").performClick()
        composeTestRule.onNodeWithTag(MESSAGE_INPUT_TAG)
            .performTextInput("Update message")
        composeTestRule.onNodeWithTag(CHECKBOX_TAG).performClick()
        composeTestRule.onNodeWithText("Schedule message").performClick()
        composeTestRule.onNodeWithTag(TIMER_UPDATE_TAG).assertIsDisplayed()
    }
}