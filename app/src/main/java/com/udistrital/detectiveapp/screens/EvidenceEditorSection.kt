package com.udistrital.detectiveapp.screens

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.udistrital.detectiveapp.model.Evidence
import java.io.File

fun copyImageToInternalStorage(context: Context, uri: Uri): String? {
    return try {
        val dir = File(context.filesDir, "evidencias").apply { mkdirs() }
        val file = File(dir, "ev_${System.currentTimeMillis()}.jpg")
        context.contentResolver.openInputStream(uri)?.use { input ->
            file.outputStream().use { output -> input.copyTo(output) }
        } ?: return null
        Uri.fromFile(file).toString()   // file:///data/.../ev_123.jpg
    } catch (e: Exception) {
        null
    }
}

@Composable
fun EvidenceEditorSection(
    evidences: List<Evidence>,
    nextEvidenceId: () -> Int,
    onEvidenceAdded: (Evidence) -> Unit,
    onEvidenceRemoved: (Evidence) -> Unit
) {
    val context = LocalContext.current
    var descripcion by remember { mutableStateOf("") }
    var fotoUri by remember { mutableStateOf<String?>(null) }

    val picker = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) fotoUri = copyImageToInternalStorage(context, uri)
    }

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text("Evidence", style = MaterialTheme.typography.titleMedium)

        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Evidence description") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedButton(
            onClick = {
                picker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text(if (fotoUri == null) "Add photo" else "Change photo") }

        fotoUri?.let {
            AsyncImage(
                model = it,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
        }

        Button(
            onClick = {
                onEvidenceAdded(Evidence(nextEvidenceId(), descripcion, fotoUri))
                descripcion = ""
                fotoUri = null
            },
            enabled = descripcion.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) { Text("Add evidence") }

        evidences.forEach { ev ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    ev.descripcion + if (ev.fotoUri != null) " 📷" else "",
                    modifier = Modifier.weight(1f)
                )
                TextButton(onClick = { onEvidenceRemoved(ev) }) { Text("Remove") }
            }
        }
    }
}