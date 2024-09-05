package com.droidcon.doggodelight.screens.detail

import cafe.adriel.voyager.core.model.ScreenModel
import com.droidcon.doggodelight.data.Doggo
import com.droidcon.doggodelight.data.DoggoRepository
import kotlinx.coroutines.flow.Flow

class DetailScreenModel(private val doggoRepository: DoggoRepository) : ScreenModel {
    fun getDoggo(id: Int): Flow<Doggo?> =
        doggoRepository.getDoggoById(id)
}
