package com.udistrital.detectiveapp

import androidx.test.core.app.ApplicationProvider
import com.udistrital.detectiveapp.data.CasePreferences
import com.udistrital.detectiveapp.repository.CaseRepository
import org.junit.Test
import android.content.Context
import com.udistrital.detectiveapp.model.Case
import com.udistrital.detectiveapp.model.Evidence
import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class CaseRepositoryTest {
    private lateinit var casePreferences: CasePreferences
    private lateinit var repository: CaseRepository

    @Before
    fun setUp() {
        //we get a fake context with roboelectric
        val context = ApplicationProvider.getApplicationContext<Context>()

        // We clean sharedPreferences for every test starts clean
        context.getSharedPreferences("casos", Context.MODE_PRIVATE).edit().clear().apply()

        // initialize classes
        repository = CaseRepository(context)
    }

    @Test
    fun generateNewID() {
        assertEquals(1, repository.getNextId())

        val caso1 = Case(1, "Caso 1", "Secuestraron a joven", "10/09/2026", "Abierto", "Sus pertenencias", emptyList(), "Pendiente")
        repository.createCase(caso1)
        println("ID generado después de crear caso: ${repository.getNextId()}")
        assertEquals(2, repository.getNextId())
    }

    @Test
    fun caseTest(){
        val newEvidence = Evidence(id = 1, descripcion = "Arma encontrada", fotoUri = "content://media/foto1")
        val newCase = Case(
            id = repository.getNextId(),
            titulo = "Robo en el museo",
            descripcion = "Se robaron una pintura",
            fecha = "2026-09-19",
            estado = "Abierto",
            hallazgos = "Ninguno",
            evidencias = listOf(newEvidence),
            estadoCierre = "Pendiente"
        )

        repository.createCase(newCase)
        val savedCase = repository.getCaseById(1)

        println("Caso recuperado de la base de datos: ${savedCase?.titulo}")
        println("Uri de evidencia guardada: ${savedCase?.evidencias?.get(0)?.fotoUri}")
        assertNotNull(savedCase)
        assertEquals("Robo en el museo", savedCase?.titulo)
        assertEquals("content://media/foto1", savedCase?.evidencias?.get(0)?.fotoUri)
    }
}