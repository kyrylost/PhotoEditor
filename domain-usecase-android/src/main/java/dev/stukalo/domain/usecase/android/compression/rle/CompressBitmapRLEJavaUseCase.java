package dev.stukalo.domain.usecase.android.compression.rle;

import android.graphics.Bitmap;

import dev.stukalo.domain.usecase.android.base.BaseCompressorJavaUseCase;


public interface CompressBitmapRLEJavaUseCase extends BaseCompressorJavaUseCase {

    byte[] invoke(Bitmap bitmap) throws Exception;

}