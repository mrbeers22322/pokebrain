package com.mattbeers.pokebrain

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mattbeers.pokebrain.ui.theme.PokeBrainTheme
import androidx.compose.foundation.layout.Column
import com.mattbeers.pokebrain.model.PokemonObservation
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

    val firstPokemon = PokemonObservation(
        pokemonUuid = "charizard-test-001",
        species = "Charizard",
        cp = 3024,
        level = 43.0,
        attackIv = 14,
        defenseIv = 15,
        staminaIv = 15,
        buddy = true
    )

    val pokemonCount = 1

    Column(
        modifier = modifier
    ) {
        Text("PokeBrain")
        Text("Pokemon Count : $pokemonCount")
        Text("First Pokemon : ${firstPokemon.species}")
        Text("CP : ${firstPokemon.cp}")
        Text("IVs : ${firstPokemon.attackIv}/${firstPokemon.defenseIv}/${firstPokemon.staminaIv}")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PokeBrainTheme {
        Greeting()
    }
}