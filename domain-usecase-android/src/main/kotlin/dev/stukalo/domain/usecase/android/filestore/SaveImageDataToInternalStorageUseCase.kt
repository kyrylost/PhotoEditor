package dev.stukalo.domain.usecase.android.filestore

interface SaveImageDataToInternalStorageUseCase {
    operator fun invoke(
        byteArray: ByteArray,
        compressionMethod: String,
        width: Int,
        height: Int,
        name: String?,
    )
}
