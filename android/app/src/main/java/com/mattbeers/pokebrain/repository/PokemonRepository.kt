package com.mattbeers.pokebrain.repository

import com.mattbeers.pokebrain.model.PokemonObservation

object PokemonRepository {

    val pokemonList = listOf(

        PokemonObservation(
            pokemonUuid = "charizard-test-001",
            species = "Charizard",
            cp = 3024,
            level = 43.0,
            attackIv = 14,
            defenseIv = 15,
            staminaIv = 15,
            buddy = true
        ),

        PokemonObservation(
            pokemonUuid = "groudon-test-001",
            species = "Groudon",
            cp = 3998,
            level = 38.0,
            attackIv = 15,
            defenseIv = 15,
            staminaIv = 15,
            lucky = true,
            legendary = true,
            buddy = true
        ),

        PokemonObservation(
            pokemonUuid = "tyranitar-test-001",
            species = "Tyranitar",
            cp = 4156,
            level = 46.5,
            attackIv = 15,
            defenseIv = 15,
            staminaIv = 15
        )

    )
}