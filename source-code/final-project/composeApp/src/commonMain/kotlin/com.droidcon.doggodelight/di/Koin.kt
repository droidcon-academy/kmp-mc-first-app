package com.droidcon.doggodelight.di

import com.droidcon.doggodelight.data.InMemoryDoggoStorage
import com.droidcon.doggodelight.data.KtorDoggoApi
import com.droidcon.doggodelight.data.DoggoApi
import com.droidcon.doggodelight.data.DoggoRepository
import com.droidcon.doggodelight.data.DoggoStorage
import com.droidcon.doggodelight.screens.detail.DetailScreenModel
import com.droidcon.doggodelight.screens.list.DoggoListScreenModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val dataModule = module {
    single {
        val json = Json { ignoreUnknownKeys = true }
        HttpClient {
            install(ContentNegotiation) {
                json(json, contentType = ContentType.Any)
            }
        }
    }

    single<DoggoApi> { KtorDoggoApi(get()) }
    single<DoggoStorage> { InMemoryDoggoStorage() }
    single {
        DoggoRepository(get(), get()).apply {
            initialize()
        }
    }
}

val screenModelsModule = module {
    factoryOf(::DoggoListScreenModel)
    factoryOf(::DetailScreenModel)
}

fun initKoin() {
    startKoin {
        modules(
            dataModule,
            screenModelsModule,
        )
    }
}
