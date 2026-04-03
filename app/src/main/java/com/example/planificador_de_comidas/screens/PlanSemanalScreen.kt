package com.example.planificador_de_comidas.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.planificador_de_comidas.model.Recipe
import com.example.planificador_de_comidas.viewmodel.PlanSemanalViewModel

@Composable
fun PlanSemanalScreen(
    viewModel: PlanSemanalViewModel,
    availableRecipes: List<Recipe>,
    onBackClick: () -> Unit,
    onNavigateToListaCompras: () -> Unit
) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(text = "Plan Semanal", style = MaterialTheme.typography.headlineSmall)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onBackClick,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Volver")
                }
                Button(
                    onClick = onNavigateToListaCompras,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Ver Lista Compras", textAlign = TextAlign.Center)
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(viewModel.diasDeLaSemana) { dia ->
                    val recetaAsignada = viewModel.planSemanal[dia]
                    DayItem(
                        dia = dia,
                        recetaAsignada = recetaAsignada,
                        availableRecipes = availableRecipes,
                        onRecipeSelected = { recipe ->
                            viewModel.asignarReceta(dia, recipe)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun DayItem(
    dia: String,
    recetaAsignada: Recipe?,
    availableRecipes: List<Recipe>,
    onRecipeSelected: (Recipe?) -> Unit
) {
    val showDialog = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { showDialog.value = true },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = dia, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = recetaAsignada?.name ?: "Sin asignar",
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (recetaAsignada == null) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary
                )
            }
        }
    }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("Seleccionar receta para $dia") },
            text = {
                LazyColumn(modifier = Modifier.heightIn(max = 400.dp)) {
                    item {
                        ListItem(
                            headlineContent = { Text("Ninguna (Quitar)") },
                            modifier = Modifier.clickable {
                                onRecipeSelected(null)
                                showDialog.value = false
                            }
                        )
                    }
                    items(availableRecipes) { recipe ->
                        ListItem(
                            headlineContent = { Text(recipe.name) },
                            modifier = Modifier.clickable {
                                onRecipeSelected(recipe)
                                showDialog.value = false
                            }
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showDialog.value = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}
