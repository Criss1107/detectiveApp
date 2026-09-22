package com.udistrital.detectiveapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.udistrital.detectiveapp.model.Case
import com.udistrital.detectiveapp.model.CasesDemo
import com.udistrital.detectiveapp.model.Evidence

private val DarkBackground = Color(0xFF14161C)
private val Surface = Color(0xFF1E212B)
private val Amber = Color(0xFFE0A458)
private val PrimaryText = Color(0xFFF2F1ED)
private val SecondaryText = Color(0xFFA0A3AD)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaseDetailScreen(
    case: Case,
    onBack: () -> Unit = {}
) {
    Scaffold(
        containerColor = DarkBackground,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Case Detail",
                        color = PrimaryText,
                        fontWeight = FontWeight.Bold
                    )
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
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(4.dp))

            // header: title status and date
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .background(Surface)
                    .padding(18.dp)
            ) {
                StatusBadge(text = case.estado)
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    case.titulo,
                    color = PrimaryText,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text("Date: ${case.fecha}", color = SecondaryText, fontSize = 13.sp)
            }

            Spacer(modifier = Modifier.height(18.dp))
            SectionTitle("Description")
            Spacer(modifier = Modifier.height(8.dp))
            TextCard(case.descripcion)

            Spacer(modifier = Modifier.height(18.dp))
            SectionTitle("Findings")
            Spacer(modifier = Modifier.height(8.dp))
            TextCard(case.hallazgos)

            if (case.evidencias.isNotEmpty()) {
                Spacer(modifier = Modifier.height(18.dp))
                SectionTitle("Evidence")
                Spacer(modifier = Modifier.height(8.dp))
                case.evidencias.forEachIndexed { index, evidence ->
                    if (index > 0) Spacer(modifier = Modifier.height(8.dp))
                    EvidenceCard(evidence)
                }
            }

            Spacer(modifier = Modifier.height(18.dp))
            SectionTitle("Closure Status")
            Spacer(modifier = Modifier.height(8.dp))
            TextCard(case.estadoCierre)

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text.uppercase(),
        color = Amber,
        fontWeight = FontWeight.SemiBold,
        fontSize = 13.sp
    )
}

@Composable
private fun TextCard(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(Surface)
            .padding(14.dp)
    ) {
        Text(text, color = PrimaryText, fontSize = 14.sp, lineHeight = 20.sp)
    }
}

@Composable
private fun EvidenceCard(evidence: Evidence) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(Surface)
            .padding(14.dp)
    ) {
        Text(
            evidence.descripcion,
            color = PrimaryText,
            fontSize = 14.sp,
            lineHeight = 20.sp
        )
        // if it has a photo or image show it, but if its not the case then show just a small general icon
        if (!evidence.fotoUri.isNullOrBlank()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text("Photo attached", color = SecondaryText, fontSize = 12.sp)
        }
    }
}

@Composable
fun StatusBadge(text: String) {
    val color = statusColor(text)
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(color.copy(alpha = 0.16f))
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Text(text, color = color, fontSize = 11.sp, fontWeight = FontWeight.Medium)
    }
}

fun statusColor(status: String): Color = when (status.trim().lowercase()) {
    "open", "abierto" -> Color(0xFF6FA8DC)
    "in progress", "en progreso" -> Amber
    "closed", "cerrado" -> Color(0xFF7CB88F)
    else -> SecondaryText
}

@Preview(showBackground = true)
@Composable
private fun CaseDetailScreenPreview() {
    CaseDetailScreen(case = CasesDemo.list.first())
}