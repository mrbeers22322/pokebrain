package com.mattbeers.pokebrain.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mattbeers.pokebrain.model.PokemonObservation

@Composable
fun SelectedPokemonDetails(
    pokemon: PokemonObservation,
    onPowerUp: () -> Unit,
    onToggleShiny: () -> Unit,
    onToggleLucky: () -> Unit,
    onToggleBuddy: () -> Unit,
    onDelete: () -> Unit
) {
    Column {
        Text("Selected Pokémon")
        Text("Species : ${pokemon.species}")
        Text("CP : ${pokemon.cp}")
        Text("Level : ${pokemon.level}")
        Text("IVs : ${pokemon.attackIv}/${pokemon.defenseIv}/${pokemon.staminaIv}")

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onPowerUp
        ) {
            Text("Power Up Selected Pokémon")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onToggleShiny
        ) {
            Text("Toggle Shiny")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onToggleLucky
        ) {
            Text("Toggle Lucky")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onToggleBuddy
        ) {
            Text("Toggle Buddy")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onDelete
        ) {
            Text("Delete Selected Pokémon")
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}