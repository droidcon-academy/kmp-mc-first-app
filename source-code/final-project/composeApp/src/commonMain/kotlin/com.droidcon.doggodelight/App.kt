package com.droidcon.doggodelight

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.droidcon.doggodelight.screens.list.DoggoListScreen

@Composable
fun App() {
    MaterialTheme {
        Navigator(DoggoListScreen)
    }
}
