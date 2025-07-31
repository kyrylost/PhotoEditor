package dev.stukalo.domain.usecase.android.decompression.downsampling

import android.graphics.Bitmap
import dev.stukalo.domain.usecase.android.core.model.ImageDataDomainModel

interface DecompressByteArrayDownsamplingUseCase {
    operator fun invoke(
        imageDataDomainModel: ImageDataDomainModel
    ): Bitmap
}