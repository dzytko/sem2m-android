package com.example.masterand.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun GameRow(
    selectedColors: List<Color>,
    feedbackColors: List<Color>,
    onColorClick: ((Int) -> Unit)? = null,
    onCheckClick: (() -> Unit)? = null
) {
    assert(selectedColors.size == 4)
    assert(feedbackColors.size == 4)

    val visibleState = remember { MutableTransitionState(false) }
    val areAllColorsUnique = selectedColors.map { it }.toSet().size == selectedColors.size
    visibleState.targetState = areAllColorsUnique && onCheckClick != null

    Row(
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        for ((i, color) in selectedColors.withIndex()) {
            CircularButton(color = color, onClick = { onColorClick?.invoke(i) })
        }
        AnimatedVisibility(
            visibleState = visibleState,
            enter = scaleIn(animationSpec = tween(1000)),
            exit = scaleOut(animationSpec = tween(1000))
        ) {
            IconButton(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(50.dp),
                colors = IconButtonDefaults.filledIconButtonColors(),
                onClick = onCheckClick ?: {},
                enabled = areAllColorsUnique && onCheckClick != null
            ) {
                Icon(Icons.Outlined.Check, "Check")
            }
     }
        FeedbackCircles(feedbackColors)
    }
}

@Composable
@Preview(showBackground = true)
fun GameRowPreview() {
    GameRow(
        selectedColors = listOf(Color.Red, Color.White, Color.Yellow, Color.Cyan),
        feedbackColors = listOf(Color.Red, Color.White, Color.Yellow, Color.Cyan)
    )
}