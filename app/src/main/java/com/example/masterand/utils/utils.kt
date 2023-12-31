package com.example.masterand.utils

import androidx.compose.ui.graphics.Color

enum class AvailableColors(val value: Color) {
    Red(Color.Red),
    Green(Color.Green),
    Blue(Color.Blue),
    Yellow(Color.Yellow),
    Cyan(Color.Cyan),
    Magenta(Color.Magenta),
    White(Color.White),
    Black(Color.Black),
}

fun nextColor(color: Color): Color {
    val allColors = AvailableColors.entries
    val index = allColors.indexOfFirst { it.value == color }
    return allColors[(index + 1) % allColors.size].value
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
    // get random colors without duplicates
    return AvailableColors.entries.shuffled().take(4).map { it.value }
}