package com.droidcon.showcase.presentation.screens.productDetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.droidcon.showcase.model.Product
import com.droidcon.showcase.presentation.common.UiState
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import showcase.composeapp.generated.resources.Res
import showcase.composeapp.generated.resources.category_format
import showcase.composeapp.generated.resources.navigate_back
import showcase.composeapp.generated.resources.no_products_found
import showcase.composeapp.generated.resources.price_format
import showcase.composeapp.generated.resources.rating_format

@Composable
fun ProductDetailScreen(
    productId: Int,
    onBackClick: () -> Unit
) {
    val viewModel: ProductDetailViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(productId) {
        viewModel.loadProduct(productId)
    }

    when (val state = uiState) {
        is UiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is UiState.Error -> {
            Text(
                text = state.message,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.fillMaxSize().padding(16.dp),
                textAlign = TextAlign.Center
            )
        }
        is UiState.Empty -> {
            Text(
                text = stringResource(Res.string.no_products_found),
                modifier = Modifier.fillMaxSize().padding(16.dp),
                textAlign = TextAlign.Center
            )
        }
        is UiState.Success -> {
            ProductDetailContent(
                product = state.data,
                onBackClick = onBackClick
            )
        }
    }
}

// The UI for the success state (product details)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProductDetailContent(product: Product, onBackClick: () -> Unit) {
    Scaffold(
        topBar = { // (2)
            TopAppBar(
                title = {
                    Text(
                        text = product.title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) { // (3)
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(Res.string.navigate_back)
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        },
    ) { innerPadding -> // (4)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()) // make content scrollable if needed
                .padding(
                    PaddingValues(
                        top = innerPadding.calculateTopPadding(), // (5)
                        start = 16.dp,
                        end = 16.dp,
                        bottom = innerPadding.calculateBottomPadding()
                    )
                )
        ) {
            // Product Image – larger size
            CoilImage(
                imageModel = { product.image },
                imageOptions = ImageOptions(contentScale = ContentScale.Fit),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(12.dp)),
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Title
            Text(
                text = product.title,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            // Price
            Text(
                text = stringResource(Res.string.price_format, product.price),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            // Category
            Text(
                text = stringResource(Res.string.category_format, product.category),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Rating
            Text(
                text = stringResource(Res.string.rating_format, product.rating.rate, product.rating.count),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Description
            Text(
                text = product.description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}