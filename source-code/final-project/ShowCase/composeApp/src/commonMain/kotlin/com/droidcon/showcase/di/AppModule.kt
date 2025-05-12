package com.droidcon.showcase.di

import com.droidcon.showcase.data.remote.StoreApi
import com.droidcon.showcase.data.repository.StoreRepository
import com.droidcon.showcase.presentation.screens.productDetail.ProductDetailViewModel
import com.droidcon.showcase.presentation.screens.productList.ProductListViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    // Provide a single instance of StoreApi
    singleOf(::StoreApi)

    // Provide a single instance of StoreRepository, injecting StoreApi
    singleOf(::StoreRepository)

    // Provide ViewModel instances (scoped to Koin's viewModel scope)
    viewModelOf(::ProductListViewModel)
    viewModelOf(::ProductDetailViewModel)
}