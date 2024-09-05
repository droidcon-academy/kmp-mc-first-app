package com.droidcon.doggodelight.screens.list

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.droidcon.doggodelight.data.Doggo
import com.droidcon.doggodelight.data.DoggoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class DoggoListScreenModel(doggoRepository: DoggoRepository) : ScreenModel {
    val doggos: StateFlow<List<Doggo>> =
        doggoRepository.getDoggo()
            .stateIn(screenModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}
