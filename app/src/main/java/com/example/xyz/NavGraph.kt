package com.example.myapp.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.xyz.DietPlanApp
import com.example.xyz.GeminiViewModel
import com.example.xyz.SplashScreen

// Define your routes
sealed class Screen(val route: String) {
    object Splash : Screen("home")
    object Diet : Screen("DietPlan")
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route // default screen
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                modifier = Modifier,
                onNavigateToDetails = {
                    navController.navigate(Screen.Diet.route) {
                        // Clear the back stack to prevent returning to the splash screen
                      //  popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Diet.route) {
            val geminiViewModel: GeminiViewModel = viewModel() // ViewModel পাওয়া যাবে এখানে
            DietPlanApp(geminiViewModel) // pass it into your composable

        }
    }
}
