package com.droidcon.doggodelight.screens.detail

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.droidcon.doggodelight.data.Doggo
import com.droidcon.doggodelight.screens.EmptyScreenContent
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import doggo_delight.composeapp.generated.resources.Res
import doggo_delight.composeapp.generated.resources.back
import doggo_delight.composeapp.generated.resources.label_title
import doggo_delight.composeapp.generated.resources.label_bred_for
import doggo_delight.composeapp.generated.resources.label_bred_group
import doggo_delight.composeapp.generated.resources.label_temperament
import doggo_delight.composeapp.generated.resources.description
import doggo_delight.composeapp.generated.resources.label_country
import doggo_delight.composeapp.generated.resources.label_history
import doggo_delight.composeapp.generated.resources.label_origin
import doggo_delight.composeapp.generated.resources.label_weight
import doggo_delight.composeapp.generated.resources.label_height
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

data class DoggoDetailScreen(val id: Int) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel: DetailScreenModel = getScreenModel()

        val doggo by screenModel.getDoggo(id).collectAsState(initial = null)
        AnimatedContent(doggo != null) { doggoAvailable ->
            if (doggoAvailable) {
                DoggoDetails(doggo!!, onBackClick = { navigator.pop() })
            } else {
                EmptyScreenContent(Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
private fun DoggoDetails(
    doggo: Doggo,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            TopAppBar(backgroundColor = Color.White) {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, stringResource(Res.string.back))
                }
            }
        },
        modifier = modifier,
    ) { paddingValues ->
        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
        ) {
            KamelImage(
                resource = asyncPainterResource(data = doggo.image.url),
                contentDescription = doggo.name,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
            )

            SelectionContainer {
                Column(Modifier.padding(12.dp)) {
                    Text(doggo.name, style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold))
                    Spacer(Modifier.height(6.dp))
                    LabeledInfo(stringResource(Res.string.label_title), doggo.name)
                    LabeledInfo(stringResource(Res.string.label_bred_for), doggo.bredFor ?: "")
                    LabeledInfo(stringResource(Res.string.label_bred_group), doggo.breedGroup?: "")
                    LabeledInfo(stringResource(Res.string.label_temperament), doggo.temperament?: "")
                    LabeledInfo(stringResource(Res.string.description), doggo.description?: "")
                    LabeledInfo(stringResource(Res.string.label_country), doggo.countryCode ?: "")
                    LabeledInfo(stringResource(Res.string.label_history), doggo.history ?: "")
                    LabeledInfo(stringResource(Res.string.label_origin), doggo.origin ?: "")
                    LabeledInfo(stringResource(Res.string.label_weight), doggo.weight.metric)
                    LabeledInfo(stringResource(Res.string.label_height), doggo.height.metric)
                }
            }
        }
    }
}

@Composable
private fun LabeledInfo(
    label: String,
    data: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier.padding(vertical = 4.dp)) {
        Spacer(Modifier.height(6.dp))
        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("$label: ")
                }
                append(data)
            }
        )
    }
}
