package com.example.planificador_de_comidas.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.planificador_de_comidas.model.Receta

@Composable
fun TarjetaReceta(receta: Receta) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(text = receta.nombre)

            receta.ingredientes.forEach { ingrediente ->
                Text(text = "- ${ingrediente.nombre}: ${ingrediente.cantidad}")
            }
        }
    }
}



