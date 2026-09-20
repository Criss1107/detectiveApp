package com.udistrital.detectiveapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.tooling.preview.Preview
import com.udistrital.detectiveapp.ui.case.DetalleCaso
import com.udistrital.detectiveapp.ui.theme.DetectiveAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DetectiveAppTheme {
//trasladarlo a la pagina de inicio cuando se cree
                var pantallaActual by remember { mutableStateOf("inicio") }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    when (pantallaActual) {
                        "inicio" ->
                            Greeting(
                                name = "Android",
                                modifier = Modifier.padding(innerPadding),
                                onBotonClick = { pantallaActual = "detalle" } // <- la acción
                            )

                        "detalle" ->
                            DetalleCaso(
                                onBotonClick = { pantallaActual = "inicio" }, // regresar, opcional
                                modifier = Modifier.padding(innerPadding)
                            )
                    }
                }
            }
        }
    }

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier, onBotonClick: () -> Unit) {
                Column (modifier = modifier) {
                Text(text = "Hello $name!")
                Button(onClick = onBotonClick) {
                    Text("Ver detalle")
                }
            }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        DetectiveAppTheme {
            Greeting(
                "inicio",
                onBotonClick = {})
        }
    }
}