package com.droidcon.showcase.data.repository

import com.droidcon.showcase.data.remote.StoreApi
import com.droidcon.showcase.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class StoreRepository(private val api: StoreApi) {

    fun getProducts(): Flow<List<Product>> = flow {
        val products = api.getProducts()
        emit(products)
    }

    fun getProduct(id: Int): Flow<Product> = flow {
        val product = api.getProduct(id)
        emit(product)
    }
}