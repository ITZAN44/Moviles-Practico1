package com.example.planificador_de_comidas.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.planificador_de_comidas.components.RecipeCard
import com.example.planificador_de_comidas.viewmodel.RecipeViewModel

@Composable
fun RecipeListScreen(
    viewModel: RecipeViewModel,
    onAddRecipeClick: () -> Unit
) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text("Lista de recetas")

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

            Button(
                onClick = onAddRecipeClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agregar receta")
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



