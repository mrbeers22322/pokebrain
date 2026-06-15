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
    onAddPokemon: (
        species: String,
        cp: Int,
        level: Double?,
        attackIv: Int?,
        defenseIv: Int?,
        staminaIv: Int?
    ) -> Unit,
    onStatusMessageChange: (message: String) -> Unit
) {
    val speciesInput = remember {
        mutableStateOf("")
    }

    val cpInput = remember {
        mutableStateOf("")
    }

    val levelInput = remember {
        mutableStateOf("")
    }

    val attackIvInput = remember {
        mutableStateOf("")
    }

    val defenseIvInput = remember {
        mutableStateOf("")
    }

    val staminaIvInput = remember {
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

    OutlinedTextField(
        value = levelInput.value,
        onValueChange = {
            levelInput.value = it
        },
        label = {
            Text("Level optional")
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal
        ),
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(8.dp))

    OutlinedTextField(
        value = attackIvInput.value,
        onValueChange = {
            attackIvInput.value = it
        },
        label = {
            Text("Attack IV optional")
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(8.dp))

    OutlinedTextField(
        value = defenseIvInput.value,
        onValueChange = {
            defenseIvInput.value = it
        },
        label = {
            Text("Defense IV optional")
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(8.dp))

    OutlinedTextField(
        value = staminaIvInput.value,
        onValueChange = {
            staminaIvInput.value = it
        },
        label = {
            Text("Stamina IV optional")
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
            val enteredLevel = levelInput.value.toDoubleOrNull()

            val enteredAttackIv = attackIvInput.value.toIntOrNull()
            val enteredDefenseIv = defenseIvInput.value.toIntOrNull()
            val enteredStaminaIv = staminaIvInput.value.toIntOrNull()

            if (enteredSpecies.isBlank()) {
                onStatusMessageChange("Species cannot be blank.")
                return@Button
            }

            if (enteredCp == null) {
                onStatusMessageChange("CP must be a number.")
                return@Button
            }

            if (levelInput.value.isNotBlank() && enteredLevel == null) {
                onStatusMessageChange("Level must be a number.")
                return@Button
            }

            if (enteredLevel != null && enteredLevel !in 1.0..51.0) {
                onStatusMessageChange("Level must be from 1 to 51.")
                return@Button
            }

            if (attackIvInput.value.isNotBlank() && enteredAttackIv == null) {
                onStatusMessageChange("Attack IV must be a number from 0 to 15.")
                return@Button
            }

            if (defenseIvInput.value.isNotBlank() && enteredDefenseIv == null) {
                onStatusMessageChange("Defense IV must be a number from 0 to 15.")
                return@Button
            }

            if (staminaIvInput.value.isNotBlank() && enteredStaminaIv == null) {
                onStatusMessageChange("Stamina IV must be a number from 0 to 15.")
                return@Button
            }

            if (enteredAttackIv != null && enteredAttackIv !in 0..15) {
                onStatusMessageChange("Attack IV must be from 0 to 15.")
                return@Button
            }

            if (enteredDefenseIv != null && enteredDefenseIv !in 0..15) {
                onStatusMessageChange("Defense IV must be from 0 to 15.")
                return@Button
            }

            if (enteredStaminaIv != null && enteredStaminaIv !in 0..15) {
                onStatusMessageChange("Stamina IV must be from 0 to 15.")
                return@Button
            }

            onAddPokemon(
                enteredSpecies,
                enteredCp,
                enteredLevel,
                enteredAttackIv,
                enteredDefenseIv,
                enteredStaminaIv
            )

            speciesInput.value = ""
            cpInput.value = ""
            levelInput.value = ""
            attackIvInput.value = ""
            defenseIvInput.value = ""
            staminaIvInput.value = ""
        }
    ) {
        Text("Add Manual Pokémon")
    }
}