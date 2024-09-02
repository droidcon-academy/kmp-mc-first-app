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
        doggoStorage.saveObjects(doggoApi.getData())
    }

    fun getObjects(): Flow<List<DoggoObject>> = doggoStorage.getObjects()

    fun getObjectById(id: Int): Flow<DoggoObject?> = doggoStorage.getObjectById(id)
}
