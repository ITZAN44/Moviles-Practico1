package com.example.planificador_de_comidas.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.planificador_de_comidas.model.Ingredient
import com.example.planificador_de_comidas.screens.CreateRecipeScreen
import com.example.planificador_de_comidas.screens.ListaComprasScreen
import com.example.planificador_de_comidas.screens.PlanSemanalScreen
import com.example.planificador_de_comidas.screens.RecipeListScreen
import com.example.planificador_de_comidas.viewmodel.ListaComprasViewModel
import com.example.planificador_de_comidas.viewmodel.PlanSemanalViewModel
import com.example.planificador_de_comidas.viewmodel.RecipeViewModel

@Composable
fun AppNavGraph(
    recipeViewModel: RecipeViewModel,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val planSemanalViewModel: PlanSemanalViewModel = viewModel()
    val listaComprasViewModel: ListaComprasViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = AppRoutes.RecipeList,
        modifier = modifier
    ) {
        composable(AppRoutes.RecipeList) {
            RecipeListScreen(
                viewModel = recipeViewModel,
                onAddRecipeClick = { navController.navigate(AppRoutes.CreateRecipe) },
                onNavigateToPlanSemanal = { navController.navigate(AppRoutes.PlanSemanal) }
            )
        }

        composable(AppRoutes.CreateRecipe) {
            CreateRecipeScreen(
                onBackClick = { navController.popBackStack() },
                onSaveRecipe = { name: String, ingredients: List<Ingredient> ->
                    recipeViewModel.addRecipe(name, ingredients)
                    navController.popBackStack()
                }
            )
        }

        composable(AppRoutes.PlanSemanal) {
            PlanSemanalScreen(
                viewModel = planSemanalViewModel,
                availableRecipes = recipeViewModel.recipes,
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
