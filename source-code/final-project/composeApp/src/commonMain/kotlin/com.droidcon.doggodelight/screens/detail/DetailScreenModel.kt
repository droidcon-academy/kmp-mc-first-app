package com.droidcon.doggodelight.screens.detail

import cafe.adriel.voyager.core.model.ScreenModel
import com.droidcon.doggodelight.data.DoggoObject
import com.droidcon.doggodelight.data.DoggoRepository
import kotlinx.coroutines.flow.Flow

class DetailScreenModel(private val doggoRepository: DoggoRepository) : ScreenModel {
    fun getObject(id: Int): Flow<DoggoObject?> =
        doggoRepository.getObjectById(id)
}
