package com.example.planificador_de_comidas.viewmodel

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import com.example.planificador_de_comidas.model.Receta
import com.example.planificador_de_comidas.model.ShoppingItem

class ListaComprasViewModel : ViewModel() {

    private val _boughtItems = mutableStateMapOf<String, Boolean>()

    fun getShoppingList(plannedRecipes: List<Receta>): List<ShoppingItem> {
        // Nombre -> (Unidad -> SumaCantidad)
        val totalsMap = mutableMapOf<String, MutableMap<String, Double>>()
        // Nombre -> Lista de detalles ("Receta: X unidad")
        val detailsMap = mutableMapOf<String, MutableList<String>>()

        plannedRecipes.forEach { recipe ->
            recipe.ingredientes.forEach { ingrediente ->
                val unitMap = totalsMap.getOrPut(ingrediente.nombre) { mutableMapOf() }
                val currentTotal = unitMap.getOrDefault(ingrediente.unidad, 0.0)
                unitMap[ingrediente.unidad] = currentTotal + ingrediente.cantidad

                val details = detailsMap.getOrPut(ingrediente.nombre) { mutableListOf() }
                details.add("${recipe.nombre}: ${ingrediente.cantidad} ${ingrediente.unidad}")
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
