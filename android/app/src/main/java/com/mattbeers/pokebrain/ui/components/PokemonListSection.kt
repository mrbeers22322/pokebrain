package com.mattbeers.pokebrain.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mattbeers.pokebrain.model.PokemonObservation

@Composable
fun PokemonListSection(
    pokemonList: List<PokemonObservation>,
    onPokemonSelected: (PokemonObservation) -> Unit
) {
    if (pokemonList.isEmpty()) {
        Text("No Pokémon match your search.")
    } else {
        pokemonList.forEach { pokemon ->
            PokemonRow(
                pokemon = pokemon,
                onClick = {
                    onPokemonSelected(pokemon)
                }
            )

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}