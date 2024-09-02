package com.droidcon.doggodelight.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

interface DoggoStorage {
    suspend fun saveObjects(newObjects: List<DoggoObject>)

    fun getObjectById(id: Int): Flow<DoggoObject?>

    fun getObjects(): Flow<List<DoggoObject>>
}

class InMemoryDoggoStorage : DoggoStorage {
    private val storedObjects = MutableStateFlow(emptyList<DoggoObject>())

    override suspend fun saveObjects(newObjects: List<DoggoObject>) {
        storedObjects.value = newObjects
    }

    override fun getObjectById(id: Int): Flow<DoggoObject?> {
        return storedObjects.map { objects ->
            objects.find { it.id == id }
        }
    }

    override fun getObjects(): Flow<List<DoggoObject>> = storedObjects
}
