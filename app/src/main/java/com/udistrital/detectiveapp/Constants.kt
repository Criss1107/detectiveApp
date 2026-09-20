package com.udistrital.detectiveapp

// Valores globales que se usan en más de un archivo.
// Si un valor se repite en varios lugares, va aquí.

object CaseStatus {
    const val OPEN = "Abierto"
    const val INVESTIGATING = "En investigación"
    const val CLOSED = "Cerrado"

    val ALL = listOf(OPEN, INVESTIGATING, CLOSED)
}

object NavArgs {
    const val CASE_ID = "id"
}