package com.mattbeers.pokebrain.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mattbeers.pokebrain.model.PokemonObservation

@Composable
fun PokemonStatsSummary(
    pokemonList: List<PokemonObservation>
) {
    val totalCp = pokemonList.sumOf { pokemon ->
        pokemon.cp ?: 0
    }

    val averageCp = if (pokemonList.isNotEmpty()) {
        totalCp / pokemonList.size
    } else {
        0
    }

    val highestCpPokemon = pokemonList.maxByOrNull { pokemon ->
        pokemon.cp ?: 0
    }

    val shinyCount = pokemonList.count { pokemon ->
        pokemon.shiny
    }

    val luckyCount = pokemonList.count { pokemon ->
        pokemon.lucky
    }

    val shadowCount = pokemonList.count { pokemon ->
        pokemon.shadow
    }

    val legendaryCount = pokemonList.count { pokemon ->
        pokemon.legendary
    }

    val buddyCount = pokemonList.count { pokemon ->
        pokemon.buddy
    }

    Text("Pokemon Count : ${pokemonList.size}")
    Text("Total CP : $totalCp")
    Text("Average CP : $averageCp")

    highestCpPokemon?.let { pokemon ->
        Text("Highest CP : ${pokemon.species} ${pokemon.cp}")
    }

    Spacer(modifier = Modifier.height(8.dp))

    Text("Tag Stats")
    Text("Shiny : $shinyCount")
    Text("Lucky : $luckyCount")
    Text("Shadow : $shadowCount")
    Text("Legendary : $legendaryCount")
    Text("Buddy : $buddyCount")
}