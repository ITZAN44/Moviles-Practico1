package com.example.planificador_de_comidas.viewmodel

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import com.example.planificador_de_comidas.model.Receta

class PlanSemanalViewModel : ViewModel() {
    val diasDeLaSemana = listOf(
        "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"
    )
    // Estado del plan: Mapa de Día -> Receta (null si no hay asignada)
    private val _planSemanal = mutableStateMapOf<String, Receta?>().apply {
        diasDeLaSemana.forEach { put(it, null) }
    }
    val planSemanal: Map<String, Receta?> = _planSemanal

    fun asignarReceta(dia: String, receta: Receta?) {
        if (dia in diasDeLaSemana) {
            _planSemanal[dia] = receta
        }
    }

    fun obtenerRecetasPlanificadas(): List<Receta> {
        return _planSemanal.values.filterNotNull()
    }
}
