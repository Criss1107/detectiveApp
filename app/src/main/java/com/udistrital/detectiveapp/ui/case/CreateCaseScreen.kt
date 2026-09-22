package com.udistrital.detectiveapp.ui.case

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.udistrital.detectiveapp.model.Case

private val MainBlue = Color(0xFF2563EB)
private val LightBackground = Color(0xFFF7F8FA)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCaseScreen(
    onSaveClick: (Case) -> Unit,
    onBackClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var hallazgos by remember { mutableStateOf("") }

    val estados = listOf("Open", "In progress", "Closed")
    var estadoExpanded by remember { mutableStateOf(false) }
    var estadoSeleccionado by remember { mutableStateOf(estados.first()) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(
            "New Case",
            style = MaterialTheme.typography.headlineSmall
        )

        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Description") },
            minLines = 3,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = fecha,
            onValueChange = { fecha = it },
            label = { Text("Date") },
            placeholder = { Text("Sep 21, 2026") },
            modifier = Modifier.fillMaxWidth()
        )

        ExposedDropdownMenuBox(
            expanded = estadoExpanded,
            onExpandedChange = { estadoExpanded = it }
        ) {
            OutlinedTextField(
                value = estadoSeleccionado,
                onValueChange = {},
                readOnly = true,
                label = { Text("Status") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = estadoExpanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable)
            )
            ExposedDropdownMenu(
                expanded = estadoExpanded,
                onDismissRequest = { estadoExpanded = false }
            ) {
                estados.forEach { estado ->
                    DropdownMenuItem(
                        text = { Text(estado) },
                        onClick = {
                            estadoSeleccionado = estado
                            estadoExpanded = false
                        }
                    )
                }
            }
        }

        OutlinedTextField(
            value = hallazgos,
            onValueChange = { hallazgos = it },
            label = { Text("Findings (optional)") },
            minLines = 2,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val newCase = Case(
                    id = 0,
                    titulo = titulo,
                    descripcion = descripcion,
                    fecha = fecha,
                    estado = estadoSeleccionado,
                    hallazgos = hallazgos,
                    evidencias = emptyList(),
                    estadoCierre = "Pending"
                )
                onSaveClick(newCase)
            },
            enabled = titulo.isNotBlank() && descripcion.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Case")
        }

        Button(
            onClick = onBackClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancel")
        }
    }
}