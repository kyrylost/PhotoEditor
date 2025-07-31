package dev.stukalo.domain.usecase.android.impl.compression.rle

import android.graphics.Bitmap
import dev.stukalo.domain.usecase.android.compression.rle.CompressBitmapRLECoroutinesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

internal class CompressBitmapRLECoroutinesUseCaseImpl : CompressBitmapRLECoroutinesUseCase {

    override suspend fun invoke(bitmap: Bitmap): ByteArray = coroutineScope {
        val width = bitmap.width
        val height = bitmap.height

        val pixels = IntArray(width * height)
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)

        val chunks = 8
        val step = pixels.size / chunks

        val outputStreams = Array(chunks) { ByteArrayOutputStream() }

        val jobs = List(chunks) { chunkIndex ->

            launch(Dispatchers.Default) {
                val start =
                    if (chunkIndex == 0) 1
                    else chunkIndex * step

                val end =
                    if (chunkIndex == chunks - 1) pixels.size + 1
                    else (chunkIndex + 1) * step

                var count = 1
                val outputStream = outputStreams[chunkIndex]

                for (i in start until end) {
                    if (i < end - 1 && pixels[i] == pixels[i - 1] && count < 255) {
                        count++
                    } else {
                        outputStream.write(count)
                        outputStream.write(pixels[i - 1] ushr 16 and 0xFF) // Red
                        outputStream.write(pixels[i - 1] ushr 8 and 0xFF)  // Green
                        outputStream.write(pixels[i - 1] and 0xFF)         // Blue
                        count = 1
                    }
                }
            }

        }

        jobs.joinAll()

        outputStreams.fold(ByteArrayOutputStream()) { mergedStream, currentStream ->
            mergedStream.apply { write(currentStream.toByteArray()) }
        }.toByteArray()
    }

}