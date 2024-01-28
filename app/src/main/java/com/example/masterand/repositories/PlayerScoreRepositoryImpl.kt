package com.example.masterand.repositories

import com.example.masterand.dao.PlayerScoreDao
import com.example.masterand.entities.PlayerWithScore

class PlayerScoreRepositoryImpl(private val playerScoreDao: PlayerScoreDao): PlayerScoreRepository {
    override suspend fun getPlayersWithScores(): List<PlayerWithScore> {
        return playerScoreDao.loadPlayersWithScores()
    }
}