package com.udistrital.detectiveapp



object CaseStatus {
    const val OPEN = "Abierto"
    const val INVESTIGATING = "En investigación"
    const val CLOSED = "Cerrado"

    val ALL = listOf(OPEN, INVESTIGATING, CLOSED)

    fun normalize(raw: String): String = when (raw.trim().lowercase()) {
        "open", "abierto" -> OPEN
        "in progress", "en progreso", "en proceso", "en investigación", "en investigacion" -> INVESTIGATING
        "closed", "cerrado" -> CLOSED
        else -> raw.trim()
    }
}

object NavArgs {
    const val CASE_ID = "id"
}