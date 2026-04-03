package com.example.planificador_de_comidas.model

data class ShoppingItem(
    val nombre: String,
    val resumenTotal: String,
    val detalles: List<String>,
    val esComprado: Boolean = false
)
