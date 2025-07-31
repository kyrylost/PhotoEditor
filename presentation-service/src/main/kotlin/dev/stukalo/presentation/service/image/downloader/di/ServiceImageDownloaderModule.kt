package dev.stukalo.presentation.service.image.downloader.di

import dev.stukalo.presentation.service.image.downloader.ImageDTO
import org.koin.dsl.module

val serviceImageDownloaderModule = module {
    single { ImageDTO() }
}
