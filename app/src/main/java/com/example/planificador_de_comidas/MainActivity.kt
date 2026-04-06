package com.example.planificador_de_comidas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.planificador_de_comidas.navigation.AppNavGraph
import com.example.planificador_de_comidas.ui.theme.PlanificadordecomidasTheme
import com.example.planificador_de_comidas.viewmodel.RecetaViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlanificadordecomidasTheme {
                val recetaViewModel: RecetaViewModel = viewModel()
                AppNavGraph(recetaViewModel = recetaViewModel)
            }
        }
    }
}
