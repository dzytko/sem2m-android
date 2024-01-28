package com.example.masterand.view_models

import android.net.Uri
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.masterand.entities.Player
import com.example.masterand.repositories.PlayersRepository
import com.example.masterand.utils.allAvailableColors
import com.example.masterand.utils.currentColorSet
import com.example.masterand.utils.loggedInPlayerId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel(private val playersRepository: PlayersRepository) : ViewModel() {
    private val playerId = mutableLongStateOf(0L)
    val name = mutableStateOf("")
    val email = mutableStateOf("")
    val numberOfColorsToGuess = mutableStateOf("")
    val profileImageUri = mutableStateOf<Uri?>(null)

    val nameError = mutableStateOf("")
    val emailError = mutableStateOf("")
    val numberOfColorsToGuessError = mutableStateOf("")

    fun validateAndSaveAndStartGame(onStartGame: () -> Unit = {}) {
        nameError.value = validateName(name.value)
        emailError.value = validateEmail(email.value)
        numberOfColorsToGuessError.value =
            validateNumberOfColorsToGuess(numberOfColorsToGuess.value)

        if (nameError.value.isEmpty() && emailError.value.isEmpty() && numberOfColorsToGuessError.value.isEmpty()) {
            // magic global variable
            currentColorSet = allAvailableColors.take(numberOfColorsToGuess.value.toInt())
            CoroutineScope(Dispatchers.IO).launch {
                savePlayer()
            }
            onStartGame()
        }
    }

    private fun validateName(name: String): String {
        return if (name.isEmpty()) {
            "Name cannot be empty"
        } else {
            ""
        }
    }

    private fun validateEmail(email: String): String {
        return if (email.isEmpty()) {
            "Email cannot be empty"
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            "Email is not valid"
        } else {
            ""
        }
    }

    private fun validateNumberOfColorsToGuess(numberOfColorsToGuess: String): String {
        return if (numberOfColorsToGuess.isEmpty()) {
            "Number of colors to guess cannot be empty"
        } else if (numberOfColorsToGuess.toIntOrNull() == null) {
            "Number of colors to guess must be a number"
        } else if (numberOfColorsToGuess.toInt() !in 5..10) {
            "Number of colors to guess must be between 5 and 10"
        } else {
            ""
        }
    }

    private suspend fun savePlayer() {
        withContext(Dispatchers.IO) {
            val players = playersRepository.getPlayersByEmail(email.value)
            if (players.isNotEmpty()) {
                playerId.longValue = players.first().playerId
                loggedInPlayerId = playerId.longValue
                val player = Player(playerId=playerId.longValue, name = name.value, email = email.value)
                playersRepository.updatePlayer(player)

                return@withContext
            }
            val player = Player(name = name.value, email = email.value)
            playerId.longValue = playersRepository.insertPlayer(player)
            loggedInPlayerId = playerId.longValue
        }
    }
}