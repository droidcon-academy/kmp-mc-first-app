package com.droidcon.showcase

import androidx.compose.ui.window.ComposeUIViewController
import com.droidcon.showcase.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }