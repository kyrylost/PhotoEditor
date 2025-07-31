package dev.stukalo.domain.usecase.android.compression.downsampling_rle

import android.graphics.Bitmap
import dev.stukalo.domain.usecase.android.compression.base.BaseDownsamplingRleCompressorUseCase

interface CompressBitmapDownsamplingRleUseCase: BaseDownsamplingRleCompressorUseCase {

    override suspend operator fun invoke(bitmap: Bitmap, compressionLevel: Int): ByteArray

}