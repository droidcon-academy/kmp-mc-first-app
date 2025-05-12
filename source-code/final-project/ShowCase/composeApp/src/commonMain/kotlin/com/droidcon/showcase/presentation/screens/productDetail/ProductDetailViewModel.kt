package com.droidcon.showcase.presentation.screens.productDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidcon.showcase.data.repository.StoreRepository
import com.droidcon.showcase.model.Product
import com.droidcon.showcase.presentation.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    private val repository: StoreRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<Product>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun loadProduct(id: Int) {
        viewModelScope.launch {
            repository.getProduct(id)
                .onStart {
                    _uiState.update {
                        UiState.Loading
                    }
                }
                .catch { e ->
                    _uiState.update { UiState.Error(e.message ?: "Unknown error") }
                }
                .collect { product ->
                    _uiState.update {
                        UiState.Success(product)
                    }
                }
        }
    }
}


