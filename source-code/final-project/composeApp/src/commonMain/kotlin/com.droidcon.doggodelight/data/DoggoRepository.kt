package com.droidcon.doggodelight.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class DoggoRepository(
    private val doggoApi: DoggoApi,
    private val doggoStorage: DoggoStorage,
) {
    private val scope = CoroutineScope(SupervisorJob())

    fun initialize() {
        scope.launch {
            refresh()
        }
    }

    suspend fun refresh() {
        doggoStorage.saveDoggos(doggoApi.getData())
    }

    fun getDoggo(): Flow<List<Doggo>> = doggoStorage.getDoggos()

    fun getDoggoById(id: Int): Flow<Doggo?> = doggoStorage.getDoggoById(id)
}
