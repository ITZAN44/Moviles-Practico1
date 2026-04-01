package com.example.planificador_de_comidas

import com.example.planificador_de_comidas.model.Ingredient
import com.example.planificador_de_comidas.viewmodel.RecipeViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class RecipeViewModelTest {

    @Test
    fun addRecipe_agregaRecetaCuandoDatosSonValidos() {
        val viewModel = RecipeViewModel()
        val initialSize = viewModel.recipes.size

        viewModel.addRecipe(
            name = "Tostadas",
            ingredients = listOf(Ingredient("Pan", "2 rebanadas"))
        )

        assertEquals(initialSize + 1, viewModel.recipes.size)
        assertTrue(viewModel.recipes.any { it.name == "Tostadas" })
    }

    @Test
    fun addRecipe_noAgregaSiNombreVacio() {
        val viewModel = RecipeViewModel()
        val initialSize = viewModel.recipes.size

        viewModel.addRecipe(
            name = "   ",
            ingredients = listOf(Ingredient("Pan", "2"))
        )

        assertEquals(initialSize, viewModel.recipes.size)
    }
}

