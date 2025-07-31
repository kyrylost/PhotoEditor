package dev.stukalo.presentation.service.image.downloader

class ImageDTO {
    lateinit var byteArray: ByteArray
    var compressionMethod: String = ""
    var width: Int = 0
    var height: Int = 0
    var name: String? = null
}
