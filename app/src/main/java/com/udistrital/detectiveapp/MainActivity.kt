package com.udistrital.detectiveapp

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.udistrital.detectiveapp.repository.CaseRepository
import com.udistrital.detectiveapp.screens.CaseListScreen
import com.udistrital.detectiveapp.screens.CreateCaseScreen
import com.udistrital.detectiveapp.screens.DeleteCaseScreen
import com.udistrital.detectiveapp.screens.EditCaseScreen
import com.udistrital.detectiveapp.screens.HomeScreen
import com.udistrital.detectiveapp.ui.CaseDetailScreen
import com.udistrital.detectiveapp.ui.theme.DetectiveAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DetectiveAppTheme {
                DetectiveAppNavigation()
            }
        }
    }
}

private object Routes {
    const val HOME = "home"
    const val CASE_LIST = "caseList"
    const val CREATE_CASE = "createCase"
    const val CASE_DETAIL = "caseDetail/{caseId}"
    const val EDIT_CASE = "editCase/{caseId}"
    const val DELETE_CASE = "deleteCase/{caseId}"

    fun caseDetail(caseId: Int) = "caseDetail/$caseId"
    fun editCase(caseId: Int) = "editCase/$caseId"
    fun deleteCase(caseId: Int) = "deleteCase/$caseId"
}

@Composable
fun DetectiveAppNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = Routes.HOME) {

        composable(Routes.HOME) {
            val context = LocalContext.current
            HomeScreen(
                onViewCases = { navController.navigate(Routes.CASE_LIST) },
                onNewCase = { navController.navigate(Routes.CREATE_CASE) },
                onExit = { (context as? Activity)?.finish() }
            )
        }

        composable(Routes.CASE_LIST) {
            CaseListScreen(
                onBack = { navController.popBackStack() },
                onViewCase = { caseId -> navController.navigate(Routes.caseDetail(caseId)) },
                onEditCase = { caseId -> navController.navigate(Routes.editCase(caseId)) },
                onDeleteCase = { caseId -> navController.navigate(Routes.deleteCase(caseId)) }
            )
        }

        composable(Routes.CREATE_CASE) {
            val context = LocalContext.current
            val repository = remember { CaseRepository(context) }

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
            arguments = listOf(navArgument("caseId") { type = NavType.IntType })
        ) { backStackEntry ->
            val caseId = backStackEntry.arguments?.getInt("caseId") ?: return@composable

            val context = LocalContext.current
            val repository = remember { CaseRepository(context) }
            val case = remember(caseId) { repository.getCaseById(caseId) }


            if (case != null) {
                CaseDetailScreen(
                    case = case,
                    onBack = { navController.popBackStack() }
                )
            } else {
                CaseNotFound(onBack = { navController.popBackStack() })
            }
        }

        composable(
            route = Routes.EDIT_CASE,
            arguments = listOf(navArgument("caseId") { type = NavType.IntType })
        ) { backStackEntry ->
            val caseId = backStackEntry.arguments?.getInt("caseId") ?: return@composable
            EditCaseScreen(
                caseId = caseId,
                onBack = { navController.popBackStack() },
                onSaved = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.DELETE_CASE,
            arguments = listOf(navArgument("caseId") { type = NavType.IntType })
        ) { backStackEntry ->
            val caseId = backStackEntry.arguments?.getInt("caseId") ?: return@composable
            DeleteCaseScreen(
                caseId = caseId,
                onBack = { navController.popBackStack() },
                // Al borrar vuelve a la lista de casos
                onDeleted = { navController.popBackStack() }
            )
        }
    }
}

@Composable
private fun CaseNotFound(onBack: () -> Unit) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Case not found", style = MaterialTheme.typography.titleMedium)
            Button(onClick = onBack) {
                Text("Volver")
            }
        }
    }
}