package dev.stukalo.presentation.feature.downloads.di

import dev.stukalo.presentation.feature.downloads.DownloadsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureDownloadsModule = module {
    viewModel { DownloadsViewModel( get() ) }
}