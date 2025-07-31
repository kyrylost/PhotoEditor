package dev.stukalo.domain.usecase.android.base;

import android.graphics.Bitmap;

public interface BaseDownsamplingCompressorJavaUseCase {

    Bitmap invoke(Bitmap bitmap, Integer compressionLevel) throws Exception;

}