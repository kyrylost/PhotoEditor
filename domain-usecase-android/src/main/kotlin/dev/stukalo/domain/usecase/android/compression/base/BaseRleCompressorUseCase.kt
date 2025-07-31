package dev.stukalo.domain.usecase.android.compression.base

import android.graphics.Bitmap

interface BaseRleCompressorUseCase {

    suspend operator fun invoke(bitmap: Bitmap): ByteArray

}