package dev.stukalo.domain.usecase.android.compression.base

import android.graphics.Bitmap

interface BaseDownsamplingCompressorUseCase {

    suspend operator fun invoke(bitmap: Bitmap, compressionLevel: Int): Bitmap

}