package com.augieafr.mealrecipe

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.augieafr.mealrecipe.ui.home_screen.HomeScreen
import com.augieafr.mealrecipe.ui.navigation.Screen

@Composable
fun MealApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = {
            EnterTransition.None
        },
        builder = {
            composable(Screen.Home.route) {
                HomeScreen(
                    modifier,
                    navigateToDetail = {
                        navController.navigate(Screen.DetailMeal.createRoute(it))
                    },
                    navigateToFavorite = {
                    })
            }
            composable(
                route = Screen.DetailMeal.route,
                arguments = listOf(navArgument(Screen.DETAIL_MEAL_ID_KEY) {
                    type = NavType.StringType
                })
            ) {
                val mealId = it.arguments?.getString(Screen.DETAIL_MEAL_ID_KEY).orEmpty()
                //TODO: add detail screen
            }
        })
}