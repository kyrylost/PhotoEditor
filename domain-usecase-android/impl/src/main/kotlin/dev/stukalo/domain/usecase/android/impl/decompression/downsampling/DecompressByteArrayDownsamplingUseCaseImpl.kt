package dev.stukalo.domain.usecase.android.impl.decompression.downsampling

import android.graphics.Bitmap
import androidx.core.graphics.createBitmap
import dev.stukalo.domain.usecase.android.core.model.ImageDataDomainModel
import dev.stukalo.domain.usecase.android.decompression.downsampling.DecompressByteArrayDownsamplingUseCase
import java.nio.ByteBuffer


class DecompressByteArrayDownsamplingUseCaseImpl :
    DecompressByteArrayDownsamplingUseCase {

    override fun invoke(imageDataDomainModel: ImageDataDomainModel): Bitmap {
        val bitmap = createBitmap(imageDataDomainModel.width, imageDataDomainModel.height)
        val buffer = ByteBuffer.wrap(imageDataDomainModel.byteArray)
        bitmap.copyPixelsFromBuffer(buffer)
        return bitmap
    }

}