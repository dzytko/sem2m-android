package com.example.masterand.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.masterand.entities.PlayerWithScore

@Dao
interface PlayerScoreDao {
    @Query(
        "SELECT DISTINCT players.playerId AS playerId, scores.scoreId AS scoreId, players.name AS name, players.email AS email, scores.score AS score " +
                "FROM players, scores WHERE players.playerId = scores.playerId GROUP BY  players.playerId, name, email, score  ORDER BY score ASC"
    )
    suspend fun loadPlayersWithScores():List<PlayerWithScore>
}
