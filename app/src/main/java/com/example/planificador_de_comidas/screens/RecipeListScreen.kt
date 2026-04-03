package com.example.planificador_de_comidas.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.planificador_de_comidas.components.RecipeCard
import com.example.planificador_de_comidas.viewmodel.RecipeViewModel

@Composable
fun RecipeListScreen(
    viewModel: RecipeViewModel,
    onAddRecipeClick: () -> Unit,
    onNavigateToPlanSemanal: () -> Unit
) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text("Lista de recetas", style = MaterialTheme.typography.headlineSmall)

            OutlinedTextField(
                value = viewModel.searchQuery,
                onValueChange = viewModel::updateSearchQuery,
                label = { Text("Buscar por nombre") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = viewModel.ingredientFilter,
                onValueChange = viewModel::updateIngredientFilter,
                label = { Text("Filtrar por ingrediente") },
                modifier = Modifier.fillMaxWidth()
            )

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = onAddRecipeClick,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Agregar receta")
                }
                Button(
                    onClick = onNavigateToPlanSemanal,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Plan Semanal")
                }
            }

            if (viewModel.filteredRecipes.isEmpty()) {
                Text("No hay recetas para mostrar")
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(vertical = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(viewModel.filteredRecipes) { recipe ->
                        RecipeCard(recipe = recipe)
                    }
                }
            }
        }
    }
}
