package dev.stukalo.domain.usecase.android.impl.compression.downsampling

import android.graphics.Bitmap
import dev.stukalo.domain.usecase.android.compression.downsampling.CompressBitmapDownsamplingUseCase
import androidx.core.graphics.createBitmap
import androidx.core.graphics.get
import androidx.core.graphics.set

internal class CompressBitmapDownsamplingUseCaseImpl : CompressBitmapDownsamplingUseCase {

    override suspend fun invoke(bitmap: Bitmap, compressionLevel: Int): Bitmap {
        val originalWidth = bitmap.width
        val originalHeight = bitmap.height

        // Calculate the downsampling factor based on the compression level
        val maxFactor = 4  // Compression at level 100 reduces the image size to 1/4
        val downsamplingFactor = 1f + (compressionLevel * (maxFactor - 1f)) / 100f

        if (downsamplingFactor == 1f) return bitmap

        // Calculate the new dimensions
        val newWidth = (originalWidth / downsamplingFactor).toInt()
        val newHeight = (originalHeight / downsamplingFactor).toInt()

        // Create a new bitmap to hold the downsampled image
        val downsampledBitmap = createBitmap(newWidth, newHeight)

        // Iterate through the new bitmap and calculate the average color for each block
        for (y in 0 until newHeight) {
            for (x in 0 until newWidth) {
                var sumRed = 0
                var sumGreen = 0
                var sumBlue = 0
                var count = 0

                // Calculate the bounds of the block (use floating-point scaling for precise bounds)
                val startX = (x * downsamplingFactor).toInt()
                val startY = (y * downsamplingFactor).toInt()
                val endX = ((x + 1) * downsamplingFactor).toInt().coerceAtMost(originalWidth)
                val endY = ((y + 1) * downsamplingFactor).toInt().coerceAtMost(originalHeight)

                // Iterate over the block and accumulate the RGB values
                for (blockY in startY until endY) {
                    for (blockX in startX until endX) {
                        val pixel = bitmap[blockX, blockY]
                        sumRed += (pixel shr 16) and 0xFF
                        sumGreen += (pixel shr 8) and 0xFF
                        sumBlue += pixel and 0xFF
                        count++
                    }
                }

                // Calculate the average color for the block
                val avgRed = sumRed / count
                val avgGreen = sumGreen / count
                val avgBlue = sumBlue / count

                // Set the pixel in the downsampled bitmap
                val avgPixel = (255 shl 24) or (avgRed shl 16) or (avgGreen shl 8) or avgBlue
                downsampledBitmap[x, y] = avgPixel
            }
        }

        return downsampledBitmap
    }
}