package com.mattbeers.pokebrain

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mattbeers.pokebrain.model.PokemonObservation
import com.mattbeers.pokebrain.repository.PokemonRepository
import com.mattbeers.pokebrain.ui.theme.PokeBrainTheme
import com.mattbeers.pokebrain.ui.components.PokemonRow
import com.mattbeers.pokebrain.ui.components.PokemonStatsSummary

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokeBrainTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PokeBrainScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun PokeBrainScreen(modifier: Modifier = Modifier) {
    val pokemonList = remember {
        mutableStateListOf<PokemonObservation>().apply {
            addAll(PokemonRepository.pokemonList)
        }
    }

    val selectedPokemon = remember {
        mutableStateOf<PokemonObservation?>(null)
    }

    val speciesInput = remember {
        mutableStateOf("")
    }

    val cpInput = remember {
        mutableStateOf("")
    }

    val searchInput = remember {
        mutableStateOf("")
    }

    val sortByHighestCp = remember {
        mutableStateOf(false)
    }

    val statusMessage = remember {
        mutableStateOf("Enter a species and CP to add a Pokémon.")
    }

    fun updateSelectedPokemon(updatedPokemon: PokemonObservation) {
        val selectedIndex = pokemonList.indexOf(selectedPokemon.value)

        if (selectedIndex != -1) {
            pokemonList[selectedIndex] = updatedPokemon
            selectedPokemon.value = updatedPokemon
        }
    }

    val searchedPokemonList = pokemonList.filter { pokemon ->
        pokemon.species.contains(
            searchInput.value.trim(),
            ignoreCase = true
        )
    }

    val filteredPokemonList = if (sortByHighestCp.value) {
        searchedPokemonList.sortedByDescending { pokemon ->
            pokemon.cp ?: 0
        }
    } else {
        searchedPokemonList
    }

    Column(
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("PokeBrain")

        PokemonStatsSummary(
            pokemonList = pokemonList
        )

        Spacer(modifier = Modifier.height(16.dp))

        selectedPokemon.value?.let { pokemon ->
            Text("Selected Pokémon")
            Text("Species : ${pokemon.species}")
            Text("CP : ${pokemon.cp}")
            Text("Level : ${pokemon.level}")
            Text("IVs : ${pokemon.attackIv}/${pokemon.defenseIv}/${pokemon.staminaIv}")

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    val selectedIndex = pokemonList.indexOf(pokemon)

                    if (selectedIndex != -1) {
                        val currentCp = pokemon.cp ?: 0
                        val updatedPokemon = pokemon.copy(
                            cp = currentCp + 50
                        )

                        pokemonList[selectedIndex] = updatedPokemon
                        selectedPokemon.value = updatedPokemon
                        statusMessage.value = "Powered up ${updatedPokemon.species} to CP ${updatedPokemon.cp}."
                    }
                }
            ) {
                Text("Power Up Selected Pokémon")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    val updatedPokemon = pokemon.copy(
                        shiny = !pokemon.shiny
                    )

                    updateSelectedPokemon(updatedPokemon)

                    statusMessage.value =
                        if (updatedPokemon.shiny) {
                            "Marked ${updatedPokemon.species} as shiny."
                        } else {
                            "Removed shiny tag from ${updatedPokemon.species}."
                        }
                }
            ) {
                Text("Toggle Shiny")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    val updatedPokemon = pokemon.copy(
                        lucky = !pokemon.lucky
                    )

                    updateSelectedPokemon(updatedPokemon)

                    statusMessage.value =
                        if (updatedPokemon.lucky) {
                            "Marked ${updatedPokemon.species} as lucky."
                        } else {
                            "Removed lucky tag from ${updatedPokemon.species}."
                        }
                }
            ) {
                Text("Toggle Lucky")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    val updatedPokemon = pokemon.copy(
                        buddy = !pokemon.buddy
                    )

                    updateSelectedPokemon(updatedPokemon)

                    statusMessage.value =
                        if (updatedPokemon.buddy) {
                            "Marked ${updatedPokemon.species} as buddy."
                        } else {
                            "Removed buddy tag from ${updatedPokemon.species}."
                        }
                }
            ) {
                Text("Toggle Buddy")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    pokemonList.remove(pokemon)
                    selectedPokemon.value = null
                    statusMessage.value = "Deleted ${pokemon.species}."
                }
            ) {
                Text("Delete Selected Pokémon")
            }

            Spacer(modifier = Modifier.height(16.dp))
        } ?: Text("Tap a Pokémon to view details")

        Spacer(modifier = Modifier.height(16.dp))

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
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                val enteredSpecies = speciesInput.value.trim()
                val enteredCp = cpInput.value.toIntOrNull()

                if (enteredSpecies.isBlank()) {
                    statusMessage.value = "Species cannot be blank."
                    return@Button
                }

                if (enteredCp == null) {
                    statusMessage.value = "CP must be a number."
                    return@Button
                }

                val newPokemon = PokemonObservation(
                    pokemonUuid = "${enteredSpecies.lowercase()}-${pokemonList.size + 1}",
                    species = enteredSpecies,
                    cp = enteredCp
                )

                pokemonList.add(newPokemon)
                selectedPokemon.value = newPokemon

                speciesInput.value = ""
                cpInput.value = ""

                statusMessage.value = "Added $enteredSpecies with CP $enteredCp."
            }
        ) {
            Text("Add Manual Pokémon")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(statusMessage.value)

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val newPokemon = PokemonObservation(
                    pokemonUuid = "dialga-test-${pokemonList.size + 1}",
                    species = "Dialga",
                    cp = 2988,
                    level = 25.0,
                    attackIv = 13,
                    defenseIv = 15,
                    staminaIv = 8,
                    shadow = true,
                    legendary = true
                )

                pokemonList.add(newPokemon)
                selectedPokemon.value = newPokemon
                statusMessage.value = "Added test Dialga."
            }
        ) {
            Text("Add Test Pokémon")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Search")

        OutlinedTextField(
            value = searchInput.value,
            onValueChange = {
                searchInput.value = it
            },
            label = {
                Text("Search species")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text("Showing ${filteredPokemonList.size} of ${pokemonList.size}")

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                sortByHighestCp.value = !sortByHighestCp.value

                statusMessage.value =
                    if (sortByHighestCp.value) {
                        "Sorted by highest CP."
                    } else {
                        "CP sort cleared."
                    }
            }
        ) {
            Text(
                if (sortByHighestCp.value) {
                    "Clear CP Sort"
                } else {
                    "Sort by Highest CP"
                }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (searchInput.value.isNotBlank()) {
            Button(
                onClick = {
                    searchInput.value = ""
                    statusMessage.value = "Search cleared."
                }
            ) {
                Text("Clear Search")
            }

            Spacer(modifier = Modifier.height(16.dp))
        } else {
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (filteredPokemonList.isEmpty()) {
            Text("No Pokémon match your search.")
        } else {
            filteredPokemonList.forEach { pokemon ->
                PokemonRow(
                    pokemon = pokemon,
                    onClick = {
                        selectedPokemon.value = pokemon
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PokeBrainTheme {
        PokeBrainScreen()
    }
}