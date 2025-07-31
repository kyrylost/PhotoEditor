package dev.stukalo.presentation.feature.compressor.di

import dev.stukalo.presentation.feature.compressor.CompressorViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val featureCompressorModule = module {
    viewModelOf(::CompressorViewModel)
}