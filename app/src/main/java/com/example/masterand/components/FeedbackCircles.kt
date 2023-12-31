package com.example.masterand.components

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FeedbackCircles(colors: List<Color>) {
    assert(colors.size == 4)

    val colorAnimation0 = remember { Animatable(Color.White) }
    val colorAnimation1 = remember { Animatable(Color.White) }
    val colorAnimation2 = remember { Animatable(Color.White) }
    val colorAnimation3 = remember { Animatable(Color.White) }
    LaunchedEffect(colors) {
        colorAnimation0.animateTo(colors[0], animationSpec = tween(250))
        colorAnimation1.animateTo(colors[1], animationSpec = tween(250))
        colorAnimation2.animateTo(colors[2], animationSpec = tween(250))
        colorAnimation3.animateTo(colors[3], animationSpec = tween(250))
    }

    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            SmallCircle(color = colorAnimation0.value)
            SmallCircle(color = colorAnimation2.value)
        }
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            SmallCircle(color = colorAnimation1.value)
            SmallCircle(color = colorAnimation3.value)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun FeedbackCirclesPreview() {
    FeedbackCircles(colors = listOf(Color.Red, Color.White, Color.Yellow, Color.Cyan))
}