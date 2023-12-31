package com.example.masterand.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ResultScreen(score: String, onRestartGame: () -> Unit = {}, onLogout: () -> Unit = {}) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text="Results", fontSize = 50.sp, modifier = Modifier.padding(4.dp))
        Text(text="Recent score: $score", fontSize = 30.sp, modifier = Modifier.padding(4.dp))
        Button(onClick = onRestartGame) {
            Text(text = "Restart game")
        }
        Button(onClick = onLogout) {
            Text(text = "Logout")
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ResultScreenPreview() {
    ResultScreen("4")
}