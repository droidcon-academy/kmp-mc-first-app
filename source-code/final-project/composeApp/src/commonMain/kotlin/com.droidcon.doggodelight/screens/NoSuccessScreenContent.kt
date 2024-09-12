package com.droidcon.doggodelight.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import doggo_delight.composeapp.generated.resources.Res
import doggo_delight.composeapp.generated.resources.no_data_available
import org.jetbrains.compose.resources.stringResource

@Composable
fun NoSuccessScreenContent(
    modifier: Modifier = Modifier,
    message: String,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Text(message)
    }
}
