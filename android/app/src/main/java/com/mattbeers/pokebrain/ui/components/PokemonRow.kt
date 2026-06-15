package com.mattbeers.pokebrain.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mattbeers.pokebrain.model.PokemonObservation
import com.mattbeers.pokebrain.util.formatPokemonIvPercent
import com.mattbeers.pokebrain.util.formatPokemonIvs
import com.mattbeers.pokebrain.util.formatPokemonLevel

@Composable
fun PokemonRow(
    pokemon: PokemonObservation,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            val levelText = formatPokemonLevel(pokemon)
            val ivText = formatPokemonIvs(pokemon)
            val ivPercentText = formatPokemonIvPercent(pokemon)

            Text("${pokemon.species}  CP ${pokemon.cp}")
            Text("Level $levelText  IVs $ivText")
            Text("IV % : $ivPercentText")

            val tags = listOfNotNull(
                if (pokemon.shiny) "Shiny" else null,
                if (pokemon.shadow) "Shadow" else null,
                if (pokemon.purified) "Purified" else null,
                if (pokemon.lucky) "Lucky" else null,
                if (pokemon.legendary) "Legendary" else null,
                if (pokemon.mythical) "Mythical" else null,
                if (pokemon.buddy) "Buddy" else null
            ).joinToString(" • ")

            if (tags.isNotBlank()) {
                Text("Tags : $tags")
            }
        }
    }
}