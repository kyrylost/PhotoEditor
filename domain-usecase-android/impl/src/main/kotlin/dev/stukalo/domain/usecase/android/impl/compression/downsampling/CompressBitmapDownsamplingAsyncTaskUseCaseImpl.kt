package dev.stukalo.domain.usecase.android.impl.compression.downsampling

import android.graphics.Bitmap
import android.os.AsyncTask
import dev.stukalo.domain.usecase.android.compression.downsampling.CompressBitmapDownsamplingAsyncTaskUseCase
import kotlinx.coroutines.CompletableDeferred
import androidx.core.graphics.createBitmap
import androidx.core.graphics.get
import androidx.core.graphics.set

internal class CompressBitmapDownsamplingAsyncTaskUseCaseImpl :
    CompressBitmapDownsamplingAsyncTaskUseCase {

    class BitmapDownsamplingTask(
        private val bitmap: Bitmap,
        private val compressionLevel: Int,
        private val callback: (Bitmap) -> Unit
    ) : AsyncTask<Unit, Unit, Bitmap>() {

        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg params: Unit?): Bitmap {
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

            val chunks = 8
            val step = newHeight / chunks

            val jobs = (0 until chunks).map { chunkIndex ->
                Thread {
                    val startY = chunkIndex * step
                    val endY = if (chunkIndex == chunks - 1) newHeight else (chunkIndex + 1) * step

                    for (y in startY until endY) {
                        for (x in 0 until newWidth) {
                            var sumRed = 0
                            var sumGreen = 0
                            var sumBlue = 0
                            var count = 0

                            // Calculate the bounds of the block
                            val startX = (x * downsamplingFactor).toInt()
                            val startBlockY = (y * downsamplingFactor).toInt()
                            val endX = ((x + 1) * downsamplingFactor).toInt().coerceAtMost(originalWidth)
                            val endBlockY = ((y + 1) * downsamplingFactor).toInt().coerceAtMost(originalHeight)

                            // Accumulate RGB values
                            for (blockY in startBlockY until endBlockY) {
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
                            synchronized(downsampledBitmap) {
                                downsampledBitmap[x, y] = avgPixel
                            }
                        }
                    }
                }.apply { start() }
            }

            jobs.forEach { it.join() }

            return downsampledBitmap
        }

        @Deprecated("Deprecated in Java")
        override fun onPostExecute(result: Bitmap) {
            super.onPostExecute(result)
            callback(result)
        }
    }

    override suspend fun invoke(bitmap: Bitmap, compressionLevel: Int): Bitmap {
        val deferredResult = CompletableDeferred<Bitmap>()

        BitmapDownsamplingTask(bitmap, compressionLevel) { downsampledBitmap ->
            deferredResult.complete(downsampledBitmap)
        }.execute()

        return deferredResult.await()
    }
}