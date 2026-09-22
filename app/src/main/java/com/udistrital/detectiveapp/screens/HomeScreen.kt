package com.udistrital.detectiveapp.screens

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.udistrital.detectiveapp.repository.CaseRepository
import androidx.compose.material3.TextButton

@Composable
fun HomeScreen(
    onViewCases: () -> Unit,
    onNewCase: () -> Unit,
    onExit: () -> Unit
) {
    val context = LocalContext.current
    val cases = remember { CaseRepository(context).getAllCases() }

    val totalCases = cases.size
    val openCases = cases.count { it.estado.trim().equals("Abierto", ignoreCase = true) }
    val investigatingCases = cases.count { it.estado.trim().equals("En investigación", ignoreCase = true) }
    val closedCases = cases.count { it.estado.trim().equals("Cerrado", ignoreCase = true) }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Detective App",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Gestión de casos e investigaciones",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Resumen de casos",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SummaryCard("Total", totalCases, Modifier.weight(1f))
                SummaryCard("Abiertos", openCases, Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SummaryCard("En investigación", investigatingCases, Modifier.weight(1f))
                SummaryCard("Cerrados", closedCases, Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onViewCases,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ver casos")
            }
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedButton(
                onClick = onNewCase,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Nuevo caso")
            }
            Spacer(modifier = Modifier.height(12.dp))
            TextButton(onClick = onExit) {
                Text("Salir")
            }
        }
    }
}

@Composable
private fun SummaryCard(title: String, value: Int, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value.toString(),
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}
    @Preview(showBackground = true)
    @Composable
    fun HomeScreenPreview() {
        HomeScreen(onViewCases = {}, onNewCase = {}, onExit = {})
    }

