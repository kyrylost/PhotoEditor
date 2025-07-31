package dev.stukalo.domain.usecase.android.impl.compression.downsampling;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import dev.stukalo.domain.usecase.android.compression.downsampling.CompressBitmapDownsamplingJavaUseCase;

public class CompressBitmapDownsamplingJavaUseCaseImpl implements CompressBitmapDownsamplingJavaUseCase {

    @NonNull
    @Override
    public Bitmap invoke(Bitmap bitmap, Integer compressionLevel) throws Exception {
        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();

        // Calculate the downsampling factor based on the compression level
        float maxFactor = 4f; // Compression at level 100 reduces the image size to 1/4
        float downsamplingFactor = 1f + (compressionLevel * (maxFactor - 1f)) / 100f;

        if (downsamplingFactor == 1f) return bitmap;

        // Calculate the new dimensions
        int newWidth = (int) (originalWidth / downsamplingFactor);
        int newHeight = (int) (originalHeight / downsamplingFactor);

        // Create a new bitmap to hold the downsampled image
        Bitmap downsampledBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);

        int chunks = 8;
        int step = newHeight / chunks;

        ExecutorService executorService = Executors.newFixedThreadPool(chunks);
        List<Callable<Void>> tasks = new ArrayList<>();

        for (int chunkIndex = 0; chunkIndex < chunks; chunkIndex++) {
            final int index = chunkIndex;
            tasks.add(() -> {
                int startY = index * step;
                int endY = (index == chunks - 1) ? newHeight : (index + 1) * step;

                for (int y = startY; y < endY; y++) {
                    for (int x = 0; x < newWidth; x++) {
                        int sumRed = 0;
                        int sumGreen = 0;
                        int sumBlue = 0;
                        int count = 0;

                        // Calculate the bounds of the block
                        int startX = (int) (x * downsamplingFactor);
                        int startBlockY = (int) (y * downsamplingFactor);
                        int endX = Math.min((int) ((x + 1) * downsamplingFactor), originalWidth);
                        int endBlockY = Math.min((int) ((y + 1) * downsamplingFactor), originalHeight);

                        // Accumulate RGB values
                        for (int blockY = startBlockY; blockY < endBlockY; blockY++) {
                            for (int blockX = startX; blockX < endX; blockX++) {
                                int pixel = bitmap.getPixel(blockX, blockY);
                                sumRed += (pixel >> 16) & 0xFF;
                                sumGreen += (pixel >> 8) & 0xFF;
                                sumBlue += pixel & 0xFF;
                                count++;
                            }
                        }

                        // Calculate the average color for the block
                        int avgRed = sumRed / count;
                        int avgGreen = sumGreen / count;
                        int avgBlue = sumBlue / count;

                        // Set the pixel in the downsampled bitmap
                        int avgPixel = (255 << 24) | (avgRed << 16) | (avgGreen << 8) | avgBlue;
                        downsampledBitmap.setPixel(x, y, avgPixel);
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

        return downsampledBitmap;
    }

}
