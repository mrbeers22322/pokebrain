package com.mattbeers.pokebrain.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun ManualPokemonEntryForm(
    onAddPokemon: (species: String, cp: Int) -> Unit,
    onStatusMessageChange: (message: String) -> Unit
) {
    val speciesInput = remember {
        mutableStateOf("")
    }

    val cpInput = remember {
        mutableStateOf("")
    }

    Text("Manual Entry")

    OutlinedTextField(
        value = speciesInput.value,
        onValueChange = {
            speciesInput.value = it
        },
        label = {
            Text("Species")
        },
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(8.dp))

    OutlinedTextField(
        value = cpInput.value,
        onValueChange = {
            cpInput.value = it
        },
        label = {
            Text("CP")
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(8.dp))

    Button(
        onClick = {
            val enteredSpecies = speciesInput.value.trim()
            val enteredCp = cpInput.value.toIntOrNull()

            if (enteredSpecies.isBlank()) {
                onStatusMessageChange("Species cannot be blank.")
                return@Button
            }

            if (enteredCp == null) {
                onStatusMessageChange("CP must be a number.")
                return@Button
            }

            onAddPokemon(enteredSpecies, enteredCp)

            speciesInput.value = ""
            cpInput.value = ""
        }
    ) {
        Text("Add Manual Pokémon")
    }
}