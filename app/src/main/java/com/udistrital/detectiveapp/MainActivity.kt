package com.udistrital.detectiveapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.udistrital.detectiveapp.navigation.AppNavigation
import com.udistrital.detectiveapp.ui.theme.DetectiveAppTheme
import com.udistrital.detectiveapp.model.Case
import com.udistrital.detectiveapp.repository.CaseRepository

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            DetectiveAppTheme {
                AppNavigation()
            }
        }
    }
}