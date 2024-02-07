package com.danielpasser.categories.compose

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.danielpasser.categories.compose.categorydetails.CategoryDetailsScreen
import com.danielpasser.categories.compose.categorieslist.CategoriesListScreen

@Composable
fun CategoriesApp() {
    val navController = rememberNavController()
    CategoriesNavController(navController = navController)
}

@Composable
fun CategoriesNavController(navController: NavHostController) {
    NavHost(navController = navController, startDestination = ScreenNav.ProductsList.route) {
        composable(route = ScreenNav.ProductsList.route) {
            CategoriesListScreen(onClickItem = {
                navController.navigate(
                    ScreenNav.CategoryDetails.createRoute(
                        it
                    )
                )
            })
        }
        composable(route = ScreenNav.CategoryDetails.route) {
            CategoryDetailsScreen(onBackPressed = { navController.popBackStack() })
        }
    }
}