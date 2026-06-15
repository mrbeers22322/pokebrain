package com.mattbeers.pokebrain.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PokemonSearchControls(
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    showingCount: Int,
    totalCount: Int,
    sortByHighestCp: Boolean,
    onToggleHighestCpSort: () -> Unit,
    onClearSearch: () -> Unit
) {
    Text("Search")

    OutlinedTextField(
        value = searchText,
        onValueChange = onSearchTextChange,
        label = {
            Text("Search species")
        },
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(8.dp))

    Text("Showing $showingCount of $totalCount")

    Spacer(modifier = Modifier.height(8.dp))

    Button(
        onClick = onToggleHighestCpSort
    ) {
        Text(
            if (sortByHighestCp) {
                "Clear CP Sort"
            } else {
                "Sort by Highest CP"
            }
        )
    }

    Spacer(modifier = Modifier.height(8.dp))

    if (searchText.isNotBlank()) {
        Button(
            onClick = onClearSearch
        ) {
            Text("Clear Search")
        }

        Spacer(modifier = Modifier.height(16.dp))
    } else {
        Spacer(modifier = Modifier.height(8.dp))
    }
}