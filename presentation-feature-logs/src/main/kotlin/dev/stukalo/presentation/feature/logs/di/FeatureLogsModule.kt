package dev.stukalo.presentation.feature.logs.di

import dev.stukalo.presentation.feature.logs.LogsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val featureLogsModule = module {
    viewModelOf(::LogsViewModel)
}