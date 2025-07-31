package dev.stukalo.domain.usecase.android.impl.compression.rle

import android.graphics.Bitmap
import dev.stukalo.domain.usecase.android.compression.rle.CompressBitmapRLEUseCase
import java.io.ByteArrayOutputStream

internal class CompressBitmapRLEUseCaseImpl :
    CompressBitmapRLEUseCase {

    override suspend fun invoke(bitmap: Bitmap): ByteArray {
        val width = bitmap.width
        val height = bitmap.height

        val pixels = IntArray(width * height)
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)

        val outputStream = ByteArrayOutputStream()
        var count = 1

        for (i in 1 until pixels.size) {
            if (pixels[i] == pixels[i - 1] && count < 255) {
                count++
            } else {
                outputStream.write(count)
                outputStream.write(pixels[i - 1] ushr 16 and 0xFF)
                outputStream.write(pixels[i - 1] ushr 8 and 0xFF)
                outputStream.write(pixels[i - 1] and 0xFF)
                count = 1
            }
        }

        // Write the last run
        outputStream.write(count)
        outputStream.write(pixels.last() ushr 16 and 0xFF)
        outputStream.write(pixels.last() ushr 8 and 0xFF)
        outputStream.write(pixels.last() and 0xFF)

        return outputStream.toByteArray()
    }

}