package dev.stukalo.domain.usecase.android.impl.compression.downsampling_rle

import android.graphics.Bitmap
import dev.stukalo.domain.usecase.android.compression.rle.CompressBitmapRLEAsyncTaskUseCase
import dev.stukalo.domain.usecase.android.compression.downsampling.CompressBitmapDownsamplingAsyncTaskUseCase
import dev.stukalo.domain.usecase.android.compression.downsampling_rle.CompressBitmapDownsamplingRleAsyncTaskUseCase

internal class CompressBitmapDownsamplingRleAsyncTaskUseCaseImpl(
    val compressBitmapDownsamplingAsyncTaskUseCase: CompressBitmapDownsamplingAsyncTaskUseCase,
    val compressBitmapRLEAsyncTaskUseCase: CompressBitmapRLEAsyncTaskUseCase,
) : CompressBitmapDownsamplingRleAsyncTaskUseCase {

    override suspend fun invoke(bitmap: Bitmap, compressionLevel: Int): ByteArray =
        compressBitmapRLEAsyncTaskUseCase(
            compressBitmapDownsamplingAsyncTaskUseCase(
                 bitmap = bitmap,
                 compressionLevel = compressionLevel,
             )
         )

}