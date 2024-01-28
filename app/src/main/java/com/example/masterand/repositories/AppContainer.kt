package com.example.masterand.repositories

import android.content.Context
import com.example.masterand.data.HighScoreDatabase

interface AppContainer {
    val playersRepository: PlayersRepository
    val playerScoreRepository: PlayerScoreRepository
    val scoreRepository: ScoreRepository
}
class AppDataContainer(private val context: Context) : AppContainer {
    override val playersRepository: PlayersRepository by lazy {
        PlayersRepositoryImpl(HighScoreDatabase.getDatabase(context).playerDao())
    }
    override val playerScoreRepository: PlayerScoreRepository by lazy {
        PlayerScoreRepositoryImpl(HighScoreDatabase.getDatabase(context).playerScoreDao())
    }
    override val scoreRepository: ScoreRepository by lazy {
        ScoreRepositoryImpl(HighScoreDatabase.getDatabase(context).scoreDao())
    }
}