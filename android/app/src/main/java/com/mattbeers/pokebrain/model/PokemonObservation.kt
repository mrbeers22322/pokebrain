package com.mattbeers.pokebrain.model

data class PokemonObservation(
    val pokemonUuid: String = "",
    val species: String = "",
    val nickname: String? = null,

    val cp: Int? = null,
    val level: Double? = null,

    val attackIv: Int? = null,
    val defenseIv: Int? = null,
    val staminaIv: Int? = null,

    val shiny: Boolean = false,
    val shadow: Boolean = false,
    val purified: Boolean = false,
    val lucky: Boolean = false,

    val legendary: Boolean = false,
    val mythical: Boolean = false,

    val buddy: Boolean = false
)