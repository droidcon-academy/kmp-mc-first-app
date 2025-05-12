package com.droidcon.showcase.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.droidcon.showcase.presentation.screens.productDetail.ProductDetailScreen
import com.droidcon.showcase.presentation.screens.productList.ProductListScreen
import androidx.navigation.toRoute

@Composable
fun NavGraph() {
    // Initialize NavController (the train driver)
    val navController = rememberNavController()

    // Set up the NavHost with our NavController and routes
    NavHost(
        navController = navController,
        startDestination = ProductListScreenRoute  // start at the product list
    ) {
        // Destination 1: Product List Screen
        composable<ProductListScreenRoute> {
            ProductListScreen(
                onProductClick = { product ->
                    // Navigate to the detail screen route when a product is clicked
                    navController.navigate(ProductDetailScreenRoute(product.id))
                }
            )
        }
        // Destination 2: Product Detail Screen
        composable<ProductDetailScreenRoute> { backStackEntry ->
            // Recreate the route object from the nav back stack (to get arguments)
            val detailRoute: ProductDetailScreenRoute = backStackEntry.toRoute()
            ProductDetailScreen(
                productId = detailRoute.productId,
                onBackClick = {
                    navController.popBackStack()
                },
            )
        }
    }
}