package com.example.masterand.repositories

import com.example.masterand.dao.ScoreDao
import com.example.masterand.entities.Score


class ScoreRepositoryImpl(private val scoreDao: ScoreDao) : ScoreRepository {
    override suspend fun insertScore(score: Score) {
        scoreDao.insert(score)
    }
}