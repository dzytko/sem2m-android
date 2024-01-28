package com.example.masterand.view_models

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.masterand.entities.Score
import com.example.masterand.repositories.ScoreRepository
import com.example.masterand.utils.emptyRow
import com.example.masterand.utils.generateRandomColors
import com.example.masterand.utils.loggedInPlayerId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainGameViewModel(private val scoreRepository: ScoreRepository): ViewModel() {
    val solution = generateRandomColors()

    val score = mutableIntStateOf(1)
    val isGameFinished = mutableStateOf(false)

    val rows = mutableStateOf(listOf(emptyRow))
    val feedbacks = mutableStateOf(listOf<List<Color>>())

    fun navigateToScoreScreen( onScoreScreen: (String) -> Unit = {}) {
        CoroutineScope(Dispatchers.IO).launch {
            scoreRepository.insertScore(Score(playerId = loggedInPlayerId!!, score = score.intValue))
        }
        onScoreScreen(score.intValue.toString())
    }


}