package com.mrenann.dataagrin.core.ui.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoadingViewTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loadingView_displaysCircularProgressIndicator() {
        composeTestRule.setContent {
            MaterialTheme {
                LoadingView()
            }
        }

        composeTestRule.onNodeWithTag("loading_indicator").assertIsDisplayed()
    }
}