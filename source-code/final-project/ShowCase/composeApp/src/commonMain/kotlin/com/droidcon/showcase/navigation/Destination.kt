package com.droidcon.showcase.navigation

import kotlinx.serialization.Serializable

// Define a route for the product list screen (no arguments needed)
@Serializable
object ProductListScreenRoute

// Define a route for the product detail screen (with a product ID argument)
@Serializable
data class ProductDetailScreenRoute(val productId: Int)
