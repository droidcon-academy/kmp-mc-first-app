package com.droidcon.doggodelight.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

interface DoggoStorage {
    suspend fun saveDoggos(newDoggos: List<Doggo>)

    fun getDoggoById(id: Int): Flow<Doggo?>

    fun getDoggos(): Flow<List<Doggo>>
}

class InMemoryDoggoStorage : DoggoStorage {
    private val storedDoggos = MutableStateFlow(emptyList<Doggo>())

    override suspend fun saveDoggos(newDoggos: List<Doggo>) {
        storedDoggos.value = newDoggos
    }

    override fun getDoggoById(id: Int): Flow<Doggo?> {
        return storedDoggos.map { doggos ->
            doggos.find { it.id == id }
        }
    }

    override fun getDoggos(): Flow<List<Doggo>> = storedDoggos
}
