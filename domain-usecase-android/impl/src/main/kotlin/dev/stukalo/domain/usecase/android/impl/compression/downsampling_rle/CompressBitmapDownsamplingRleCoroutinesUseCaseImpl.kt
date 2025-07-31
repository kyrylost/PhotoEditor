package dev.stukalo.domain.usecase.android.impl.compression.downsampling_rle

import android.graphics.Bitmap
import dev.stukalo.domain.usecase.android.compression.downsampling.CompressBitmapDownsamplingCoroutinesUseCase
import dev.stukalo.domain.usecase.android.compression.downsampling_rle.CompressBitmapDownsamplingRleCoroutinesUseCase
import dev.stukalo.domain.usecase.android.compression.rle.CompressBitmapRLECoroutinesUseCase

internal class CompressBitmapDownsamplingRleCoroutinesUseCaseImpl(
    val compressBitmapDownsamplingCoroutinesUseCase: CompressBitmapDownsamplingCoroutinesUseCase,
    val compressBitmapRLECoroutinesUseCase: CompressBitmapRLECoroutinesUseCase,
) : CompressBitmapDownsamplingRleCoroutinesUseCase {

    override suspend fun invoke(bitmap: Bitmap, compressionLevel: Int): ByteArray =
        compressBitmapRLECoroutinesUseCase(
            compressBitmapDownsamplingCoroutinesUseCase(
                 bitmap = bitmap,
                 compressionLevel = compressionLevel,
             )
         )

}