package com.example.masterand.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SelectableColorsRow() {
    Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
        CircularButton(color = Color.Red, onClick = {})
        CircularButton(color = Color.Green, onClick = {})
        CircularButton(color = Color.Blue, onClick = {})
        CircularButton(color = Color.Yellow, onClick = {})
    }
}

@Composable
@Preview(showBackground = true)
fun SelectableColorsRowPreview() {
    SelectableColorsRow()
}