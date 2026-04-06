package com.example.planificador_de_comidas.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.planificador_de_comidas.components.FilaEntradaIngrediente
import com.example.planificador_de_comidas.model.Ingrediente

@Composable
fun CrearRecetaScreen(
    onBackClick: () -> Unit,
    onSaveRecipe: (String, List<Ingrediente>) -> Unit
) {
    var nombreReceta by remember { mutableStateOf("") }
    val entradasIngredientes = remember {
        mutableStateListOf(
            EntradaIngrediente("", "", "unidad")
        )
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(text = "Nueva Receta", style = MaterialTheme.typography.headlineSmall)

            OutlinedTextField(
                value = nombreReceta,
                onValueChange = { nombreReceta = it },
                label = { Text("Nombre de la receta") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = onBackClick,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text("Volver")
            }

            Text("Ingredientes", style = MaterialTheme.typography.titleMedium)

            entradasIngredientes.forEachIndexed { index, entrada ->
                FilaEntradaIngrediente(
                    nombre = entrada.nombre,
                    cantidad = entrada.cantidad,
                    unidad = entrada.unidad,
                    onNombreChange = { nuevoNombre ->
                        entradasIngredientes[index] = entrada.copy(nombre = nuevoNombre)
                    },
                    onCantidadChange = { nuevaCantidad ->
                        entradasIngredientes[index] = entrada.copy(cantidad = nuevaCantidad)
                    },
                    onUnidadChange = { nuevaUnidad ->
                        entradasIngredientes[index] = entrada.copy(unidad = nuevaUnidad)
                    },
                    onEliminarClick = {
                        if (entradasIngredientes.size > 1) {
                            entradasIngredientes.removeAt(index)
                        }
                    }
                )
            }

            Button(
                onClick = { entradasIngredientes.add(EntradaIngrediente("", "", "unidad")) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agregar ingrediente")
            }

            Button(
                onClick = {
                    val ingredientes = entradasIngredientes.mapNotNull {
                        val cantidad = it.cantidad.toDoubleOrNull() ?: 0.0
                        if (it.nombre.isNotBlank() && cantidad > 0) {
                            Ingrediente(nombre = it.nombre.trim(), cantidad = cantidad, unidad = it.unidad)
                        } else null
                    }
                    if (nombreReceta.isNotBlank() && ingredientes.isNotEmpty()) {
                        onSaveRecipe(nombreReceta, ingredientes)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar receta")
            }
        }
    }
}

private data class EntradaIngrediente(
    val nombre: String,
    val cantidad: String,
    val unidad: String
)

