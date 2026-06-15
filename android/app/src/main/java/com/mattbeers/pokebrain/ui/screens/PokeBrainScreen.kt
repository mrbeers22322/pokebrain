package com.mattbeers.pokebrain.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import com.mattbeers.pokebrain.ui.components.ManualPokemonEntryForm
import com.mattbeers.pokebrain.ui.components.PokemonListSection
import com.mattbeers.pokebrain.ui.components.PokemonSearchControls
import com.mattbeers.pokebrain.ui.components.PokemonStatsSummary
import com.mattbeers.pokebrain.ui.components.SelectedPokemonDetails
import com.mattbeers.pokebrain.ui.theme.PokeBrainTheme

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

        PokemonSearchControls(
            searchText = searchInput.value,
            onSearchTextChange = { newSearchText ->
                searchInput.value = newSearchText
            },
            showingCount = filteredPokemonList.size,
            totalCount = pokemonList.size,
            sortByHighestCp = sortByHighestCp.value,
            onToggleHighestCpSort = {
                sortByHighestCp.value = !sortByHighestCp.value

                statusMessage.value =
                    if (sortByHighestCp.value) {
                        "Sorted by highest CP."
                    } else {
                        "CP sort cleared."
                    }
            },
            onClearSearch = {
                searchInput.value = ""
                statusMessage.value = "Search cleared."
            }
        )

        PokemonListSection(
            pokemonList = filteredPokemonList,
            onPokemonSelected = { pokemon ->
                selectedPokemon.value = pokemon
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PokeBrainScreenPreview() {
    PokeBrainTheme {
        PokeBrainScreen()
    }
}