package com.example.masterand.utils

import androidx.compose.ui.graphics.Color

val emptyRow = List(4) { Color.White }
val allAvailableColors = listOf(
    Color.White,
    Color.Red,
    Color.Green,
    Color.Blue,
    Color.Yellow,
    Color.Magenta,
    Color.Cyan,
    Color.Gray,
    Color.Black,
    Color.DarkGray
)

// magic global variables
var currentColorSet = allAvailableColors
var loggedInPlayerId: Long? = null

fun nextColor(color: Color): Color {
    val index = currentColorSet.indexOfFirst { it == color }
    return currentColorSet[(index + 1) % currentColorSet.size]
}

fun getNextColorForIndex(index: Int, selectedColors: List<Color>): Color {
    var newColor = nextColor(selectedColors[index])
    while (selectedColors.contains(newColor)) {
        newColor = nextColor(newColor)
    }
    return newColor
}

fun checkGuess(guess: List<Color>, solution: List<Color>): List<Color> {
    val feedback = MutableList(4) { Color.White }
    for ((i, color) in guess.withIndex()) {
        when (solution.indexOf(color)) {
            -1 -> feedback[i] = Color.White
            i -> feedback[i] = Color.Red
            else -> feedback[i] = Color.Yellow
        }
    }

    return feedback
}

fun generateRandomColors(): List<Color> {
    return currentColorSet.shuffled().take(4)
}