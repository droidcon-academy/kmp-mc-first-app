package com.droidcon.showcase.presentation.screens.productList

import androidx.lifecycle.ViewModel
import com.droidcon.showcase.data.repository.StoreRepository
import androidx.lifecycle.viewModelScope
import com.droidcon.showcase.model.Product
import com.droidcon.showcase.presentation.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductListViewModel(
    private val repository: StoreRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _uiState = MutableStateFlow<UiState<List<Product>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Product>>> = _uiState.asStateFlow()

    init {
        getProducts()
    }

    private fun getProducts() {
        viewModelScope.launch {
            repository
                .getProducts()
                .combine(_searchQuery) { list, query ->
                    if (query.isBlank()) {
                        list
                    } else {
                        list.filter { it.title.contains(query, ignoreCase = true) }
                    }
                }
                .onStart {
                    _uiState.update { UiState.Loading }
                }
                .catch { e ->
                    _uiState.update { UiState.Error(e.message ?: "Unknown error") }
                }
                .collect { productsList ->
                    if (productsList.isEmpty()) {
                        _uiState.update { UiState.Empty }
                    } else {
                        _uiState.update { UiState.Success(productsList) }
                    }
                }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }
}