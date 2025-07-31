package dev.stukalo.domain.usecase.android.compression.rle

import android.graphics.Bitmap
import dev.stukalo.domain.usecase.android.compression.base.BaseRleCompressorUseCase

interface CompressBitmapRLEAsyncTaskUseCase:
    BaseRleCompressorUseCase {

    override suspend operator fun invoke(bitmap: Bitmap): ByteArray

}