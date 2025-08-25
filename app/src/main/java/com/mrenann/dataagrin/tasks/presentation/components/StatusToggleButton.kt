package com.mrenann.dataagrin.tasks.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mrenann.dataagrin.core.data.local.entity.ActivityStatus
import compose.icons.EvaIcons
import compose.icons.evaicons.Fill
import compose.icons.evaicons.Outline
import compose.icons.evaicons.fill.CheckmarkCircle
import compose.icons.evaicons.fill.Sync
import compose.icons.evaicons.outline.Clock

@Composable
fun StatusToggleButton(
    status: ActivityStatus,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val icon = when (status) {
        ActivityStatus.PENDING -> EvaIcons.Outline.Clock
        ActivityStatus.IN_PROGRESS -> EvaIcons.Fill.Sync
        ActivityStatus.DONE -> EvaIcons.Fill.CheckmarkCircle
    }

    Box(
        modifier = modifier
            .size(ToggleButtonDefaults.BUTTON_SIZE)
            .clip(CircleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        AnimatedContent(
            targetState = icon,
            transitionSpec = {
                (fadeIn(animationSpec = tween(ToggleButtonDefaults.ANIMATION_DURATION)) + scaleIn(
                    animationSpec = tween(ToggleButtonDefaults.ANIMATION_DURATION)
                ))
                    .togetherWith(
                        fadeOut(animationSpec = tween(ToggleButtonDefaults.ANIMATION_DURATION)) + scaleOut(
                            animationSpec = tween(ToggleButtonDefaults.ANIMATION_DURATION)
                        )
                    )
            }, label = "iconAnimation"
        ) { targetIcon ->
            Icon(
                imageVector = targetIcon,
                contentDescription = "Status da Tarefa",
                tint = color,
                modifier = Modifier.size(ToggleButtonDefaults.ICON_SIZE)
            )
        }
    }
}

private object ToggleButtonDefaults {
    val BUTTON_SIZE = 40.dp
    val ICON_SIZE = 24.dp
    const val ANIMATION_DURATION = 200
}