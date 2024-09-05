package com.droidcon.doggodelight.screens.list

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.droidcon.doggodelight.data.Doggo
import com.droidcon.doggodelight.screens.EmptyScreenContent
import com.droidcon.doggodelight.screens.detail.DoggoDetailScreen
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

data object DoggoListScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel: DoggoListScreenModel = getScreenModel()

        val doggosList by screenModel.doggos.collectAsState()

        AnimatedContent(doggosList.isNotEmpty()) { doggos ->
            if (doggos) {
                DoggoGrid(
                    doggoList = doggosList,
                    onDoggoClick = { id ->
                        navigator.push(DoggoDetailScreen(id))
                    }
                )
            } else {
                EmptyScreenContent(Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
private fun DoggoGrid(
    doggoList: List<Doggo>,
    onDoggoClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(180.dp),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(doggoList, key = { it.id }) { doggo ->
            DoggoFrame(
                doggo = doggo,
                onClick = { onDoggoClick(doggo.id) },
            )
        }
    }
}

@Composable
private fun DoggoFrame(
    doggo: Doggo,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        KamelImage(
            resource = asyncPainterResource(data = doggo.image.url),
            contentDescription = doggo.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(Color.LightGray),
        )

        Spacer(Modifier.height(2.dp))

        Text(doggo.name, style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold))
        Text(doggo.bredFor ?: "", style = MaterialTheme.typography.body2)
        Text(doggo.origin ?: "", style = MaterialTheme.typography.caption)
    }
}
