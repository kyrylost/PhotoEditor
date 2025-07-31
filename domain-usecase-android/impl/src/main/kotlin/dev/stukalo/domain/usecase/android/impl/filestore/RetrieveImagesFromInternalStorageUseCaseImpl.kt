package dev.stukalo.domain.usecase.android.impl.filestore

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import dev.stukalo.domain.usecase.android.core.model.ImageDataDomainModel
import dev.stukalo.domain.usecase.android.decompression.downsampling.DecompressByteArrayDownsamplingUseCase
import dev.stukalo.domain.usecase.android.decompression.rle.DecompressByteArrayRLEUseCase
import dev.stukalo.domain.usecase.android.filestore.RetrieveImagesFromInternalStorageUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import java.io.BufferedInputStream
import java.io.DataInputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import dev.stukalo.presentation.core.localization.R.string as RString

/**
 * This UseCase retrieves all images saved in filestore
 *
 * @return [List]<[Bitmap]> if there are any saved image, emptyList if no images were saved, and null in case of error
 */
class RetrieveImagesFromInternalStorageUseCaseImpl(
    private val application: Application,
    private val decompressByteArrayRLEUseCase: DecompressByteArrayRLEUseCase,
    private val decompressByteArrayDownsamplingUseCase: DecompressByteArrayDownsamplingUseCase,
): RetrieveImagesFromInternalStorageUseCase {

    @OptIn(ExperimentalStdlibApi::class)
    override suspend fun invoke(): List<Bitmap>? {

        require(currentCoroutineContext()[CoroutineDispatcher] == Dispatchers.IO) {
            "Use IO Dispatcher!"
        }

        val imagesDirectory = application.getDir(
            application.getString(RString.downloads_dir_name),
            Context.MODE_PRIVATE
        )
        val files = imagesDirectory.listFiles()

        return try {
            files?.let {
                // Prepare groups
                val rleFiles = mutableListOf<File>()
                val downsamplingFiles = mutableListOf<File>()

                // Classify files in a single iteration
                it.forEach { file ->
                    when {
                        file.name.contains(Regex("RLE"))
                                && !file.name.contains(Regex("config")) -> rleFiles.add(file)

                        file.name.contains(Regex("DS"))
                                && !file.name.contains(Regex("config")) -> downsamplingFiles.add(file)
                    }
                }

                // Map each group with the corresponding method
                val rleBitmaps = rleFiles.map { file ->
                    decompressByteArrayRLEUseCase(retrieveImageData(file))
                }
                val downsamplingBitmaps = downsamplingFiles.map { file ->
                    decompressByteArrayDownsamplingUseCase(retrieveImageData(file))
                }

                // Combine all results into a single list
                rleBitmaps + downsamplingBitmaps
            } ?: emptyList()
//            files
//                ?.filter { file -> !file.name.contains(Regex("[a-zA-Z]")) }
//                ?.map { file -> decompressByteArrayRLEUseCase(retrieveImageData(file)) }// BitmapFactory.decodeStream(FileInputStream(file))
//                ?: emptyList()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            null
        }
    }

    private fun retrieveImageData(file: File): ImageDataDomainModel {
        val config = File(file.path + application.getString(RString.image_config_prefix)).readLines()

        val bytes = ByteArray(file.length().toInt())
        val bis = BufferedInputStream(FileInputStream(file))
        val dis = DataInputStream(bis)
        dis.readFully(bytes)

        return ImageDataDomainModel(
            bytes,
            config[0].toInt(),
            config[1].toInt()
        )
    }

//    private fun retrieveByreArray(file: File): ByteArray {
//        val bytes = ByteArray(file.length().toInt())
//        val bis = BufferedInputStream(FileInputStream(file))
//        val dis = DataInputStream(bis)
//        dis.readFully(bytes)
//
//        return bytes
//    }

}
