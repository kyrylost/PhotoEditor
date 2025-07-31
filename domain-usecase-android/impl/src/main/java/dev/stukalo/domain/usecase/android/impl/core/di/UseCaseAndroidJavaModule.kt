package dev.stukalo.domain.usecase.android.impl.core.di

import dev.stukalo.domain.usecase.android.compression.downsampling.CompressBitmapDownsamplingJavaUseCase
import dev.stukalo.domain.usecase.android.compression.rle.CompressBitmapRLEJavaUseCase
import dev.stukalo.domain.usecase.android.impl.compression.downsampling.CompressBitmapDownsamplingJavaUseCaseImpl
import dev.stukalo.domain.usecase.android.impl.compression.rle.CompressBitmapRLEJavaUseCaseImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val domainUseCaseAndroidJavaModule = module {

    singleOf(::CompressBitmapRLEJavaUseCaseImpl) bind
            CompressBitmapRLEJavaUseCase::class
    singleOf(::CompressBitmapDownsamplingJavaUseCaseImpl) bind
            CompressBitmapDownsamplingJavaUseCase::class

}
