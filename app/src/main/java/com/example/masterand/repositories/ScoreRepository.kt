package com.example.masterand.repositories

import com.example.masterand.entities.Score

interface ScoreRepository {
    suspend fun insertScore(score: Score)
}