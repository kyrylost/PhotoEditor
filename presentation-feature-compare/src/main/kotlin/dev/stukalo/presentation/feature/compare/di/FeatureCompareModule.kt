package dev.stukalo.presentation.feature.compare.di

import dev.stukalo.presentation.feature.compare.CompareViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureCompareModule = module {
    viewModel { CompareViewModel() }
}