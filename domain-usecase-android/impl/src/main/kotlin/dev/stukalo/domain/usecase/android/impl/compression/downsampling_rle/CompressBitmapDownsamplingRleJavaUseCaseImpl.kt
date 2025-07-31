package dev.stukalo.domain.usecase.android.impl.compression.downsampling_rle

import android.graphics.Bitmap
import dev.stukalo.domain.usecase.android.compression.downsampling_rle.CompressBitmapDownsamplingRleJavaUseCase
import dev.stukalo.domain.usecase.android.compression.downsampling.CompressBitmapDownsamplingJavaUseCase
import dev.stukalo.domain.usecase.android.compression.rle.CompressBitmapRLEJavaUseCase

internal class CompressBitmapDownsamplingRleJavaUseCaseImpl(
    val compressBitmapDownsamplingJavaUseCase: CompressBitmapDownsamplingJavaUseCase,
    val compressBitmapRLEJavaUseCase: CompressBitmapRLEJavaUseCase,
) : CompressBitmapDownsamplingRleJavaUseCase {

    override suspend fun invoke(bitmap: Bitmap, compressionLevel: Int): ByteArray =
        compressBitmapRLEJavaUseCase(
            compressBitmapDownsamplingJavaUseCase(
                 bitmap,
                 compressionLevel,
             )
         )

}