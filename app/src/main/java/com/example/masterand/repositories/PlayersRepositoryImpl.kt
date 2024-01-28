package com.example.masterand.repositories

import com.example.masterand.dao.PlayerDao
import com.example.masterand.entities.Player
import kotlinx.coroutines.flow.Flow

class PlayersRepositoryImpl(private val playerDao: PlayerDao) : PlayersRepository {
    override fun getAllPlayersStream(): Flow<List<Player>> {
        return playerDao.getAllPlayersStream()
    }

    override fun getPlayerStream(id: Int): Flow<Player?> =
        playerDao.getPlayerStream(id)
    override suspend fun getPlayersByEmail(email: String): List<Player> =
        playerDao.getPlayersByEmail(email)
    override suspend fun insertPlayer(player: Player): Long = playerDao.insert(player)
    override suspend fun updatePlayer(player: Player) {
        playerDao.update(player)
    }
}