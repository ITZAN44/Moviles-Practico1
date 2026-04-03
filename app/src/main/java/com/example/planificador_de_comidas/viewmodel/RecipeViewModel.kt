package com.example.planificador_de_comidas.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.planificador_de_comidas.model.Ingredient
import com.example.planificador_de_comidas.model.Recipe

class RecipeViewModel : ViewModel() {

    var recipes by mutableStateOf(
        listOf(
            Recipe(
                id = 1,
                name = "Ensalada simple",
                ingredients = listOf(
                    Ingredient("Lechuga", 1.0, "unidad"),
                    Ingredient("Tomate", 2.0, "unidades")
                )
            )
        )
    )
        private set

    var searchQuery by mutableStateOf("")
        private set

    var ingredientFilter by mutableStateOf("")
        private set

    val filteredRecipes: List<Recipe>
        get() = recipes.filter { recipe ->
            val matchesName = recipe.name.contains(searchQuery, ignoreCase = true)

            val matchesIngredient = if (ingredientFilter.isBlank()) {
                true
            } else {
                recipe.ingredients.any { ingredient ->
                    ingredient.name.contains(ingredientFilter, ignoreCase = true)
                }
            }

            matchesName && matchesIngredient
        }

    fun updateSearchQuery(query: String) {
        searchQuery = query
    }

    fun updateIngredientFilter(filter: String) {
        ingredientFilter = filter
    }

    fun addRecipe(name: String, ingredients: List<Ingredient>) {
        val cleanedName = name.trim()
        val cleanedIngredients = ingredients.filter {
            it.name.isNotBlank() && it.quantity > 0
        }

        if (cleanedName.isBlank() || cleanedIngredients.isEmpty()) return

        val nextId = (recipes.maxOfOrNull { it.id } ?: 0) + 1
        val newRecipe = Recipe(
            id = nextId,
            name = cleanedName,
            ingredients = cleanedIngredients
        )

        recipes = recipes + newRecipe
    }
}
