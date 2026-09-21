package com.udistrital.detectiveapp.ui.case

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.Unit as Unit1

// Colores reutilizados del listado de casos
private val AzulPrincipal = Color(0xFF2563EB)
private val FondoClaro = Color(0xFFF7F8FA)

@Composable
fun CrearCaso(
    onGuardarClick: (CasoNuevo) -> Unit1,
    onVolverClick: () -> Unit1 = {},
    modifier: Modifier = Modifier
) {
    var titulo by remember { mutableStateOf("") }
    var cliente by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }

    val categorias = listOf("Robo", "Fraude corporativo", "Desaparición de persona", "Vandalismo", "Homicidio")
    var categoriaExpandida by remember { mutableStateOf(false) }
    var categoriaSeleccionada by remember { mutableStateOf(categorias.first()) }
}
