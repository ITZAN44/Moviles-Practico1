package com.example.planificador_de_comidas.viewmodel

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import com.example.planificador_de_comidas.model.Recipe
import com.example.planificador_de_comidas.model.ShoppingItem

class ListaComprasViewModel : ViewModel() {

    private val _boughtItems = mutableStateMapOf<String, Boolean>()

    fun getShoppingList(plannedRecipes: List<Recipe>): List<ShoppingItem> {
        // Nombre -> (Unidad -> SumaCantidad)
        val totalsMap = mutableMapOf<String, MutableMap<String, Double>>()
        // Nombre -> Lista de detalles ("Receta: X unidad")
        val detailsMap = mutableMapOf<String, MutableList<String>>()

        plannedRecipes.forEach { recipe ->
            recipe.ingredients.forEach { ingredient ->
                val unitMap = totalsMap.getOrPut(ingredient.name) { mutableMapOf() }
                val currentTotal = unitMap.getOrDefault(ingredient.unit, 0.0)
                unitMap[ingredient.unit] = currentTotal + ingredient.quantity

                val details = detailsMap.getOrPut(ingredient.name) { mutableListOf() }
                details.add("${recipe.name}: ${ingredient.quantity} ${ingredient.unit}")
            }
        }

        return totalsMap.map { (name, unitMap) ->
            val resumen = unitMap.entries.joinToString(", ") { "${it.value} ${it.key}" }
            
            ShoppingItem(
                nombre = name,
                resumenTotal = resumen,
                detalles = detailsMap[name] ?: emptyList(),
                esComprado = _boughtItems[name] ?: false
            )
        }.sortedBy { it.nombre }
    }

    fun toggleBoughtStatus(itemName: String) {
        val current = _boughtItems[itemName] ?: false
        _boughtItems[itemName] = !current
    }
}
