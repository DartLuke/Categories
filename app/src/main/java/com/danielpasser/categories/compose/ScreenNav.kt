package com.danielpasser.categories.compose

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.danielpasser.categories.viewmodels.CategoryDetailsViewModel


sealed class ScreenNav(val route: String, val navArguments: List<NamedNavArgument> = emptyList()) {
    data object ProductsList : ScreenNav("productsList")
    data object CategoryDetails : ScreenNav(
        "$CATEGORY_DETAILS/{${CategoryDetailsViewModel.CATEGORY}}",
        navArguments = listOf(navArgument(CategoryDetailsViewModel.CATEGORY) {
            type = NavType.StringType
        })
    ) {
        fun createRoute(category: String?) = "$CATEGORY_DETAILS/$category"
    }

    private companion object {
        const val CATEGORY_DETAILS = "CATEGORY_DETAILS"
    }
}
