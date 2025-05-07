package com.muhammadfaishalrizqipratama0094.cookit.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.muhammadfaishalrizqipratama0094.cookit.ui.screen.DetailResepScreen
import com.muhammadfaishalrizqipratama0094.cookit.ui.screen.EditResepScreen
import com.muhammadfaishalrizqipratama0094.cookit.ui.screen.HomeScreen
import com.muhammadfaishalrizqipratama0094.cookit.ui.screen.RecycleBinScreen
import com.muhammadfaishalrizqipratama0094.cookit.ui.screen.TambahResepScreen
import com.muhammadfaishalrizqipratama0094.cookit.ui.screen.ThemeSettingsScreen
import com.muhammadfaishalrizqipratama0094.cookit.viewmodel.ResepViewModel

@Composable
fun SetupNavGraph(
    navController: NavHostController = rememberNavController(),
    resepViewModel: ResepViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(
                navController = navController,
                viewModel = resepViewModel
            )
        }
        composable(route = Screen.TambahResep.route) {
            TambahResepScreen(
                navController = navController,
                viewModel = resepViewModel
            )
        }
        composable(
            route = Screen.DetailResep.route,
            arguments = listOf(
                navArgument("resepId") {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->
            val resepId = backStackEntry.arguments?.getLong("resepId") ?: 0L
            DetailResepScreen(
                navController = navController,
                viewModel = resepViewModel,
                resepId = resepId
            )
        }
        composable(
            route = Screen.EditResep.route,
            arguments = listOf(
                navArgument("resepId") {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->
            val resepId = backStackEntry.arguments?.getLong("resepId") ?: 0L
            EditResepScreen(
                navController = navController,
                viewModel = resepViewModel,
                resepId = resepId
            )
        }
        composable(route = Screen.RecycleBin.route) {
            RecycleBinScreen(
                navController = navController,
                viewModel = resepViewModel
            )
        }
        composable(route = Screen.ThemeSettings.route) {
            ThemeSettingsScreen(
                navController = navController,
                viewModel = resepViewModel
            )
        }
    }
}