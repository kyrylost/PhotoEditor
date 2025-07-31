package dev.stukalo.domain.usecase.android.compression.base

import android.graphics.Bitmap

interface BaseDownsamplingRleCompressorUseCase {

    suspend operator fun invoke(bitmap: Bitmap, compressionLevel: Int): ByteArray

}