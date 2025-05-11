package com.droidcon.showcase

import androidx.compose.runtime.Composable
import androidx.compose.material3.MaterialTheme
import com.droidcon.showcase.navigation.NavGraph

@Composable
fun App() {
    MaterialTheme {
        NavGraph()
    }
}