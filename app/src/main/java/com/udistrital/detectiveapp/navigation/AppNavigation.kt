package com.udistrital.detectiveapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.udistrital.detectiveapp.NavArgs
import com.udistrital.detectiveapp.screens.CaseDetailScreen
import com.udistrital.detectiveapp.screens.CaseListScreen
import com.udistrital.detectiveapp.screens.CreateCaseScreen
import com.udistrital.detectiveapp.screens.DeleteCaseScreen
import com.udistrital.detectiveapp.screens.EditCaseScreen
import com.udistrital.detectiveapp.screens.HomeScreen
import android.app.Activity
import androidx.compose.ui.platform.LocalContext


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {
        composable(Routes.HOME) {
            HomeScreen(
                onViewCases = { navController.navigate(Routes.CASE_LIST) },
                onNewCase = { navController.navigate(Routes.CREATE_CASE) },
                onExit = { (context as? Activity)?.finish() }
            )
        }

        composable(Routes.CASE_LIST) {
            CaseListScreen(
                onBack = { navController.popBackStack() },
                onViewCase = { id -> navController.navigate(Routes.caseDetail(id)) },
                onEditCase = { id -> navController.navigate(Routes.editCase(id)) },
                onDeleteCase = { id -> navController.navigate(Routes.deleteCase(id)) }
            )
        }

        composable(
            route = Routes.DELETE_CASE,
            arguments = listOf(navArgument(NavArgs.CASE_ID) { type = NavType.IntType })
        ) { entry ->
            val id = entry.arguments?.getInt(NavArgs.CASE_ID) ?: -1
            DeleteCaseScreen(
                caseId = id,
                onBack = { navController.popBackStack() },
                onDeleted = { navController.popBackStack() }
            )
        }

        // Pantallas de Persona 2 (cascarones con navegación conectada)
        composable(Routes.CREATE_CASE) {
            CreateCaseScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.CASE_DETAIL,
            arguments = listOf(navArgument(NavArgs.CASE_ID) { type = NavType.IntType })
        ) { entry ->
            val id = entry.arguments?.getInt(NavArgs.CASE_ID) ?: -1
            CaseDetailScreen(
                caseId = id,
                onBack = { navController.popBackStack() },
                onEdit = { navController.navigate(Routes.editCase(id)) }
            )
        }

        composable(
            route = Routes.EDIT_CASE,
            arguments = listOf(navArgument(NavArgs.CASE_ID) { type = NavType.IntType })
        ) { entry ->
            val id = entry.arguments?.getInt(NavArgs.CASE_ID) ?: -1
            EditCaseScreen(
                caseId = id,
                onBack = { navController.popBackStack() }
            )
        }
    }
}