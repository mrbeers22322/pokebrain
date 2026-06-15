package com.mattbeers.pokebrain.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.mattbeers.pokebrain.data.export.pokemonExportCsvHeader
import com.mattbeers.pokebrain.data.export.toCsvLine
import com.mattbeers.pokebrain.data.export.toExportRows
import com.mattbeers.pokebrain.data.export.toPokemonExportCsv
import com.mattbeers.pokebrain.model.PokemonObservation

@Composable
fun PokemonExportPreview(
    pokemonList: List<PokemonObservation>
) {
    val context = LocalContext.current
    val exportRows = pokemonList.toExportRows()
    val previewRows = exportRows.takeLast(5)
    val fullCsvText = pokemonList.toPokemonExportCsv()

    val copyStatusMessage = remember {
        mutableStateOf("")
    }

    Text("Export Preview")
    Text("Rows ready : ${exportRows.size}")
    Text("Showing newest ${previewRows.size} rows")
    Text("CSV characters : ${fullCsvText.length}")

    Spacer(modifier = Modifier.height(8.dp))

    Button(
        onClick = {
            val clipboardManager =
                context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

            val clipData = ClipData.newPlainText(
                "PokeBrain Pokemon Export CSV",
                fullCsvText
            )

            clipboardManager.setPrimaryClip(clipData)

            copyStatusMessage.value = "Copied ${exportRows.size} Pokémon rows to clipboard."
        }
    ) {
        Text("Copy CSV to Clipboard")
    }

    Spacer(modifier = Modifier.height(8.dp))

    Button(
        onClick = {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/csv"
                putExtra(Intent.EXTRA_SUBJECT, "PokeBrain Pokemon Export CSV")
                putExtra(Intent.EXTRA_TEXT, fullCsvText)
            }

            context.startActivity(
                Intent.createChooser(
                    shareIntent,
                    "Share PokeBrain CSV"
                )
            )
        }
    ) {
        Text("Share CSV")
    }

    if (copyStatusMessage.value.isNotBlank()) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(copyStatusMessage.value)
    }

    Spacer(modifier = Modifier.height(8.dp))

    Text(pokemonExportCsvHeader())

    Spacer(modifier = Modifier.height(8.dp))

    previewRows.forEach { exportRow ->
        Text(exportRow.toCsvLine())

        Spacer(modifier = Modifier.height(8.dp))
    }
}