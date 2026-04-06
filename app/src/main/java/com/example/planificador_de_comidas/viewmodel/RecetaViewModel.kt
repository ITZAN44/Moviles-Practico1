package com.example.planificador_de_comidas.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.planificador_de_comidas.model.Ingrediente
import com.example.planificador_de_comidas.model.Receta

class RecetaViewModel : ViewModel() {

    var recetas by mutableStateOf(
        listOf(
            Receta(
                id = 1,
                nombre = "Ensalada simple",
                ingredientes = listOf(
                    Ingrediente("Lechuga", 1.0, "unidad"),
                    Ingrediente("Tomate", 2.0, "unidades")
                )
            )
        )
    )
        private set

    var busquedaNombre by mutableStateOf("")
        private set

    var filtroIngrediente by mutableStateOf("")
        private set

    val recetasFiltradas: List<Receta>
        get() = recetas.filter { receta ->
            val coincideNombre = receta.nombre.contains(busquedaNombre, ignoreCase = true)

            val coincideIngrediente = if (filtroIngrediente.isBlank()) {
                true
            } else {
                receta.ingredientes.any { ingrediente ->
                    ingrediente.nombre.contains(filtroIngrediente, ignoreCase = true)
                }
            }

            coincideNombre && coincideIngrediente
        }

    fun actualizarBusquedaNombre(texto: String) {
        busquedaNombre = texto
    }

    fun actualizarFiltroIngrediente(texto: String) {
        filtroIngrediente = texto
    }

    fun agregarReceta(nombre: String, ingredientes: List<Ingrediente>) {
        val nombreLimpio = nombre.trim()
        val ingredientesLimpios = ingredientes.filter {
            it.nombre.isNotBlank() && it.cantidad > 0
        }

        if (nombreLimpio.isBlank() || ingredientesLimpios.isEmpty()) return

        val siguienteId = (recetas.maxOfOrNull { it.id } ?: 0) + 1
        val nuevaReceta = Receta(
            id = siguienteId,
            nombre = nombreLimpio,
            ingredientes = ingredientesLimpios
        )

        recetas = recetas + nuevaReceta
    }
}

