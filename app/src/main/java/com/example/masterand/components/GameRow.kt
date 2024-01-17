package com.example.masterand.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.masterand.utils.emptyRow

@Composable
fun GameRow(
    selectedColors: List<Color>,
    feedbackColors: List<Color>?,
    onColorClick: ((Int) -> Unit)? = null,
    onCheckClick: (() -> Unit)? = null
) {
    assert(selectedColors.size == 4)

    val colorAnimationSpec = repeatable<Color>(
        iterations = 3,
        animation = tween(250),
        repeatMode = RepeatMode.Reverse
    )

    val color0 by animateColorAsState(
        selectedColors[0],
        animationSpec = colorAnimationSpec,
        label = "Color 0"
    )
    val color1 by animateColorAsState(
        selectedColors[1],
        animationSpec = colorAnimationSpec,
        label = "Color 1"
    )
    val color2 by animateColorAsState(
        selectedColors[2],
        animationSpec = colorAnimationSpec,
        label = "Color 2"
    )
    val color3 by animateColorAsState(
        selectedColors[3],
        animationSpec = colorAnimationSpec,
        label = "Color 3"
    )

    val visibleState = remember { MutableTransitionState(false) }
    val areAllColorsUnique = selectedColors.map { it }.toSet().size == selectedColors.size
    visibleState.targetState = areAllColorsUnique && feedbackColors == null

    var rowVisible by remember { mutableStateOf(false) }
    LaunchedEffect(selectedColors) {
        rowVisible = true
    }

    AnimatedVisibility(visible = rowVisible, enter = expandVertically(expandFrom = Alignment.Top) ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            for ((i, color) in listOf(color0, color1, color2, color3).withIndex()) {
                CircularButton(color = color, onClick = { if (feedbackColors == null) onColorClick?.invoke(i) })
            }
            if (!visibleState.isIdle || visibleState.currentState) {
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
            } else {
                Box(modifier = Modifier.size(50.dp))
            }
            FeedbackCircles(feedbackColors?: emptyRow)
        }
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