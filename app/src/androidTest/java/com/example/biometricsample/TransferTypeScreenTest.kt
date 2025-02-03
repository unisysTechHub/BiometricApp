package com.example.biometricsample
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.findNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.biometricsample.activity.MainActivityBottomNav
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TransferTypeScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivityBottomNav>()

    @Test
    fun testButtonInDashboardFragment() {
        // Simulate navigating to the Dashboard fragment
        composeTestRule.activity.runOnUiThread {
            val navController = composeTestRule.activity.findNavController(R.id.nav_host_fragment_activity_main_bottom_nav)
            navController.navigate(R.id.navigation_beneficiary_home)
        }

        // Verify the button with the text "Test" is displayed
        composeTestRule.onNodeWithTag("DomesticWire").assertExists()

        // Perform a click on the button
        composeTestRule.onNodeWithTag("DomesticWire").performClick()

        // Optionally verify the behavior after the button click
        // For example, check if another Composable or text is displayed
        composeTestRule.onNodeWithText("Submit").assertExists()
        val textfiedNode = composeTestRule.onNodeWithTag("Account Number")
       // composeTestRule.onNodeWithText("Enter accountNumber").assertExists()
       textfiedNode.performTextInput("123456789")
        composeTestRule.onNodeWithText("Submit").assertExists()
        Thread.sleep(3000)
        // composeTestRule.onNodeWithText("Enter accountNumber").assertDoesNotExist()


    }
}