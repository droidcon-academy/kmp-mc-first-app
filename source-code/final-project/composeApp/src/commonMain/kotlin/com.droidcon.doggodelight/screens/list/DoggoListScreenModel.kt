package com.droidcon.doggodelight.screens.list

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.droidcon.doggodelight.data.Doggo
import com.droidcon.doggodelight.data.DoggoRepository
import com.droidcon.doggodelight.data.ScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DoggoListScreenModel(doggoRepository: DoggoRepository) : ScreenModel {

    private val _uiState = MutableStateFlow<ScreenState>(ScreenState.Loading)
    val uiState: StateFlow<ScreenState> = _uiState.asStateFlow()

    init {
        screenModelScope.launch {
            combine(
                doggoRepository.isRefreshed(),
                doggoRepository.getDoggo()
            ) { isRefreshed, doggos ->
                if (isRefreshed) {
                    ScreenState.Success(doggos)
                } else {
                    ScreenState.Loading
                }
            }.collect {
                _uiState.value = it
            }
        }
    }
}
