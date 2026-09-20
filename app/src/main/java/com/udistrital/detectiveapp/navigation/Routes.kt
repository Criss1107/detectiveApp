package com.udistrital.detectiveapp.navigation

import com.udistrital.detectiveapp.NavArgs

object Routes {
    const val HOME = "home"
    const val CASE_LIST = "case_list"
    const val DELETE_CASE = "delete_case/{${NavArgs.CASE_ID}}"
    const val CREATE_CASE = "create_case"
    const val CASE_DETAIL = "case_detail/{${NavArgs.CASE_ID}}"
    const val EDIT_CASE = "edit_case/{${NavArgs.CASE_ID}}"

    fun caseDetail(id: Int) = "case_detail/$id"
    fun editCase(id: Int) = "edit_case/$id"
    fun deleteCase(id: Int) = "delete_case/$id"
}