package com.udistrital.detectiveapp.repository

import android.content.Context
import com.udistrital.detectiveapp.data.CasePreferences
import com.udistrital.detectiveapp.model.Case

class CaseRepository (context: Context){

    private val casePreferences = CasePreferences(context)

    //see all cases registered
    fun getAllCases(): List<Case>{
        return casePreferences.getCases()
    }

    fun getCaseById(id: Int): Case? {
        val cases= getAllCases()
        return cases.find {case-> case.id == id}
    }

    fun getNextId(): Int {
        val cases = getAllCases()

        if (cases.isEmpty()) {
            return 1
        }

        var highestId = 0

        for (caseItem in cases){
            if (caseItem.id > highestId){
                highestId = caseItem.id
            }
        }
        return highestId + 1
    }

    fun createCase(newCase: Case){
        val cases=getAllCases().toMutableList()
        cases.add(newCase)
        casePreferences.saveCases(cases)
    }

    fun updateCase(updateCase: Case){
        val cases = getAllCases().toMutableList()

        for(i in cases.indices){
            if(cases[i].id == updateCase.id){
                cases[i]=updateCase
                break
            }
        }
        casePreferences.saveCases(cases)
    }

    fun deleteCase(id: Int){
        val cases=getAllCases().toMutableList()
        cases.removeAll { it.id==id }
        casePreferences.saveCases(cases)
    }

    fun searchCases(text: String): List<Case>{
        val cases=getAllCases()

        if(text.isBlank()){
            return cases
        }

        return cases.filter { case -> case.titulo.contains(text, ignoreCase = true) || case.estado.contains(text, ignoreCase = true) }
    }

}