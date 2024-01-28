package com.example.masterand.entities

data class PlayerWithScore(
    val scoreId: Long,
    val playerId: Long,
    val email: String,
    val name: String,
    val score: Int,
)
