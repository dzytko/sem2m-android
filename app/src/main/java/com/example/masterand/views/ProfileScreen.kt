package com.example.masterand.views

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.masterand.R

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

@Composable
fun ProfileScreenInitial(onStartGame: () -> Unit = {}) {
    val name = rememberSaveable { mutableStateOf("") }
    val nameError = rememberSaveable { mutableStateOf("") }

    val email = rememberSaveable { mutableStateOf("") }
    val emailError = rememberSaveable { mutableStateOf("Err") }

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
            modifier = Modifier.padding(bottom = 48.dp)
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
        Button(
            modifier = Modifier.fillMaxSize(),
            onClick = onStartGame
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