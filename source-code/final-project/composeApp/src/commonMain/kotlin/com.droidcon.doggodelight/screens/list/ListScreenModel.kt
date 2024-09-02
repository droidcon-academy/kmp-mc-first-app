package com.droidcon.doggodelight.screens.list

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.droidcon.doggodelight.data.DoggoObject
import com.droidcon.doggodelight.data.DoggoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class ListScreenModel(doggoRepository: DoggoRepository) : ScreenModel {
    val objects: StateFlow<List<DoggoObject>> =
        doggoRepository.getObjects()
            .stateIn(screenModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}
