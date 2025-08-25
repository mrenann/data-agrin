package com.mrenann.dataagrin.core.ui.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PrimaryButtonTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun primaryButton_displaysCorrectText() {
        val buttonText = "Salvar Atividade"

        composeTestRule.setContent {
            MaterialTheme {
                PrimaryButton(text = buttonText, onClick = {})
            }
        }

        composeTestRule.onNodeWithTag("primary_button")
            .assertIsDisplayed()
            .assertTextEquals(buttonText)
    }

    @Test
    fun primaryButton_whenClicked_invokesCallback() {
        var clicked = false

        composeTestRule.setContent {
            MaterialTheme {
                PrimaryButton(text = "Clique Aqui", onClick = { clicked = true })
            }
        }
        composeTestRule.onNodeWithTag("primary_button").performClick()

        assert(clicked) { "O callback onClick não foi invocado." }
    }

    @Test
    fun primaryButton_whenEnabled_isClickable() {
        composeTestRule.setContent {
            MaterialTheme {
                PrimaryButton(text = "Habilitado", onClick = {}, enabled = true)
            }
        }

        composeTestRule.onNodeWithTag("primary_button")
            .assertIsEnabled()
            .assertHasClickAction()
    }

    @Test
    fun primaryButton_whenDisabled_isNotClickable() {
        var clicked = false
        composeTestRule.setContent {
            MaterialTheme {
                PrimaryButton(text = "Desabilitado", onClick = { clicked = true }, enabled = false)
            }
        }

        composeTestRule.onNodeWithTag("primary_button").performClick()

        composeTestRule.onNodeWithTag("primary_button").assertIsNotEnabled()
        assert(!clicked) { "O callback onClick foi invocado mesmo com o botão desabilitado." }
    }
}