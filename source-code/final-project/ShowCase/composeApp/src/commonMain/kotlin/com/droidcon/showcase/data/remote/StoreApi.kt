package com.droidcon.showcase.data.remote

import com.droidcon.showcase.model.Product
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class StoreApi {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    private val baseUrl = "https://fakestoreapi.com"

    suspend fun getProducts(): List<Product> =
        client.get("$baseUrl/products").body()

    suspend fun getProduct(id: Int): Product =
        client.get("$baseUrl/products/$id").body()
}