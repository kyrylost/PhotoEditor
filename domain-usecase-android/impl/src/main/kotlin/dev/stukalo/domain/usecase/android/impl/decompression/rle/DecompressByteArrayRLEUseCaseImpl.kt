package dev.stukalo.domain.usecase.android.impl.decompression.rle

import android.graphics.Bitmap
import dev.stukalo.domain.usecase.android.core.model.ImageDataDomainModel
import dev.stukalo.domain.usecase.android.decompression.rle.DecompressByteArrayRLEUseCase
import java.io.ByteArrayInputStream

class DecompressByteArrayRLEUseCaseImpl :
    DecompressByteArrayRLEUseCase {

    override fun invoke(byteArray: ByteArray, width: Int, height: Int) =
        decodeByteArray(byteArray, width, height)

    override fun invoke(imageDataDomainModel: ImageDataDomainModel) = with(imageDataDomainModel) {
        decodeByteArray(byteArray, width, height)
    }

    private fun decodeByteArray(byteArray: ByteArray, width: Int, height: Int): Bitmap {
        val inputStream = ByteArrayInputStream(byteArray)
        val pixels = IntArray(width * height)
        var index = 0

        while (index < pixels.size) {
            val count = inputStream.read()

            val red = inputStream.read()
            val green = inputStream.read()
            val blue = inputStream.read()

            val color = (255 shl 24) or (red shl 16) or (green shl 8) or blue

//            if (count <= 0) break //FIX AND REMOVE
            for (i in 0 until count) {
//                if (index + 1 < pixels.size)
                pixels[index++] = color
            }
        }

        return Bitmap.createBitmap(pixels, width, height, Bitmap.Config.ARGB_8888)
    }

}