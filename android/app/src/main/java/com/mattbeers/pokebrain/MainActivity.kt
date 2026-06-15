package com.mattbeers.pokebrain

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mattbeers.pokebrain.ui.theme.PokeBrainTheme
import com.mattbeers.pokebrain.repository.PokemonRepository
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.ui.unit.dp
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
    val pokemonList = PokemonRepository.pokemonList

    Column(
        modifier = modifier
    ) {
        Text("PokeBrain")
        Text("Pokemon Count : ${pokemonList.size}")

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