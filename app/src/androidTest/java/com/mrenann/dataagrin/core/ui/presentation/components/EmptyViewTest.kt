package com.mrenann.dataagrin.core.ui.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EmptyViewTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun emptyView_displaysCorrectTitleAndSubtitle() {
        val testTitle = "Nenhum item encontrado"
        val testSubtitle = "Tente adicionar um novo item ou altere seus filtros."

        composeTestRule.setContent {
            MaterialTheme {
                EmptyView(
                    title = testTitle,
                    subTitle = testSubtitle
                )
            }
        }

        composeTestRule.onNodeWithTag("empty_view_container").assertIsDisplayed()
        composeTestRule.onNodeWithTag("empty_view_icon").assertIsDisplayed()

        composeTestRule.onNodeWithTag("empty_view_title")
            .assertIsDisplayed()
            .assertTextEquals(testTitle)

        composeTestRule.onNodeWithTag("empty_view_subtitle")
            .assertIsDisplayed()
            .assertTextEquals(testSubtitle)
    }

    @Test
    fun emptyView_withDifferentContent_displaysCorrectly() {
        val testTitle = "Caixa de Entrada Vazia"
        val testSubtitle = "Você não possui novas mensagens."

        composeTestRule.setContent {
            MaterialTheme {
                EmptyView(
                    title = testTitle,
                    subTitle = testSubtitle
                )
            }
        }

        composeTestRule.onNodeWithTag("empty_view_title")
            .assertIsDisplayed()
            .assertTextEquals(testTitle)

        composeTestRule.onNodeWithTag("empty_view_subtitle")
            .assertIsDisplayed()
            .assertTextEquals(testSubtitle)
    }
}