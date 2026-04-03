package com.example.planificador_de_comidas.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.planificador_de_comidas.components.IngredientInputRow
import com.example.planificador_de_comidas.model.Ingredient

@Composable
fun CreateRecipeScreen(
    onBackClick: () -> Unit,
    onSaveRecipe: (String, List<Ingredient>) -> Unit
) {
    var recipeName by remember { mutableStateOf("") }
    val ingredientInputs = remember {
        mutableStateListOf(
            IngredientInput("", "", "unidad")
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
                value = recipeName,
                onValueChange = { recipeName = it },
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

            ingredientInputs.forEachIndexed { index, input ->
                IngredientInputRow(
                    name = input.name,
                    quantity = input.quantity,
                    unit = input.unit,
                    onNameChange = { newName ->
                        ingredientInputs[index] = input.copy(name = newName)
                    },
                    onQuantityChange = { newQuantity ->
                        ingredientInputs[index] = input.copy(quantity = newQuantity)
                    },
                    onUnitChange = { newUnit ->
                        ingredientInputs[index] = input.copy(unit = newUnit)
                    },
                    onDeleteClick = {
                        if (ingredientInputs.size > 1) {
                            ingredientInputs.removeAt(index)
                        }
                    }
                )
            }

            Button(
                onClick = { ingredientInputs.add(IngredientInput("", "", "unidad")) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agregar ingrediente")
            }

            Button(
                onClick = {
                    val ingredients = ingredientInputs.mapNotNull {
                        val qty = it.quantity.toDoubleOrNull() ?: 0.0
                        if (it.name.isNotBlank() && qty > 0) {
                            Ingredient(name = it.name.trim(), quantity = qty, unit = it.unit)
                        } else null
                    }
                    if (recipeName.isNotBlank() && ingredients.isNotEmpty()) {
                        onSaveRecipe(recipeName, ingredients)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar receta")
            }
        }
    }
}

private data class IngredientInput(
    val name: String,
    val quantity: String,
    val unit: String
)
