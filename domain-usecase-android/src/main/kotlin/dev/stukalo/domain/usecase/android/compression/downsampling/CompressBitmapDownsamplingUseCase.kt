package dev.stukalo.domain.usecase.android.compression.downsampling

import android.graphics.Bitmap
import dev.stukalo.domain.usecase.android.compression.base.BaseDownsamplingCompressorUseCase

interface CompressBitmapDownsamplingUseCase:
    BaseDownsamplingCompressorUseCase {

    override suspend operator fun invoke(bitmap: Bitmap, compressionLevel: Int): Bitmap

}