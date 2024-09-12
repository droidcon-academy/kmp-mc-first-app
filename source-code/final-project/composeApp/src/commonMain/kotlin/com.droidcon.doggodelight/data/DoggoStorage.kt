package com.droidcon.doggodelight.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

interface DoggoStorage {
    suspend fun saveDoggos(newDoggos: List<Doggo>)

    fun isRefreshed() : Flow<Boolean>

    fun getDoggoById(id: Int): Flow<Doggo?>

    fun getDoggos(): Flow<List<Doggo>>
}

class InMemoryDoggoStorage : DoggoStorage {
    private val storedDoggos = MutableStateFlow(emptyList<Doggo>())
    private val isRefreshed = MutableStateFlow(false)

    override fun isRefreshed() : Flow<Boolean>{
        return isRefreshed
    }

    override suspend fun saveDoggos(newDoggos: List<Doggo>) {
        storedDoggos.value = newDoggos
        isRefreshed.value = true
    }

    override fun getDoggoById(id: Int): Flow<Doggo?> {
        return storedDoggos.map { doggos ->
            doggos.find { it.id == id }
        }
    }

    override fun getDoggos(): Flow<List<Doggo>> = storedDoggos
}
