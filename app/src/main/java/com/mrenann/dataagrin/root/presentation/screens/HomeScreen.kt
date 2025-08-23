package com.mrenann.dataagrin.root.presentation.screens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.mrenann.dataagrin.root.presentation.components.MainContent

class HomeScreen : Screen {
    @Composable
    override fun Content() {
        LocalNavigator.current
        val tabs = listOf<Tab>(HomeTab(), HomeTab())
        TabNavigator(
            tab = HomeTab(),
            tabDisposable = {
                TabDisposable(
                    navigator = it, tabs = tabs
                )
            }

        ) {
            MainContent(tabs)

        }
    }

}

