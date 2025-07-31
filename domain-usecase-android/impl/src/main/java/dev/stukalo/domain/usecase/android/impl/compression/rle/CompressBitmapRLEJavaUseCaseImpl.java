package dev.stukalo.domain.usecase.android.impl.compression.rle;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import dev.stukalo.domain.usecase.android.compression.rle.CompressBitmapRLEJavaUseCase;

public class CompressBitmapRLEJavaUseCaseImpl implements CompressBitmapRLEJavaUseCase {

    @NonNull
    @Override
    public byte[] invoke(Bitmap bitmap) throws Exception {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

        int chunks = 8;
        int step = pixels.length / chunks;

        ByteArrayOutputStream[] outputStreams = new ByteArrayOutputStream[chunks];
        for (int i = 0; i < chunks; i++) {
            outputStreams[i] = new ByteArrayOutputStream();
        }

        ExecutorService executorService = Executors.newFixedThreadPool(chunks);
        List<Callable<Void>> tasks = new ArrayList<>();

        for (int chunkIndex = 0; chunkIndex < chunks; chunkIndex++) {
            final int index = chunkIndex;
            tasks.add(() -> {
                int start = (index == 0) ? 1 : index * step;
                int end = (index == chunks - 1) ? pixels.length + 1 : (index + 1) * step;

                int count = 1;
                ByteArrayOutputStream outputStream = outputStreams[index];

                for (int i = start; i < end; i++) {
                    if (i < end - 1 && pixels[i] == pixels[i - 1] && count < 255) {
                        count++;
                    } else {
                        outputStream.write(count);
                        outputStream.write((pixels[i - 1] >> 16) & 0xFF); // Red
                        outputStream.write((pixels[i - 1] >> 8) & 0xFF);  // Green
                        outputStream.write(pixels[i - 1] & 0xFF);         // Blue
                        count = 1;
                    }
                }

                return null;
            });
        }

        List<Future<Void>> futures = executorService.invokeAll(tasks);
        for (Future<Void> future : futures) {
            future.get(); // Wait for all tasks to complete
        }

        executorService.shutdown();

        // Merge all output streams
        ByteArrayOutputStream mergedStream = new ByteArrayOutputStream();
        for (ByteArrayOutputStream outputStream : outputStreams) {
            mergedStream.write(outputStream.toByteArray());
        }

        return mergedStream.toByteArray();
    }

}
