package com.droidcon.doggodelight.data

sealed class ScreenState {
    object Loading : ScreenState()
    data class Success(val data: List<Doggo>) : ScreenState()
    object Error : ScreenState()
}