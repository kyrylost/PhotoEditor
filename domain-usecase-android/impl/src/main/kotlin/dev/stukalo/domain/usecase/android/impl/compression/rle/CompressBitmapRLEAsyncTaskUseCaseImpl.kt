package dev.stukalo.domain.usecase.android.impl.compression.rle

import android.graphics.Bitmap
import android.os.AsyncTask
import dev.stukalo.domain.usecase.android.compression.rle.CompressBitmapRLEAsyncTaskUseCase
import kotlinx.coroutines.CompletableDeferred
import java.io.ByteArrayOutputStream

internal class CompressBitmapRLEAsyncTaskUseCaseImpl :
    CompressBitmapRLEAsyncTaskUseCase {

    class BitmapCompressionTask(
        private val bitmap: Bitmap,
        private val callback: (ByteArray) -> Unit
    ) : AsyncTask<Unit, Unit, ByteArray>() {

        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg params: Unit?): ByteArray {
            val width = bitmap.width
            val height = bitmap.height

            val pixels = IntArray(width * height)
            bitmap.getPixels(pixels, 0, width, 0, 0, width, height)

            val chunks = 8
            val step = pixels.size / chunks

            val outputStreams = Array(chunks) { ByteArrayOutputStream() }

            val jobs = (0 until chunks).map { chunkIndex ->
                val start =
                    if (chunkIndex == 0) 1
                    else chunkIndex * step

                val end =
                    if (chunkIndex == chunks - 1) pixels.size + 1
                    else (chunkIndex + 1) * step

                Thread {
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
                }.apply { start() }
            }

            jobs.forEach { it.join() }

            return outputStreams.fold(ByteArrayOutputStream()) { mergedStream, currentStream ->
                mergedStream.apply { write(currentStream.toByteArray()) }
            }.toByteArray()
        }

        @Deprecated("Deprecated in Java")
        override fun onPostExecute(result: ByteArray) {
            super.onPostExecute(result)
            callback(result)
        }
    }

    override suspend fun invoke(bitmap: Bitmap): ByteArray {
        val deferredResult = CompletableDeferred<ByteArray>()

        BitmapCompressionTask(bitmap) { compressedData ->
            deferredResult.complete(compressedData)
        }.execute()

        return deferredResult.await()
    }
}