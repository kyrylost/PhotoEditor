package dev.stukalo.presentation.feature.search.di

import dev.stukalo.presentation.feature.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureSearchModule = module {
    viewModel { SearchViewModel(get(), get()) }
}