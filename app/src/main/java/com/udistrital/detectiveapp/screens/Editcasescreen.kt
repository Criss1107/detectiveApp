package com.udistrital.detectiveapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.udistrital.detectiveapp.CaseStatus
import com.udistrital.detectiveapp.model.Case
import com.udistrital.detectiveapp.repository.CaseRepository


private val DarkBackground = Color(0xFF14161C)
private val Surface = Color(0xFF1E212B)
private val Amber = Color(0xFFE0A458)
private val PrimaryText = Color(0xFFF2F1ED)
private val SecondaryText = Color(0xFFA0A3AD)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditCaseScreen(
    caseId: Int,
    onBack: () -> Unit,
    onSaved: (Case) -> Unit = {}
) {
    val context = LocalContext.current
    val caseRepository = remember { CaseRepository(context) }

    val originalCase = remember(caseId) { caseRepository.getCaseById(caseId) }

    if (originalCase == null) {
        Scaffold(containerColor = DarkBackground) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Case not found", color = SecondaryText)
            }
        }
        return
    }

    var titulo by remember { mutableStateOf(originalCase.titulo) }
    var descripcion by remember { mutableStateOf(originalCase.descripcion) }
    var fecha by remember { mutableStateOf(originalCase.fecha) }
    var hallazgos by remember { mutableStateOf(originalCase.hallazgos) }
    var estadoCierre by remember { mutableStateOf(originalCase.estadoCierre) }

    val estados = CaseStatus.ALL
    var estadoExpanded by remember { mutableStateOf(false) }
    var estadoSeleccionado by remember { mutableStateOf(CaseStatus.normalize(originalCase.estado)) }

    Scaffold(
        containerColor = DarkBackground,
        topBar = {
            TopAppBar(
                title = {
                    Text("Edit Case", color = PrimaryText, fontWeight = FontWeight.Bold)
                },
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text("< Back", color = PrimaryText)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBackground)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                value = titulo,
                onValueChange = { titulo = it },
                label = { Text("Title") },
                colors = fieldColors(),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Description") },
                minLines = 3,
                colors = fieldColors(),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = fecha,
                onValueChange = { fecha = it },
                label = { Text("Date") },
                colors = fieldColors(),
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
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = estadoExpanded)
                    },
                    colors = fieldColors(),
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
                label = { Text("Findings") },
                minLines = 2,
                colors = fieldColors(),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = estadoCierre,
                onValueChange = { estadoCierre = it },
                label = { Text("Closure Status") },
                colors = fieldColors(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    val updatedCase = originalCase.copy(
                        titulo = titulo,
                        descripcion = descripcion,
                        fecha = fecha,
                        estado = estadoSeleccionado,
                        hallazgos = hallazgos,
                        estadoCierre = estadoCierre
                    )
                    caseRepository.updateCase(updatedCase)
                    onSaved(updatedCase)
                    onBack()
                },
                enabled = titulo.isNotBlank() && descripcion.isNotBlank(),
                colors = ButtonDefaults.buttonColors(containerColor = Amber),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Changes")
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun fieldColors() = OutlinedTextFieldDefaults.colors(
    focusedTextColor = PrimaryText,
    unfocusedTextColor = PrimaryText,
    focusedContainerColor = Surface,
    unfocusedContainerColor = Surface,
    focusedLabelColor = Amber,
    unfocusedLabelColor = SecondaryText,
    focusedBorderColor = Amber,
    unfocusedBorderColor = SecondaryText
)