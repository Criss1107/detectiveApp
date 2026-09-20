package com.udistrital.detectiveapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CreateCaseScreen(onBack: () -> Unit) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp)
        ) {
            Text("Nuevo caso", style = MaterialTheme.typography.headlineSmall)

            // TODO Persona 2: formulario de creación (título, descripción, fecha,
            // estado, hallazgos, evidencias). Al guardar, llamar a onBack().

            Spacer(modifier = Modifier.height(24.dp))
            OutlinedButton(onClick = onBack) {
                Text("Volver")
            }
        }
    }
}