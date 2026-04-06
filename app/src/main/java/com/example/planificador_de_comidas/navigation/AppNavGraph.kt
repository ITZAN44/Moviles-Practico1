package com.example.planificador_de_comidas.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.planificador_de_comidas.model.Ingrediente
import com.example.planificador_de_comidas.screens.CrearRecetaScreen
import com.example.planificador_de_comidas.screens.ListaComprasScreen
import com.example.planificador_de_comidas.screens.ListaRecetasScreen
import com.example.planificador_de_comidas.screens.PlanSemanalScreen
import com.example.planificador_de_comidas.viewmodel.ListaComprasViewModel
import com.example.planificador_de_comidas.viewmodel.PlanSemanalViewModel
import com.example.planificador_de_comidas.viewmodel.RecetaViewModel

@Composable
fun AppNavGraph(
    recetaViewModel: RecetaViewModel,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val planSemanalViewModel: PlanSemanalViewModel = viewModel()
    val listaComprasViewModel: ListaComprasViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = AppRoutes.ListaRecetas,
        modifier = modifier
    ) {
        composable(AppRoutes.ListaRecetas) {
            ListaRecetasScreen(
                viewModel = recetaViewModel,
                onAgregarRecetaClick = { navController.navigate(AppRoutes.CrearReceta) },
                onNavegarAPlanSemanal = { navController.navigate(AppRoutes.PlanSemanal) }
            )
        }

        composable(AppRoutes.CrearReceta) {
            CrearRecetaScreen(
                onBackClick = { navController.popBackStack() },
                onSaveRecipe = { nombre: String, ingredientes: List<Ingrediente> ->
                    recetaViewModel.agregarReceta(nombre, ingredientes)
                    navController.popBackStack()
                }
            )
        }

        composable(AppRoutes.PlanSemanal) {
            PlanSemanalScreen(
                viewModel = planSemanalViewModel,
                availableRecipes = recetaViewModel.recetas,
                onBackClick = { navController.popBackStack() },
                onNavigateToListaCompras = { navController.navigate(AppRoutes.ListaCompras) }
            )
        }

        composable(AppRoutes.ListaCompras) {
            ListaComprasScreen(
                viewModel = listaComprasViewModel,
                plannedRecipes = planSemanalViewModel.obtenerRecetasPlanificadas(),
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
