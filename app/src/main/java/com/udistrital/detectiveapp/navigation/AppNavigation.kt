package com.udistrital.detectiveapp.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.udistrital.detectiveapp.NavArgs
import com.udistrital.detectiveapp.repository.CaseRepository
import com.udistrital.detectiveapp.screens.CaseListScreen
import com.udistrital.detectiveapp.screens.CreateCaseScreen
import com.udistrital.detectiveapp.screens.DeleteCaseScreen
import com.udistrital.detectiveapp.screens.EditCaseScreen
import com.udistrital.detectiveapp.screens.HomeScreen
import com.udistrital.detectiveapp.ui.CaseDetailScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val repository = remember { CaseRepository(context) }

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

        composable(Routes.CREATE_CASE) {
            CreateCaseScreen(
                onSaveClick = { newCase ->
                    repository.createCase(newCase)
                    navController.popBackStack()
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.CASE_DETAIL,
            arguments = listOf(navArgument(NavArgs.CASE_ID) { type = NavType.IntType })
        ) { entry ->
            val id = entry.arguments?.getInt(NavArgs.CASE_ID) ?: -1
            val case = remember(id) { repository.getCaseById(id) }
            if (case != null) {
                CaseDetailScreen(
                    case = case,
                    onBack = { navController.popBackStack() }
                )
            } else {
                navController.popBackStack()
            }
        }

        composable(
            route = Routes.EDIT_CASE,
            arguments = listOf(navArgument(NavArgs.CASE_ID) { type = NavType.IntType })
        ) { entry ->
            val id = entry.arguments?.getInt(NavArgs.CASE_ID) ?: -1
            EditCaseScreen(
                caseId = id,
                onBack = { navController.popBackStack() },
                onSaved = { navController.popBackStack() }
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
    }
}