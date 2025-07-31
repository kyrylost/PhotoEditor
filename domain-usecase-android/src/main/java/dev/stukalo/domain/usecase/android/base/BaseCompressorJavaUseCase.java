package dev.stukalo.domain.usecase.android.base;

import android.graphics.Bitmap;

public interface BaseCompressorJavaUseCase {

    byte[] invoke(Bitmap bitmap) throws Exception;

}