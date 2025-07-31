package dev.stukalo.domain.usecase.android.core.model

data class ImageDataDomainModel(
    val byteArray: ByteArray,
    val width: Int,
    val height: Int,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ImageDataDomainModel

        if (!byteArray.contentEquals(other.byteArray)) return false
        if (width != other.width) return false
        if (height != other.height) return false

        return true
    }

    override fun hashCode(): Int {
        var result = byteArray.contentHashCode()
        result = 31 * result + width
        result = 31 * result + height
        return result
    }
}
