package com.mattbeers.pokebrain.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
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
        nickname: String?,
        species: String,
        cp: Int,
        level: Double?,
        attackIv: Int?,
        defenseIv: Int?,
        staminaIv: Int?,
        shiny: Boolean,
        shadow: Boolean,
        purified: Boolean,
        lucky: Boolean,
        legendary: Boolean,
        mythical: Boolean,
        buddy: Boolean
    ) -> Unit,
    onStatusMessageChange: (message: String) -> Unit
) {
    val isExpanded = remember { mutableStateOf(false) }

    val speciesInput = remember { mutableStateOf("") }
    val nicknameInput = remember { mutableStateOf("") }
    val cpInput = remember { mutableStateOf("") }
    val levelInput = remember { mutableStateOf("") }
    val attackIvInput = remember { mutableStateOf("") }
    val defenseIvInput = remember { mutableStateOf("") }
    val staminaIvInput = remember { mutableStateOf("") }

    val shinyInput = remember { mutableStateOf(false) }
    val shadowInput = remember { mutableStateOf(false) }
    val purifiedInput = remember { mutableStateOf(false) }
    val luckyInput = remember { mutableStateOf(false) }
    val legendaryInput = remember { mutableStateOf(false) }
    val mythicalInput = remember { mutableStateOf(false) }
    val buddyInput = remember { mutableStateOf(false) }

    Text("Manual Entry")

    Button(
        onClick = {
            isExpanded.value = !isExpanded.value
        }
    ) {
        Text(
            if (isExpanded.value) {
                "Hide Manual Entry"
            } else {
                "Show Manual Entry"
            }
        )
    }

    Spacer(modifier = Modifier.height(8.dp))

    if (isExpanded.value) {
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
            value = nicknameInput.value,
            onValueChange = {
                nicknameInput.value = it
            },
            label = {
                Text("Nickname optional")
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

        Text("Tags")

        TagCheckbox(
            label = "Shiny",
            checked = shinyInput.value,
            onCheckedChange = {
                shinyInput.value = it
            }
        )

        TagCheckbox(
            label = "Shadow",
            checked = shadowInput.value,
            onCheckedChange = {
                shadowInput.value = it
            }
        )

        TagCheckbox(
            label = "Purified",
            checked = purifiedInput.value,
            onCheckedChange = {
                purifiedInput.value = it
            }
        )

        TagCheckbox(
            label = "Lucky",
            checked = luckyInput.value,
            onCheckedChange = {
                luckyInput.value = it
            }
        )

        TagCheckbox(
            label = "Legendary",
            checked = legendaryInput.value,
            onCheckedChange = {
                legendaryInput.value = it
            }
        )

        TagCheckbox(
            label = "Mythical",
            checked = mythicalInput.value,
            onCheckedChange = {
                mythicalInput.value = it
            }
        )

        TagCheckbox(
            label = "Buddy",
            checked = buddyInput.value,
            onCheckedChange = {
                buddyInput.value = it
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                val enteredSpecies = speciesInput.value.trim()
                val enteredNickname = nicknameInput.value.trim().ifBlank {
                    null
                }

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
                    enteredNickname,
                    enteredSpecies,
                    enteredCp,
                    enteredLevel,
                    enteredAttackIv,
                    enteredDefenseIv,
                    enteredStaminaIv,
                    shinyInput.value,
                    shadowInput.value,
                    purifiedInput.value,
                    luckyInput.value,
                    legendaryInput.value,
                    mythicalInput.value,
                    buddyInput.value
                )

                speciesInput.value = ""
                nicknameInput.value = ""
                cpInput.value = ""
                levelInput.value = ""
                attackIvInput.value = ""
                defenseIvInput.value = ""
                staminaIvInput.value = ""

                shinyInput.value = false
                shadowInput.value = false
                purifiedInput.value = false
                luckyInput.value = false
                legendaryInput.value = false
                mythicalInput.value = false
                buddyInput.value = false

                isExpanded.value = false
            }
        ) {
            Text("Add Manual Pokémon")
        }
    }
}

@Composable
fun TagCheckbox(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )

        Text(label)
    }
}