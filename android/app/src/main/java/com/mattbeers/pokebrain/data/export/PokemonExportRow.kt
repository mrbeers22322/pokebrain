package com.mattbeers.pokebrain.data.export

data class PokemonExportRow(
    val pokemonUuid: String,
    val species: String,
    val nickname: String,
    val cp: String,
    val level: String,
    val attackIv: String,
    val defenseIv: String,
    val staminaIv: String,
    val ivPercent: String,
    val shiny: String,
    val shadow: String,
    val purified: String,
    val lucky: String,
    val legendary: String,
    val mythical: String,
    val buddy: String
)