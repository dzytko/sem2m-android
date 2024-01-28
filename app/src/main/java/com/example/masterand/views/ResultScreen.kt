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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.masterand.AppViewModelProvider
import com.example.masterand.components.ResultElement
import com.example.masterand.view_models.ResultScreenViewModel

@Composable
fun ResultScreen(
    score: String, onRestartGame: () -> Unit = {},
    onLogout: () -> Unit = {},
    viewModel: ResultScreenViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text="Results", fontSize = 50.sp, modifier = Modifier.padding(4.dp))
        Text(text="Recent score: $score", fontSize = 30.sp, modifier = Modifier.padding(4.dp))
        for (result in viewModel.fetchResults()) {
            ResultElement(result.name, result.score)
        }
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