package com.mrenann.dataagrin.core.ui.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ErrorViewTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun errorView_displaysCorrectMessageAndButtonText() {
        val testMessage = "Não foi possível conectar ao servidor"
        val testButtonText = "Tentar novamente"

        composeTestRule.setContent {
            MaterialTheme {
                ErrorView(
                    message = testMessage,
                    onRetry = { }
                )
            }
        }

        composeTestRule.onNodeWithTag("error_view_container").assertIsDisplayed()

        composeTestRule.onNodeWithTag("error_view_message")
            .assertIsDisplayed()
            .assertTextEquals(testMessage)

        composeTestRule.onNodeWithTag("error_view_retry_button")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText(testButtonText).assertIsDisplayed()
    }

    @Test
    fun errorView_whenClicked_invokesCallback() {
        var buttonClicked = false
        val testMessage = "Erro"

        composeTestRule.setContent {
            MaterialTheme {
                ErrorView(
                    message = testMessage,
                    onRetry = { buttonClicked = true }
                )
            }
        }

        composeTestRule.onNodeWithTag("error_view_retry_button").performClick()

        assert(buttonClicked) { "O botão não invocou a função onRetry" }
    }
}