package com.mrenann.dataagrin.core.ui.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StyledTextFieldTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun styledTextField_displaysInitialValueAndLabel() {
        val initialValue = "Texto Inicial"
        val label = "Nome da Atividade"

        composeTestRule.setContent {
            MaterialTheme {
                StyledTextField(
                    value = initialValue,
                    onValueChange = {},
                    label = label
                )
            }
        }

        composeTestRule.onNodeWithText(initialValue)
            .assertIsDisplayed()

        composeTestRule.onNodeWithText(label)
            .assertIsDisplayed()
    }

    @Test
    fun styledTextField_onValueChange_updatesValue() {
        val typedText = "Novo texto digitado"

        composeTestRule.setContent {
            MaterialTheme {
                val state = remember { mutableStateOf("") }
                StyledTextField(
                    value = state.value,
                    onValueChange = { state.value = it },
                    label = "Campo de Teste"
                )
            }
        }

        composeTestRule.onNodeWithTag("styled_text_field")
            .performTextInput(typedText)

        composeTestRule.onNodeWithText(typedText)
            .assertIsDisplayed()
    }

    @Test
    fun styledTextField_whenDisabled_isNotEditable() {
        val initialValue = "Não pode editar"
        val label = "Desabilitado"

        composeTestRule.setContent {
            MaterialTheme {
                StyledTextField(
                    value = initialValue,
                    onValueChange = {},
                    label = label,
                    enabled = false
                )
            }
        }

        composeTestRule.onNodeWithTag("styled_text_field")
            .assertIsNotEnabled()

        composeTestRule.onNodeWithText(initialValue)
            .assertIsDisplayed()

        composeTestRule.onNodeWithText(label)
            .assertIsDisplayed()
    }

    @Test
    fun styledTextField_whenReadOnly_isNotEditableButIsEnabled() {
        val initialValue = "Apenas Leitura"

        composeTestRule.setContent {
            MaterialTheme {
                StyledTextField(
                    value = initialValue,
                    onValueChange = {},
                    label = "Apenas Leitura",
                    readOnly = true
                )
            }
        }

        composeTestRule.onNodeWithTag("styled_text_field")
            .assertIsEnabled()
            .assertTextEquals(initialValue)
    }
}