package com.udistrital.detectiveapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CaseDetailScreen(caseId: Int, onBack: () -> Unit, onEdit: () -> Unit) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp)
        ) {
            Text("Detalle del caso #$caseId", style = MaterialTheme.typography.headlineSmall)

            // TODO Persona 2: cargar el caso con CaseRepository.getCaseById(caseId)
            // y mostrar todos sus campos y evidencias.

            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = onEdit) {
                Text("Editar")
            }
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedButton(onClick = onBack) {
                Text("Volver")
            }
        }
    }
}