package com.example.planificador_de_comidas.model

data class Receta(
    val id: Int,
    val nombre: String,
    val ingredientes: List<Ingrediente>
)



