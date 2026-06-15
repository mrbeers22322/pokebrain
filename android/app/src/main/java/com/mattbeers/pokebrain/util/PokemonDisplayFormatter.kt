package com.mattbeers.pokebrain.util

import com.mattbeers.pokebrain.model.PokemonObservation

fun formatPokemonLevel(
    pokemon: PokemonObservation
): String {
    return pokemon.level?.toString() ?: "Unknown"
}

fun formatPokemonIvs(
    pokemon: PokemonObservation
): String {
    if (
        pokemon.attackIv == null ||
        pokemon.defenseIv == null ||
        pokemon.staminaIv == null
    ) {
        return "Unknown"
    }

    return "${pokemon.attackIv}/${pokemon.defenseIv}/${pokemon.staminaIv}"
}

fun formatPokemonIvPercent(
    pokemon: PokemonObservation
): String {
    val attackIv = pokemon.attackIv
    val defenseIv = pokemon.defenseIv
    val staminaIv = pokemon.staminaIv

    if (
        attackIv == null ||
        defenseIv == null ||
        staminaIv == null
    ) {
        return "Unknown"
    }

    val totalIv = attackIv + defenseIv + staminaIv
    val percentTimesTen = totalIv * 1000 / 45
    val wholeNumber = percentTimesTen / 10
    val decimalNumber = percentTimesTen % 10

    return "$wholeNumber.$decimalNumber%"
}