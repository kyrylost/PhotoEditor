package dev.stukalo.domain.usecase.android.decompression.rle

import android.graphics.Bitmap
import dev.stukalo.domain.usecase.android.core.model.ImageDataDomainModel

interface DecompressByteArrayRLEUseCase {
    operator fun invoke(
        byteArray: ByteArray,
        width: Int,
        height: Int
    ): Bitmap

    operator fun invoke(
        imageDataDomainModel: ImageDataDomainModel
    ): Bitmap
}