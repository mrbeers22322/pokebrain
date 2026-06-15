package com.mattbeers.pokebrain

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mattbeers.pokebrain.model.PokemonObservation
import com.mattbeers.pokebrain.repository.PokemonRepository
import com.mattbeers.pokebrain.ui.theme.PokeBrainTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokeBrainTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    val pokemonList = remember {
        mutableStateListOf<PokemonObservation>().apply {
            addAll(PokemonRepository.pokemonList)
        }
    }

    Column(
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("PokeBrain")
        Text("Pokemon Count : ${pokemonList.size}")

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                pokemonList.add(
                    PokemonObservation(
                        pokemonUuid = "dialga-test-${pokemonList.size + 1}",
                        species = "Dialga",
                        cp = 2988,
                        level = 25.0,
                        attackIv = 13,
                        defenseIv = 15,
                        staminaIv = 8,
                        shadow = true,
                        legendary = true
                    )
                )
            }
        ) {
            Text("Add Test Pokémon")
        }

        Spacer(modifier = Modifier.height(16.dp))

        pokemonList.forEach { pokemon ->
            Text("${pokemon.species}  CP ${pokemon.cp}")
            Text("Level ${pokemon.level}  IVs ${pokemon.attackIv}/${pokemon.defenseIv}/${pokemon.staminaIv}")

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PokeBrainTheme {
        Greeting()
    }
}