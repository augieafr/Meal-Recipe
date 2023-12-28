package com.augieafr.mealrecipe.ui.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object DetailMeal : Screen("home/{$DETAIL_MEAL_ID_KEY}") {
        fun createRoute(mealId: String) = "home/$mealId"
    }

    data object Bookmark : Screen("home/bookmark")

    companion object {
        const val DETAIL_MEAL_ID_KEY = "meal_id"
    }
}