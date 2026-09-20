package com.udistrital.detectiveapp.model

data class Case (
    val id: Int,
    val titulo: String,
    val descripcion: String,
    val fecha: String,
    val estado: String,
    val hallazgos: String,
    val evidencias:List<Evidence>,
    val estadoCierre: String
)