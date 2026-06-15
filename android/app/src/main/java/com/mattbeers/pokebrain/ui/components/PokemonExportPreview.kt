package com.mattbeers.pokebrain.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mattbeers.pokebrain.data.export.pokemonExportCsvHeader
import com.mattbeers.pokebrain.data.export.toCsvLine
import com.mattbeers.pokebrain.data.export.toExportRows
import com.mattbeers.pokebrain.model.PokemonObservation

@Composable
fun PokemonExportPreview(
    pokemonList: List<PokemonObservation>
) {
    val exportRows = pokemonList.toExportRows()
    val previewRows = exportRows.takeLast(5)

    Text("Export Preview")
    Text("Rows ready : ${exportRows.size}")
    Text("Showing newest ${previewRows.size} rows")

    Spacer(modifier = Modifier.height(8.dp))

    Text(pokemonExportCsvHeader())

    Spacer(modifier = Modifier.height(8.dp))

    previewRows.forEach { exportRow ->
        Text(exportRow.toCsvLine())

        Spacer(modifier = Modifier.height(8.dp))
    }
}