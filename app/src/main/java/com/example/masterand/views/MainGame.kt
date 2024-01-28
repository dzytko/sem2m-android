package com.example.masterand.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.masterand.AppViewModelProvider
import com.example.masterand.components.GameRow
import com.example.masterand.utils.checkGuess
import com.example.masterand.utils.emptyRow
import com.example.masterand.utils.getNextColorForIndex
import com.example.masterand.view_models.MainGameViewModel

@Composable
fun MainGame(
    onScoreScreen: (String) -> Unit = {},
    onLogout: () -> Unit = {},
    viewModel: MainGameViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    Column(
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween

    ) {
        Column(
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Your score: ${viewModel.score.intValue}",
                fontSize = 50.sp,
                modifier = Modifier.padding(4.dp)
            )
            // debug row with solution
//            GameRow(selectedColors = viewModel.solution, feedbackColors = emptyRow)
            for (rowIndex in viewModel.rows.value.indices) {
                val row = viewModel.rows.value[rowIndex]
                GameRow(
                    selectedColors = row,
                    feedbackColors = viewModel.feedbacks.value.getOrNull(rowIndex),
                    onColorClick = { index ->
                        val newColor = getNextColorForIndex(index, row)
                        val newRow = row.toMutableList()
                        newRow[index] = newColor
                        viewModel.rows.value =viewModel. rows.value.toMutableList().apply {
                            set(rowIndex, newRow.toList())
                        }
                    },
                    onCheckClick = {
                        viewModel.feedbacks.value = viewModel.feedbacks.value + listOf(checkGuess(viewModel.rows.value.last(), viewModel.solution))
                        if (viewModel.rows.value.last() == viewModel.solution) {
                            viewModel.isGameFinished.value = true
                            return@GameRow
                        }
                        viewModel.score.intValue += 1
                        viewModel.rows.value = viewModel.rows.value + listOf(emptyRow.toList())
                    }
                )
            }
            if (viewModel.isGameFinished.value) {
                Button(onClick = {viewModel.navigateToScoreScreen(onScoreScreen)}) {
                    Text(text = "High score table")
                }
            }
        }
        Button(onClick = onLogout) {
            Text(text = "Logout")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainGamePreview() {
    MainGame()
}