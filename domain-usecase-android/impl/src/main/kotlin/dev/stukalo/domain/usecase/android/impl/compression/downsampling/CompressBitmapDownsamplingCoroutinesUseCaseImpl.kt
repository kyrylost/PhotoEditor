package dev.stukalo.domain.usecase.android.impl.compression.downsampling

import android.graphics.Bitmap
import dev.stukalo.domain.usecase.android.compression.downsampling.CompressBitmapDownsamplingCoroutinesUseCase
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import androidx.core.graphics.createBitmap
import androidx.core.graphics.get
import androidx.core.graphics.set

internal class CompressBitmapDownsamplingCoroutinesUseCaseImpl :
    CompressBitmapDownsamplingCoroutinesUseCase {

    override suspend fun invoke(bitmap: Bitmap, compressionLevel: Int): Bitmap = coroutineScope {
        val originalWidth = bitmap.width
        val originalHeight = bitmap.height

        val maxFactor = 4  // Compression at level 100 reduces the image size to 1/4
        val downsamplingFactor = 1f + (compressionLevel * (maxFactor - 1f)) / 100f

        if (downsamplingFactor == 1f) return@coroutineScope bitmap

        val newWidth = (originalWidth / downsamplingFactor).toInt()
        val newHeight = (originalHeight / downsamplingFactor).toInt()

        val downsampledBitmap = createBitmap(newWidth, newHeight)

        for (y in 0 until newHeight) {
            launch {
                for (x in 0 until newWidth) {
                    var sumRed = 0
                    var sumGreen = 0
                    var sumBlue = 0
                    var count = 0

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

        }

        downsampledBitmap
    }

}