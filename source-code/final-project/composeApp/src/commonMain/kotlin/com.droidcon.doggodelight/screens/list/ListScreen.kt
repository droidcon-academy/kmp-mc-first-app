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
import androidx.compose.foundation.lazy.LazyRow
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
import com.droidcon.doggodelight.data.DoggoObject
import com.droidcon.doggodelight.screens.EmptyScreenContent
import com.droidcon.doggodelight.screens.detail.DetailScreen
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

data object ListScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel: ListScreenModel = getScreenModel()

        val objects by screenModel.objects.collectAsState()

        AnimatedContent(objects.isNotEmpty()) { objectsAvailable ->
            if (objectsAvailable) {
                ObjectGrid(
                    objects = objects,
                    onObjectClick = { id ->
                        navigator.push(DetailScreen(id))
                    }
                )
            } else {
                EmptyScreenContent(Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
private fun ObjectGrid(
    objects: List<DoggoObject>,
    onObjectClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyRow() {  }
    LazyVerticalGrid(
        columns = GridCells.Adaptive(180.dp),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(objects, key = { it.id }) { obj ->
            ObjectFrame(
                obj = obj,
                onClick = { onObjectClick(obj.id) },
            )
        }
    }
}

@Composable
private fun ObjectFrame(
    obj: DoggoObject,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        KamelImage(
            resource = asyncPainterResource(data = obj.image.url),
            contentDescription = obj.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(Color.LightGray),
        )

        Spacer(Modifier.height(2.dp))

        Text(obj.name, style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold))
        Text(obj.bredFor ?: "", style = MaterialTheme.typography.body2)
        Text(obj.origin ?: "", style = MaterialTheme.typography.caption)
    }
}
