package dev.stukalo.domain.usecase.android.impl.core.di

import dev.stukalo.domain.usecase.android.compression.downsampling.CompressBitmapDownsamplingAsyncTaskUseCase
import dev.stukalo.domain.usecase.android.compression.downsampling.CompressBitmapDownsamplingCoroutinesUseCase
import dev.stukalo.domain.usecase.android.compression.downsampling.CompressBitmapDownsamplingUseCase
import dev.stukalo.domain.usecase.android.compression.downsampling_rle.CompressBitmapDownsamplingRleAsyncTaskUseCase
import dev.stukalo.domain.usecase.android.compression.downsampling_rle.CompressBitmapDownsamplingRleCoroutinesUseCase
import dev.stukalo.domain.usecase.android.compression.downsampling_rle.CompressBitmapDownsamplingRleJavaUseCase
import dev.stukalo.domain.usecase.android.compression.downsampling_rle.CompressBitmapDownsamplingRleUseCase
import dev.stukalo.domain.usecase.android.compression.rle.CompressBitmapRLEAsyncTaskUseCase
import dev.stukalo.domain.usecase.android.compression.rle.CompressBitmapRLECoroutinesUseCase
import dev.stukalo.domain.usecase.android.compression.rle.CompressBitmapRLEUseCase
import dev.stukalo.domain.usecase.android.decompression.downsampling.DecompressByteArrayDownsamplingUseCase
import dev.stukalo.domain.usecase.android.decompression.rle.DecompressByteArrayRLEUseCase
import dev.stukalo.domain.usecase.android.filestore.RetrieveImagesFromInternalStorageUseCase
import dev.stukalo.domain.usecase.android.filestore.SaveImageDataToInternalStorageUseCase
import dev.stukalo.domain.usecase.android.impl.compression.downsampling.CompressBitmapDownsamplingAsyncTaskUseCaseImpl
import dev.stukalo.domain.usecase.android.impl.compression.downsampling.CompressBitmapDownsamplingCoroutinesUseCaseImpl
import dev.stukalo.domain.usecase.android.impl.compression.downsampling.CompressBitmapDownsamplingUseCaseImpl
import dev.stukalo.domain.usecase.android.impl.compression.downsampling_rle.CompressBitmapDownsamplingRleAsyncTaskUseCaseImpl
import dev.stukalo.domain.usecase.android.impl.compression.downsampling_rle.CompressBitmapDownsamplingRleCoroutinesUseCaseImpl
import dev.stukalo.domain.usecase.android.impl.compression.downsampling_rle.CompressBitmapDownsamplingRleJavaUseCaseImpl
import dev.stukalo.domain.usecase.android.impl.compression.downsampling_rle.CompressBitmapDownsamplingRleUseCaseImpl
import dev.stukalo.domain.usecase.android.impl.compression.rle.CompressBitmapRLEAsyncTaskUseCaseImpl
import dev.stukalo.domain.usecase.android.impl.compression.rle.CompressBitmapRLECoroutinesUseCaseImpl
import dev.stukalo.domain.usecase.android.impl.compression.rle.CompressBitmapRLEUseCaseImpl
import dev.stukalo.domain.usecase.android.impl.decompression.downsampling.DecompressByteArrayDownsamplingUseCaseImpl
import dev.stukalo.domain.usecase.android.impl.decompression.rle.DecompressByteArrayRLEUseCaseImpl
import dev.stukalo.domain.usecase.android.impl.filestore.RetrieveImagesFromInternalStorageUseCaseImpl
import dev.stukalo.domain.usecase.android.impl.filestore.SaveImageDataToInternalStorageUseCaseImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val domainUseCaseAndroidModule = module {

    singleOf(::CompressBitmapRLEAsyncTaskUseCaseImpl) bind
            CompressBitmapRLEAsyncTaskUseCase::class
    singleOf(::CompressBitmapRLECoroutinesUseCaseImpl) bind
            CompressBitmapRLECoroutinesUseCase::class
    singleOf(::CompressBitmapRLEUseCaseImpl) bind
            CompressBitmapRLEUseCase::class

    singleOf(::CompressBitmapDownsamplingAsyncTaskUseCaseImpl) bind
            CompressBitmapDownsamplingAsyncTaskUseCase::class
    singleOf(::CompressBitmapDownsamplingCoroutinesUseCaseImpl) bind
            CompressBitmapDownsamplingCoroutinesUseCase::class
    singleOf(::CompressBitmapDownsamplingUseCaseImpl) bind
            CompressBitmapDownsamplingUseCase::class

    singleOf(::CompressBitmapDownsamplingRleAsyncTaskUseCaseImpl) bind
            CompressBitmapDownsamplingRleAsyncTaskUseCase::class
    singleOf(::CompressBitmapDownsamplingRleCoroutinesUseCaseImpl) bind
            CompressBitmapDownsamplingRleCoroutinesUseCase::class
    singleOf(::CompressBitmapDownsamplingRleJavaUseCaseImpl) bind
            CompressBitmapDownsamplingRleJavaUseCase::class
    singleOf(::CompressBitmapDownsamplingRleUseCaseImpl) bind
            CompressBitmapDownsamplingRleUseCase::class

    singleOf(::DecompressByteArrayRLEUseCaseImpl) bind
            DecompressByteArrayRLEUseCase::class
    singleOf(::DecompressByteArrayDownsamplingUseCaseImpl) bind
            DecompressByteArrayDownsamplingUseCase::class


    singleOf(::RetrieveImagesFromInternalStorageUseCaseImpl) bind
            RetrieveImagesFromInternalStorageUseCase::class

    singleOf(::SaveImageDataToInternalStorageUseCaseImpl) bind
            SaveImageDataToInternalStorageUseCase::class

}
