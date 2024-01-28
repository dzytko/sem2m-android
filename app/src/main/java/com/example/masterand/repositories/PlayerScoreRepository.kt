package com.example.masterand.repositories

import com.example.masterand.entities.PlayerWithScore

interface PlayerScoreRepository {
    suspend fun getPlayersWithScores(): List<PlayerWithScore>
}