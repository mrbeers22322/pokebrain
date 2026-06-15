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
import com.mattbeers.pokebrain.ui.components.PokemonRow
import com.mattbeers.pokebrain.ui.components.PokemonStatsSummary
import com.mattbeers.pokebrain.ui.components.SelectedPokemonDetails
import com.mattbeers.pokebrain.ui.theme.PokeBrainTheme
import com.mattbeers.pokebrain.ui.components.ManualPokemonEntryForm

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
            SelectedPokemonDetails(
                pokemon = pokemon,
                onPowerUp = {
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
                },
                onToggleShiny = {
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
                },
                onToggleLucky = {
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
                },
                onToggleBuddy = {
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
                },
                onDelete = {
                    pokemonList.remove(pokemon)
                    selectedPokemon.value = null
                    statusMessage.value = "Deleted ${pokemon.species}."
                }
            )
        } ?: Text("Tap a Pokémon to view details")

        Spacer(modifier = Modifier.height(16.dp))

        ManualPokemonEntryForm(
            onAddPokemon = { enteredSpecies, enteredCp ->
                val newPokemon = PokemonObservation(
                    pokemonUuid = "${enteredSpecies.lowercase()}-${pokemonList.size + 1}",
                    species = enteredSpecies,
                    cp = enteredCp
                )

                pokemonList.add(newPokemon)
                selectedPokemon.value = newPokemon

                statusMessage.value = "Added $enteredSpecies with CP $enteredCp."
            },
            onStatusMessageChange = { message ->
                statusMessage.value = message
            }
        )

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