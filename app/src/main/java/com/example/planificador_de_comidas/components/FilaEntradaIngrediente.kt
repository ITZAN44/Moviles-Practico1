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
fun FilaEntradaIngrediente(
    nombre: String,
    cantidad: String,
    unidad: String,
    onNombreChange: (String) -> Unit,
    onCantidadChange: (String) -> Unit,
    onUnidadChange: (String) -> Unit,
    onEliminarClick: () -> Unit
) {
    val unidades = listOf("unidad", "unidades", "kg", "g", "l", "ml", "cucharada", "pizca")
    var expandido by remember { mutableStateOf(false) }

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
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = onNombreChange,
                    label = { Text("Nombre del ingrediente") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )

                IconButton(
                    onClick = onEliminarClick,
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar ingrediente")
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = cantidad,
                    onValueChange = onCantidadChange,
                    label = { Text("Cantidad") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true
                )

                Box(modifier = Modifier.weight(1f)) {
                    OutlinedTextField(
                        value = unidad,
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
                            .clickable { expandido = true }
                    )

                    if (expandido) {
                        Dialog(onDismissRequest = { expandido = false }) {
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
                                        items(unidades.size) { index ->
                                            val opcionSeleccionada = unidades[index]
                                            ListItem(
                                                headlineContent = { Text(opcionSeleccionada) },
                                                modifier = Modifier.clickable {
                                                    onUnidadChange(opcionSeleccionada)
                                                    expandido = false
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

