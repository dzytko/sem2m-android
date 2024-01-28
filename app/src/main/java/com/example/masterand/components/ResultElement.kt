package com.example.masterand.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ResultElement(playerName: String, score: Int) {
    Column(modifier = Modifier.padding(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = playerName, modifier = Modifier.padding(start = 12.dp), fontSize = 30.sp)
            Text(text = score.toString(), modifier = Modifier.padding(end = 16.dp), fontSize = 30.sp)
        }
        Divider(color = Color.Black, modifier = Modifier.padding(top = 8.dp))
    }
}

@Composable
@Preview(showBackground = true)
fun ResultElementPreview() {
    ResultElement(
        playerName = "Player 1",
        score = 4
    )
}