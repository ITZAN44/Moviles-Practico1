package com.example.planificador_de_comidas.model

data class Recipe(
    val id: Int,
    val name: String,
    val ingredients: List<Ingredient>
)


