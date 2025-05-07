package com.muhammadfaishalrizqipratama0094.cookit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.muhammadfaishalrizqipratama0094.cookit.navigation.SetupNavGraph
import com.muhammadfaishalrizqipratama0094.cookit.ui.theme.CookITTheme
import com.muhammadfaishalrizqipratama0094.cookit.viewmodel.ResepViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: ResepViewModel = viewModel()

            CookITTheme(viewModel = viewModel) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SetupNavGraph(resepViewModel = viewModel)
                }
            }
        }
    }
}