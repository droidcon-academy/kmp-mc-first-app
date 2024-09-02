package com.droidcon.doggodelight.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.utils.io.CancellationException

interface DoggoApi {
    suspend fun getData(): List<DoggoObject>
}

class KtorDoggoApi(private val client: HttpClient) : DoggoApi {
    companion object {
        private const val API_URL =
            "https://raw.githubusercontent.com/droidcon-academy/kmp-mc-first-app/main/helper-files/doggo_list.json"
    }

    override suspend fun getData(): List<DoggoObject> {
        return try {
            client.get(API_URL).body()
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            e.printStackTrace()

            emptyList()
        }
    }
}
