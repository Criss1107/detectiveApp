package com.udistrital.detectiveapp.data

import android.content.Context
import android.content.SharedPreferences
import com.udistrital.detectiveapp.model.Case
import com.udistrital.detectiveapp.model.Evidence
import org.json.JSONArray
import org.json.JSONObject

class CasePreferences (context: Context){

    private val preferences: SharedPreferences = context.getSharedPreferences("casos", Context.MODE_PRIVATE)

    fun saveCases(cases: List<Case>){
        val jsonArray = JSONArray()

        for (case in cases){
            val jsonObject = JSONObject()

            jsonObject.put("id",case.id)
            jsonObject.put("titulo",case.titulo)
            jsonObject.put("descripcion",case.descripcion)
            jsonObject.put("fecha",case.fecha)
            jsonObject.put("estado",case.estado)
            jsonObject.put("hallazgos",case.hallazgos)
            jsonObject.put("estado de cierre",case.estadoCierre)

            val evidencesArray=JSONArray()
            for (evidence in case.evidencias){
                val evObjt=JSONObject()
                evObjt.put("id",evidence.id)
                evObjt.put("descripcion",evidence.descripcion)
                evObjt.put("foto",evidence.fotoUri ?:"")

                evidencesArray.put(evObjt)
            }
            jsonObject.put("evidencias",evidencesArray)

            jsonArray.put(jsonObject)
        }
        preferences.edit().putString("lista_casos", jsonArray.toString()).apply()
    }

    fun getCases(): List<Case>{

        val cases = mutableListOf<Case>()
        val jsonString = preferences.getString("lista_casos", null)

        if(jsonString !=null){
            val jsonArray= JSONArray(jsonString)

            for(i in 0 until jsonArray.length()){
                val jsonObject = jsonArray.getJSONObject(i)

                val evidences = mutableListOf<Evidence>()
                if (jsonObject.has("evidencias")) {
                    val evidencesArray = jsonObject.getJSONArray("evidencias")
                    for (j in 0 until evidencesArray.length()) {
                        val evObj = evidencesArray.getJSONObject(j)

                        val uriString = evObj.optString("foto", "")

                        evidences.add(
                            Evidence(
                                id = evObj.getInt("id"),
                                descripcion = evObj.getString("descripcion"),
                                fotoUri = if (uriString.isNotEmpty()) uriString else null
                            )
                        )
                    }
                }

                val case = Case(
                    id = jsonObject.getInt("id"),
                    titulo = jsonObject.getString("titulo"),
                    descripcion = jsonObject.getString("descripcion"),
                    fecha = jsonObject.getString("fecha"),
                    estado = jsonObject.getString("estado"),
                    hallazgos = jsonObject.getString("hallazgos"),
                    evidencias = evidences,
                    estadoCierre = jsonObject.getString("estado de cierre")
                    )
                cases.add(case)
            }
        }
        return cases
    }
}