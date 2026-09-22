package com.udistrital.detectiveapp.model
object CasesDemo {
    val list = listOf(
        Case(
            id = 1,
            titulo = "The missing necklace",
            descripcion = "An emerald necklace disappeared during a private party. " +
                    "There are no signs of forced entry on the safe.",
            fecha = "Sep 03, 2026",
            estado = "In progress",
            hallazgos = "The safe has only 3 known combinations. The butler left 15 min " +
                    "before the loss was noticed.",
            evidencias = listOf(
                Evidence(id = 1, descripcion = "Access record from the smart lock", fotoUri = null),
                Evidence(id = 2, descripcion = "Housekeeper's witness statement", fotoUri = null)
            ),
            estadoCierre = "Pending"
        ),
        Case(
            id = 2,
            titulo = "Suspicious warehouse fire",
            descripcion = "A textile warehouse caught fire at dawn. Insurance fraud is suspected.",
            fecha = "Aug 28, 2026",
            estado = "Open",
            hallazgos = "Security cameras were off that night.",
            evidencias = listOf(
                Evidence(id = 3, descripcion = "Official fire department report", fotoUri = null)
            ),
            estadoCierre = "Not closed"
        ),
        Case(
            id = 3,
            titulo = "Blackmail of the councilman",
            descripcion = "Blackmail letters sent to a local councilman.",
            fecha = "Jul 10, 2026",
            estado = "Closed",
            hallazgos = "Confession obtained on Jul 15, 2026.",
            evidencias = listOf(
                Evidence(id = 4, descripcion = "Three blackmail letters recovered", fotoUri = null)
            ),
            estadoCierre = "Solved"
        )
    )
}
