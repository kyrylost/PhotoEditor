package dev.stukalo.domain.usecase.android.impl.filestore

import android.app.Application
import android.content.Intent
import androidx.core.content.ContextCompat.startForegroundService
import dev.stukalo.presentation.service.image.downloader.ImageDTO
import dev.stukalo.presentation.service.image.downloader.ImageDownloaderService
import dev.stukalo.domain.usecase.android.filestore.SaveImageDataToInternalStorageUseCase

class SaveImageDataToInternalStorageUseCaseImpl(
    private val application: Application,
    private val imageDTO: ImageDTO,
): SaveImageDataToInternalStorageUseCase {
    override fun invoke(
        byteArray: ByteArray,
        compressionMethod: String,
        width: Int,
        height: Int,
        name: String?,
    ) {
        imageDTO.byteArray = byteArray
        imageDTO.compressionMethod = compressionMethod
        imageDTO.width = width
        imageDTO.height = height
        imageDTO.name = name

        val intent = Intent(application, ImageDownloaderService::class.java)
        startForegroundService(application, intent)
    }
}
