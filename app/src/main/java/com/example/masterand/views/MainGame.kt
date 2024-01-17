package com.example.masterand.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.masterand.components.GameRow
import com.example.masterand.utils.checkGuess
import com.example.masterand.utils.emptyRow
import com.example.masterand.utils.generateRandomColors
import com.example.masterand.utils.getNextColorForIndex

@Composable
fun MainGame(onScoreScreen: (String) -> Unit = {}, onLogout: () -> Unit = {}) {
    val solution = remember { generateRandomColors() }

    val score = remember { mutableIntStateOf(1) }
    val isGameFinished = remember { mutableStateOf(false) }

    val rows = remember { mutableStateOf(listOf(emptyRow)) }
    val feedbacks = remember { mutableStateOf(listOf<List<Color>>()) }

    fun restartGame() {
        score.intValue = 1
        isGameFinished.value = false
        rows.value = listOf()

    }
    Column(
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween

    ) {
        Column(
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Your score: ${score.intValue}",
                fontSize = 50.sp,
                modifier = Modifier.padding(4.dp)
            )
            // debug row with solution
//            GameRow(selectedColors = solution, feedbackColors = emptyRow)
            for (rowIndex in rows.value.indices) {
                val row = rows.value[rowIndex]
                GameRow(
                    selectedColors = row,
                    feedbackColors = feedbacks.value.getOrNull(rowIndex),
                    onColorClick = { index ->
                        val newColor = getNextColorForIndex(index, row)
                        val newRow = row.toMutableList()
                        newRow[index] = newColor
                        rows.value = rows.value.toMutableList().apply {
                            set(rowIndex, newRow.toList())
                        }
                    },
                    onCheckClick = {
                        feedbacks.value = feedbacks.value + listOf(checkGuess(rows.value.last(), solution))
                        if (rows.value.last() == solution) {
                            isGameFinished.value = true
                            return@GameRow
                        }
                        score.intValue += 1
                        rows.value = rows.value + listOf(emptyRow.toList())
                    }
                )
            }
            if (isGameFinished.value) {
                Button(onClick = {onScoreScreen(score.intValue.toString())}) {
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