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
import com.example.masterand.utils.generateRandomColors
import com.example.masterand.utils.getNextColorForIndex

@Composable
fun MainGame(onScoreScreen: (String) -> Unit = {}, onLogout: () -> Unit = {}) {
    val emptyRow = List(4) { Color.White }
    val solution = remember { generateRandomColors() }

    val score = remember { mutableIntStateOf(1) }
    val isGameFinished = remember { mutableStateOf(false) }

    val rows = remember { mutableStateOf(listOf<List<Color>>()) }
    val activeRow = remember { mutableStateOf(emptyRow.toList()) }

    fun restartGame() {
        score.intValue = 1
        isGameFinished.value = false
        rows.value = listOf()
        activeRow.value = emptyRow.toList()
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
            GameRow(selectedColors = solution, feedbackColors = emptyRow)
            for (row in rows.value) {
                GameRow(
                    selectedColors = row,
                    feedbackColors = checkGuess(row, solution),
                )
            }
            if (!isGameFinished.value) {
                GameRow(
                    selectedColors = activeRow.value,
                    feedbackColors = emptyRow,
                    onColorClick = { index ->
                        activeRow.value = activeRow.value
                            .mapIndexed { i, color ->
                                if (i == index) getNextColorForIndex(i, activeRow.value) else color
                            }
                            .toList()
                    },
                    onCheckClick = {
                        rows.value = rows.value + listOf(activeRow.value)
                        if (activeRow.value == solution) {
                            isGameFinished.value = true
                        } else {
                            score.intValue++
                            activeRow.value = emptyRow.toList()
                        }
                    }
                )
            } else {
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