package com.mattbeers.pokebrain.data.export

import com.mattbeers.pokebrain.model.PokemonObservation
import com.mattbeers.pokebrain.util.formatPokemonIvPercent
import com.mattbeers.pokebrain.util.formatPokemonLevel

fun PokemonObservation.toExportRow(): PokemonExportRow {
    return PokemonExportRow(
        pokemonUuid = pokemonUuid,
        species = species,
        nickname = nickname ?: "",
        cp = cp?.toString() ?: "",
        level = formatPokemonLevel(this),
        attackIv = attackIv?.toString() ?: "",
        defenseIv = defenseIv?.toString() ?: "",
        staminaIv = staminaIv?.toString() ?: "",
        ivPercent = formatPokemonIvPercent(this),
        shiny = shiny.toExportBoolean(),
        shadow = shadow.toExportBoolean(),
        purified = purified.toExportBoolean(),
        lucky = lucky.toExportBoolean(),
        legendary = legendary.toExportBoolean(),
        mythical = mythical.toExportBoolean(),
        buddy = buddy.toExportBoolean()
    )
}

fun List<PokemonObservation>.toExportRows(): List<PokemonExportRow> {
    return map { pokemon ->
        pokemon.toExportRow()
    }
}

fun PokemonExportRow.toCsvLine(): String {
    return listOf(
        pokemonUuid,
        species,
        nickname,
        cp,
        level,
        attackIv,
        defenseIv,
        staminaIv,
        ivPercent,
        shiny,
        shadow,
        purified,
        lucky,
        legendary,
        mythical,
        buddy
    ).joinToString(",")
}

fun pokemonExportCsvHeader(): String {
    return listOf(
        "pokemon_uuid",
        "species",
        "nickname",
        "cp",
        "level",
        "attack_iv",
        "defense_iv",
        "stamina_iv",
        "iv_percent",
        "shiny",
        "shadow",
        "purified",
        "lucky",
        "legendary",
        "mythical",
        "buddy"
    ).joinToString(",")
}

private fun Boolean.toExportBoolean(): String {
    return if (this) {
        "TRUE"
    } else {
        "FALSE"
    }
}