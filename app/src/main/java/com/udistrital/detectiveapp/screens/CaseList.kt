package com.udistrital.detectiveapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.udistrital.detectiveapp.model.Case
import com.udistrital.detectiveapp.repository.CaseRepository

@Composable
fun CaseListScreen(
    onBack: () -> Unit,
    onViewCase: (Int) -> Unit,
    onEditCase: (Int) -> Unit,
    onDeleteCase: (Int) -> Unit
) {
    val context = LocalContext.current
    val repository = remember { CaseRepository(context) }

    var query by rememberSaveable { mutableStateOf("") }
    val cases = remember(query) {
        repository.searchCases(query).sortedByDescending { it.id }
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            TextButton(onClick = onBack) {
                Text("← Volver")
            }

            Text("Casos", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                label = { Text("Buscar por título o estado") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${cases.size} caso(s)",
                style = MaterialTheme.typography.labelLarge
            )
            Spacer(modifier = Modifier.height(8.dp))

            if (cases.isEmpty()) {
                Text(
                    text = if (query.isBlank()) {
                        "Aún no hay casos registrados."
                    } else {
                        "No se encontraron casos para \"$query\"."
                    },
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(cases, key = { it.id }) { case ->
                        CaseCard(
                            case = case,
                            onView = { onViewCase(case.id) },
                            onEdit = { onEditCase(case.id) },
                            onDelete = { onDeleteCase(case.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CaseCard(
    case: Case,
    onView: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = case.titulo, style = MaterialTheme.typography.titleMedium)
            Text(
                text = "Estado: ${case.estado}  ·  ${case.fecha}",
                style = MaterialTheme.typography.bodyMedium
            )

            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                TextButton(onClick = onView) { Text("Consultar") }
                TextButton(onClick = onEdit) { Text("Editar") }
                TextButton(
                    onClick = onDelete,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) { Text("Eliminar") }
            }
        }
    }
}