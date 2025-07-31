package dev.stukalo.domain.usecase.android.filestore

import android.graphics.Bitmap

interface RetrieveImagesFromInternalStorageUseCase {
    suspend operator fun invoke(): List<Bitmap>?
}
