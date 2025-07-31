package dev.stukalo.domain.usecase.android.impl.compression.downsampling_rle

import android.graphics.Bitmap
import dev.stukalo.domain.usecase.android.compression.downsampling.CompressBitmapDownsamplingUseCase
import dev.stukalo.domain.usecase.android.compression.downsampling_rle.CompressBitmapDownsamplingRleUseCase
import dev.stukalo.domain.usecase.android.compression.rle.CompressBitmapRLEUseCase

internal class CompressBitmapDownsamplingRleUseCaseImpl(
    val compressBitmapDownsamplingUseCase: CompressBitmapDownsamplingUseCase,
    val compressBitmapRLEUseCase: CompressBitmapRLEUseCase,
) : CompressBitmapDownsamplingRleUseCase {

    override suspend fun invoke(bitmap: Bitmap, compressionLevel: Int): ByteArray =
         compressBitmapRLEUseCase(
             compressBitmapDownsamplingUseCase(
                 bitmap = bitmap,
                 compressionLevel = compressionLevel,
             )
         )

}