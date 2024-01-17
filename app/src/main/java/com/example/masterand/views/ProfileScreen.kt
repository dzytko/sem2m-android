package com.example.masterand.views

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.outlined.ImageSearch
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.masterand.R
import com.example.masterand.utils.allAvailableColors
import com.example.masterand.utils.currentColorSet

@Composable
fun OutlinedTextFieldWithError(
    label: String,
    value: MutableState<String>,
    error: MutableState<String>,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(modifier = Modifier.fillMaxWidth(),
        value = value.value,
        onValueChange = { value.value = it },
        trailingIcon = {
            if (error.value.isNotEmpty()) {
                Icon(Icons.Filled.Error, "Error", tint = MaterialTheme.colorScheme.error)
            }
        },
        label = { Text(label) },
        singleLine = true,
        isError = error.value.isNotEmpty(),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        supportingText = { Text(AnnotatedString(error.value)) }
    )
}

@Composable
fun ProfileImageWithPicker(profileImageUri: Uri?, selectImageOnClick: () -> Unit) {
    val defaultImageUri =
        Uri.parse("android.resource://com.example.masterand/drawable/" + R.drawable.baseline_question_mark_24)
    Box {
        AsyncImage(
            model = profileImageUri ?: defaultImageUri,
            contentDescription = "Profile image",
            modifier = Modifier
                .height(150.dp)
                .height(150.dp)
        )
        IconButton(
            onClick = selectImageOnClick,
            modifier = Modifier
                .align(Alignment.TopEnd)
        ) {
            Icon(Icons.Outlined.ImageSearch, "Select image")
        }
    }
}

fun validateName(name: String): String {
    return if (name.isEmpty()) {
        "Name cannot be empty"
    } else {
        ""
    }
}

fun validateEmail(email: String): String {
    return if (email.isEmpty()) {
        "Email cannot be empty"
    } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        "Email is not valid"
    } else {
        ""
    }
}

fun validateNumberOfColorsToGuess(numberOfColorsToGuess: String): String {
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

@Composable
fun ProfileScreenInitial(onStartGame: () -> Unit = {}) {
    val infiniteTransition = rememberInfiniteTransition(label = "Title anim transition")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Title animation"
    )


    val name = rememberSaveable { mutableStateOf("") }
    val nameError = rememberSaveable { mutableStateOf("") }

    val email = rememberSaveable { mutableStateOf("") }
    val emailError = rememberSaveable { mutableStateOf("") }

    val numberOfColorsToGuess = rememberSaveable { mutableStateOf("") }
    val numberOfColorsToGuessError = rememberSaveable { mutableStateOf("") }

    val profileImageUri = rememberSaveable { mutableStateOf<Uri?>(null) }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { selectedUri ->
            if (selectedUri != null) {
                profileImageUri.value = selectedUri
            }
        })

    fun validateAndStartGame() {
        nameError.value = validateName(name.value)
        emailError.value = validateEmail(email.value)
        numberOfColorsToGuessError.value = validateNumberOfColorsToGuess(numberOfColorsToGuess.value)

        if (nameError.value.isEmpty() && emailError.value.isEmpty() && numberOfColorsToGuessError.value.isEmpty()) {
            // magic global variable
            currentColorSet = allAvailableColors.take(numberOfColorsToGuess.value.toInt())
            onStartGame()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "MasterAnd",
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier
                .padding(bottom = 48.dp)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    transformOrigin = TransformOrigin.Center
                }
        )

        ProfileImageWithPicker(
            profileImageUri = profileImageUri.value,
            selectImageOnClick = {
                imagePicker.launch(
                    PickVisualMediaRequest(
                        ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextFieldWithError(label = "Name", value = name, error = nameError)
        OutlinedTextFieldWithError(
            label = "Email",
            value = email,
            error = emailError,
            keyboardType = KeyboardType.Email
        )
        OutlinedTextFieldWithError(
            label = "Number of colors to guess",
            value = numberOfColorsToGuess,
            error = numberOfColorsToGuessError,
            keyboardType = KeyboardType.Number
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier.fillMaxSize(),
            onClick = { validateAndStartGame() },
        ) {

            Text(text = "Next")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenInitialPreview() {
    ProfileScreenInitial()
}