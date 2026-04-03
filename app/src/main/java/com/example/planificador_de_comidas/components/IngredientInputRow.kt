package com.example.planificador_de_comidas.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun IngredientInputRow(
    name: String,
    quantity: String,
    unit: String,
    onNameChange: (String) -> Unit,
    onQuantityChange: (String) -> Unit,
    onUnitChange: (String) -> Unit,
    onDeleteClick: () -> Unit
) {
    val units = listOf("unidad", "unidades", "kg", "g", "l", "ml", "cucharada", "pizca")
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        )
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Primera Fila: Nombre del ingrediente y botón de eliminar
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = onNameChange,
                    label = { Text("Nombre del ingrediente") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                
                IconButton(
                    onClick = onDeleteClick,
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar ingrediente")
                }
            }

            // Segunda Fila: Cantidad y Selector de Unidad
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = quantity,
                    onValueChange = onQuantityChange,
                    label = { Text("Cantidad") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true
                )

                // Selector de Unidad personalizado (No experimental)
                Box(modifier = Modifier.weight(1f)) {
                    OutlinedTextField(
                        value = unit,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Unidad") },
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = {
                            Text("▼", modifier = Modifier.padding(end = 8.dp))
                        }
                    )

                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .clickable { expanded = true }
                    )

                    if (expanded) {
                        Dialog(onDismissRequest = { expanded = false }) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                                    .padding(16.dp),
                                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .heightIn(max = 300.dp)
                                ) {
                                    Text(
                                        "Seleccionar Unidad",
                                        style = MaterialTheme.typography.titleMedium,
                                        modifier = Modifier.padding(8.dp)
                                    )
                                    HorizontalDivider()
                                    LazyColumn {
                                        items(units.size) { index ->
                                            val selectionOption = units[index]
                                            ListItem(
                                                headlineContent = { Text(selectionOption) },
                                                modifier = Modifier.clickable {
                                                    onUnitChange(selectionOption)
                                                    expanded = false
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
