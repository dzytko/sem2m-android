package com.example.masterand.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FeedbackCircles(colors: List<Color>) {
    assert(colors.size == 4)

    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            SmallCircle(color = colors[0])
            SmallCircle(color = colors[2])
        }
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            SmallCircle(color = colors[1])
            SmallCircle(color = colors[3])
        }
    }
}

@Composable
@Preview(showBackground = true)
fun FeedbackCirclesPreview() {
    FeedbackCircles(colors = listOf(Color.Red, Color.White, Color.Yellow, Color.Cyan))
}