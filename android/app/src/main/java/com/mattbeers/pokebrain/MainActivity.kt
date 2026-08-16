package com.mattbeers.pokebrain

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.mattbeers.pokebrain.ui.screens.PokeBrainScreen
import com.mattbeers.pokebrain.ui.theme.PokeBrainTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokeBrainTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PokeBrainScreen(
                        modifier = Modifier.padding(innerPadding),
                        storageContext = LocalContext.current
                    )
                }
            }
        }
    }
}
