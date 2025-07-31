package dev.stukalo.presentation.navigation.processing.flow.di

import dev.stukalo.presentation.navigation.processing.flow.ProcessingFlowViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val processingFlowModule = module {

    viewModelOf (::ProcessingFlowViewModel)

}