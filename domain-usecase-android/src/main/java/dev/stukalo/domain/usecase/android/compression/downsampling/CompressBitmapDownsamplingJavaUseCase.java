package dev.stukalo.domain.usecase.android.compression.downsampling;

import android.graphics.Bitmap;

import dev.stukalo.domain.usecase.android.base.BaseDownsamplingCompressorJavaUseCase;


public interface CompressBitmapDownsamplingJavaUseCase extends BaseDownsamplingCompressorJavaUseCase {

    Bitmap invoke(Bitmap bitmap, Integer compressionLevel) throws Exception;

}