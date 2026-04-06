package com.example.planificador_de_comidas.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.planificador_de_comidas.model.Receta
import com.example.planificador_de_comidas.viewmodel.ListaComprasViewModel

@Composable
fun ListaComprasScreen(
    viewModel: ListaComprasViewModel,
    plannedRecipes: List<Receta>,
    onBackClick: () -> Unit
) {
    val shoppingList = viewModel.getShoppingList(plannedRecipes)

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(text = "Lista de Compras", style = MaterialTheme.typography.headlineSmall)

            Button(
                onClick = onBackClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver")
            }

            if (shoppingList.isEmpty()) {
                Box(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No hay ingredientes necesarios. Agrega recetas al plan semanal.")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(shoppingList) { item ->
                        ShoppingItemRow(
                            nombre = item.nombre,
                            resumenTotal = item.resumenTotal,
                            detalles = item.detalles,
                            isBought = item.esComprado,
                            onCheckedChange = { viewModel.toggleBoughtStatus(item.nombre) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ShoppingItemRow(
    nombre: String,
    resumenTotal: String,
    detalles: List<String>,
    isBought: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isBought) 
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f) 
                else MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isBought,
                    onCheckedChange = onCheckedChange
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = nombre,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        textDecoration = if (isBought) TextDecoration.LineThrough else null
                    )
                    Text(
                        text = "Total: $resumenTotal",
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (isBought) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.primary,
                        textDecoration = if (isBought) TextDecoration.LineThrough else null
                    )
                }

                Surface(
                    shape = MaterialTheme.shapes.small,
                    color = if (isBought) 
                        MaterialTheme.colorScheme.primaryContainer 
                        else MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.1f),
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text(
                        text = if (isBought) "Comprado" else "Pendiente",
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        color = if (isBought) 
                            MaterialTheme.colorScheme.onPrimaryContainer 
                            else MaterialTheme.colorScheme.error
                    )
                }
            }
            
            if (detalles.isNotEmpty()) {
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                Text(
                    text = "Desglose por receta:",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
                detalles.forEach { detalle ->
                    Text(
                        text = "• $detalle",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}
