package com.example.planificador_de_comidas.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
            IngredientInput("", "")
        )
    }

    Scaffold(
        topBar = {}
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextField(
                value = recipeName,
                onValueChange = { recipeName = it },
                label = { Text("Nombre de la receta") },
                modifier = Modifier.fillMaxWidth()
            )

            TextButton(onClick = onBackClick) {
                Text("Volver")
            }

            Text("Ingredientes")

            ingredientInputs.forEachIndexed { index, input ->
                IngredientInputRow(
                    name = input.name,
                    quantity = input.quantity,
                    onNameChange = { newName ->
                        ingredientInputs[index] = input.copy(name = newName)
                    },
                    onQuantityChange = { newQuantity ->
                        ingredientInputs[index] = input.copy(quantity = newQuantity)
                    },
                    onDeleteClick = {
                        if (ingredientInputs.size > 1) {
                            ingredientInputs.removeAt(index)
                        }
                    }
                )
            }

            Button(
                onClick = { ingredientInputs.add(IngredientInput("", "")) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agregar ingrediente")
            }

            Button(
                onClick = {
                    val ingredients = ingredientInputs.map {
                        Ingredient(name = it.name.trim(), quantity = it.quantity.trim())
                    }
                    onSaveRecipe(recipeName, ingredients)
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
    val quantity: String
)



