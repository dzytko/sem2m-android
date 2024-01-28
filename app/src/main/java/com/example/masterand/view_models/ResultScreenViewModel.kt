package com.example.masterand.view_models

import androidx.lifecycle.ViewModel
import com.example.masterand.entities.PlayerWithScore
import com.example.masterand.repositories.PlayerScoreRepository
import kotlinx.coroutines.runBlocking

class ResultScreenViewModel(
    private val playerScoreRepository: PlayerScoreRepository
): ViewModel() {
    fun fetchResults(): List<PlayerWithScore> {
        var playerScores: List<PlayerWithScore>
        runBlocking {
            playerScores = playerScoreRepository.getPlayersWithScores()
        }
        return playerScores
    }
}