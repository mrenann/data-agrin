package com.mrenann.dataagrin.activityLog.presentation.tab

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.lyricist.strings
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

class ActivityTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = strings.activities.tabTitle
            val icon = rememberVectorPainter(Icons.Default.Lock)

            return remember {
                TabOptions(
                    index = 1u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Text("Tab home")
    }
}
